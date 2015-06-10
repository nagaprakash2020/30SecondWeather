package com.company.weather.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.company.weather.R;
import com.company.weather.fragements.HourlyFragment;
import com.company.weather.fragements.NavigationDrawerFragment;
import com.company.weather.fragements.PlacesFragment;
import com.company.weather.fragements.VideoFragment;
import com.company.weather.fragements.WhereAmIFragment;
import com.company.weather.interfaces.MyLocationChangedInterface;
import com.company.weather.model.Place;
import com.company.weather.model.WeatherInfo;
import com.company.weather.utils.WeatherUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WeatherActivity extends ActionBarActivity implements 
														ConnectionCallbacks,
														OnConnectionFailedListener,
														LocationListener
														{
	
	GoogleApiClient 		 mGoogleApiClient;
	Location        		 location;
	LocationRequest 		 locationRequest;
	String[]        		 navMenuTitles;
	TypedArray      		 navMenuIcons;
	ActionBarDrawerToggle    drawerToggle;
	WeatherInfo              currentWeatherInfo;
	Fragment 				 retainedFragment;
	ProgressDialog           weatherDialog;
	ProgressDialog           locationDialog;
	ProgressDialog           asyncProgress;
	RequestQueue             requestQueue;
	long                     startTime;
	long                     endTime;
	ActionBar                actionBar;
	Toolbar                  toolbar;
	Spinner                  places_spinner;
	// Later this can be changed to List<Place>
	List<String>             city_list;
	SharedPreferences        prefs;
	OnSharedPreferenceChangeListener sharedPrefListener;
	
	public MyLocationChangedInterface locationChangedInterface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Location","onCreate");
		
		if(!WeatherUtils.isLocationEnabled(this))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Location Not Found");  // GPS not found
	        builder.setMessage("Do you want to enable location ?"); // Want to enable?
	        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	            } 
	        });
	        
	        builder.setNegativeButton("NO", null);
	        builder.create().show();
		}
		
		requestQueue  	   = Volley.newRequestQueue(this);
		weatherDialog	   = new ProgressDialog(WeatherActivity.this);
		locationDialog	   = new ProgressDialog(WeatherActivity.this);
		toolbar        	   = (Toolbar) findViewById(R.id.app_bar);
		places_spinner 	   = (Spinner) findViewById(R.id.places_spinner);
		actionBar          = getActionBar();
		prefs              = getPreferences(Context.MODE_PRIVATE);
		sharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {
				Toast.makeText(WeatherActivity.this, "Shared Pref Changed", Toast.LENGTH_SHORT).show();
				//TODO Below notifyDataSetChanged() is not working because List<String> cities is never updated
				// Update cities and then call notifyDataSetChanged.
				// Also add default city in the list from the location.
				// They should be allowed to delete the city from shared prefs from the list.
				((BaseAdapter)places_spinner.getAdapter()).notifyDataSetChanged();
			}
		};
		prefs.registerOnSharedPreferenceChangeListener(sharedPrefListener);
		
		places_spinner.setAdapter(getSharedPrefAdapter());
		
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_fragment);
		DrawerLayout             drawerLayout   = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerFragment.setUp(drawerLayout, toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if(savedInstanceState == null)
		{
//			displayView(0);
//			createLocationRequest();
//			buildGoogleApiClient();
		}
		else
		{
			retainedFragment = getSupportFragmentManager().getFragment(savedInstanceState, "FRAGMENT");
		}
	}
	
	private ArrayAdapter<String> getSharedPrefAdapter() {
		
		String       placesListJson = prefs.getString(getResources().getString(R.string.places_list), "default");
		List<Place>  places         = placesListJson.equals("default") ? new ArrayList<Place>() : (List<Place>) new Gson().fromJson(placesListJson, new TypeToken<List<Place>>(){}.getType());
		List<String> cities         = new ArrayList<String>();
		
		for(Place place: places)
		{
			cities.add(place.getCity());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cities);
		
		return adapter;
		
	}

	private void createLocationRequest() {
		
		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		locationRequest.setSmallestDisplacement(1000); // Update location every 10KM
		locationRequest.setFastestInterval(1000*5);    //  get update when another application requests location every 5 seconds
		locationRequest.setInterval(1000 * 10); // Update location every 10 sec
	}
	
	public class NavigationDrawerListListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			retainedFragment = null;
			displayView(position);
			
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	public void displayView(int position) {
		
		if(retainedFragment ==  null)
		{
			switch(position)
			{
				case 0:
					retainedFragment = new VideoFragment();
					break;
				case 1:
					retainedFragment = new WhereAmIFragment();
					break;
				case 2:
					retainedFragment = new HourlyFragment();
					break;
				case 3:
					retainedFragment = new PlacesFragment();
					break;
				default:
					break;
			}
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, retainedFragment).commit();

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addApi(LocationServices.API)
		.addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d("Location","onConnectionFailed");
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d("Location","onConnected");
		startLocationUpdates();
		
	}

	private void startLocationUpdates() {
		if(locationDialog != null)
		{
			locationDialog.setTitle("Pulling your loction");
			locationDialog.setMessage("Hang on");
			locationDialog.setIndeterminate(false);
			locationDialog.setCancelable(false);
			locationDialog.show();
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest,this);
	}

	@Override
	public void onConnectionSuspended(int cause) {
		if(locationDialog != null && locationDialog.isShowing())
			locationDialog.dismiss();
		Log.d("Location","onConnectionSuspended");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(mGoogleApiClient != null)
			mGoogleApiClient.connect();
	}
	
	@Override
	protected void onPause() {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
		{
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
			mGoogleApiClient.disconnect();
		}
		// Don't update on Pause
		if(locationChangedInterface != null)
		{
			locationChangedInterface = null;
		}
		
		// Dismiss progress dialog if showing
		if(weatherDialog != null && weatherDialog.isShowing())
			weatherDialog.dismiss();
		if(locationDialog != null && locationDialog.isShowing())
			locationDialog.dismiss();
		
		super.onStop();
	}

	@Override
	protected void onResume() {
		if(mGoogleApiClient!= null && mGoogleApiClient.isConnected())
		{
			startLocationUpdates();
		}
		super.onResume();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		setLocation(location);
	}

	public Location getLocation() {
		return location;
	}
	
	public void setWeatherInfo(WeatherInfo weatherInfo)
	{
		currentWeatherInfo = weatherInfo;
	}
	
	public WeatherInfo getWeatherInfo()
	{
		return currentWeatherInfo;
	}

	public void setLocation(Location location) {
		
			locationDialog.dismiss();		
			requestWeatherData(location);
	}
	
	public RequestQueue getRequestQueueObject()
	{
		return requestQueue;
	}
	
	public List<String> getCity_list() {
		return city_list;
	}

	public void setCity_list(List<String> city_list) {
		this.city_list = city_list;
	}

	public SharedPreferences getPrefs() {
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs) {
		this.prefs = prefs;
	}

	private void requestWeatherData(Location location) {
		
		String ENDPOINT = "http://www.30secondweather.com";
//		weatherDialog.setTitle("Getting Weather Info");
		weatherDialog.setMessage("Using Volley");
		weatherDialog.setIndeterminate(false);
		weatherDialog.setCancelable(false);
		weatherDialog.show();
	
//		 Volley Request
		StringBuffer url = new StringBuffer("http://stg.30secondweather.com/api/v1/?");
		url.append("lat="+location.getLatitude()+"&lng="+location.getLongitude());
		startTime = System.currentTimeMillis();
		JsonObjectRequest getWeatherRequest = new JsonObjectRequest(Request.Method.GET,url.toString(),null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						endTime = System.currentTimeMillis();
						Log.d("TAG",endTime-startTime + ":Millisecs");
						Toast.makeText(getApplicationContext(), endTime-startTime +":Milli Seconds", Toast.LENGTH_SHORT).show();
						weatherDialog.dismiss();
						//Toast.makeText(WeatherActivity.this, "Response Received", Toast.LENGTH_LONG).show();
						//Log.d("TAG",response.toString());
						Gson gson = new Gson();
						currentWeatherInfo = gson.fromJson(response.toString(), WeatherInfo.class);
						if(locationChangedInterface!=null)
						{
							locationChangedInterface.onLocationChanged(currentWeatherInfo);
						}
					}
				},
				
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						weatherDialog.dismiss();
						Toast.makeText(WeatherActivity.this, "Error Retrieving Data", Toast.LENGTH_LONG).show();
						Log.d("TAG",error.toString());
					}
				});
		
		requestQueue.add(getWeatherRequest);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "FRAGMENT", retainedFragment);
	}
	
	public Fragment getRetainedFragment() {
		return retainedFragment;
	}

	public void setRetainedFragment(Fragment retainedFragment) {
		this.retainedFragment = retainedFragment;
	}
	
}
