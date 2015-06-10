package com.company.weather.fragements;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.company.weather.R;
import com.company.weather.activities.WeatherActivity;
import com.company.weather.model.Address_ZipCode;
import com.company.weather.model.AutoCompletePlaces;
import com.company.weather.model.Place;
import com.company.weather.model.Predictions;
import com.company.weather.model.Results_ZipCode;
import com.company.weather.utils.WeatherUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class PlacesFragment extends Fragment implements TextWatcher,
														OnClickListener{

	WeatherActivity      weatherActivity;
	AutoCompleteTextView autoCompleteTextView;
	Button               savePlace;
	ListView             listView;
	SharedPreferences    prefs;
	Editor               editor;
	
	final String DEFAULT = "default";
	
	@Override
	public void onAttach(Activity activity) {
		weatherActivity = (WeatherActivity) activity;
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.places_layout, container,false);
		
		prefs                = weatherActivity.getPrefs();
		editor               = prefs.edit();
		listView             = (ListView) view.findViewById(R.id.placesList);
		autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
		savePlace            = (Button) view.findViewById(R.id.savePlace);
		autoCompleteTextView.setThreshold(1);
		autoCompleteTextView.addTextChangedListener(this);
		savePlace.setOnClickListener(this);
		
		populateListView();
		return view;
	}

	private void populateListView() {
		
		String       placesListJson = prefs.getString(getResources().getString(R.string.places_list), DEFAULT);
		List<Place>  places         = placesListJson.equals(DEFAULT) ? new ArrayList<Place>() : (List<Place>) new Gson().fromJson(placesListJson, new TypeToken<List<Place>>(){}.getType());
		List<String> cities         = new ArrayList<String>();
		
		for(Place place: places)
		{
			cities.add(place.getCity());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(weatherActivity, android.R.layout.simple_list_item_1,cities);
		listView.setAdapter(adapter);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(WeatherUtils.isInteger(s.toString()))
		{
			callGoogleGeoCodeApi(autoCompleteTextView,s);
		}
		
		else if(!WeatherUtils.isInteger(s.toString()))
		{
			callGooglePlacesApi(autoCompleteTextView, s);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
	private void callGooglePlacesApi(final AutoCompleteTextView autoCompleteTextView,CharSequence s) 
	{
		StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
		
		url.append("input="+s);
		url.append("&types=(cities)");
		url.append("&components=country:us");
		//TODO replace key
		url.append("&key=AIzaSyAaWk7VCvZj-lH3xfColYfXyz63gQcbieQ");
		
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url.toString(),null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Gson               gson         = new Gson();
						AutoCompletePlaces places       = gson.fromJson(response.toString(), AutoCompletePlaces.class);
						List<String>       descriptions = new ArrayList<String>();
						
						for(Predictions prediction: places.getPredictions())
						{
							descriptions.add(prediction.getDescription());
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(weatherActivity, R.layout.autocomplete_dropdown_textview,descriptions);
						autoCompleteTextView.setAdapter(adapter);
					}
				},
				new Response.ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.d("Test",arg0.toString());
					}
					
				});
		weatherActivity.getRequestQueueObject().add(jsonRequest);
	}
	
	private void callGoogleGeoCodeApi(final AutoCompleteTextView autoCompleteTextView, CharSequence s) {
		
		StringBuilder url = new StringBuilder("http://maps.googleapis.com/maps/api/geocode/json?");
		
		url.append("address="+s);
		url.append("&components=country:us");
		
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url.toString(),null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Gson                  gson            = new Gson();
						Address_ZipCode       address_ZipCode = gson.fromJson(response.toString(), Address_ZipCode.class);
						List<Results_ZipCode> results_ZipCode = address_ZipCode.getResults();
						List<String>          descriptions    = new ArrayList<String>();
						
						// This is not AutoComplete by ZipCode
						// Json object has only one object which matches with given zipcode
						
						for(Results_ZipCode result: results_ZipCode)
						{
							descriptions.add(result.getFormatted_address());
						}  
						
						descriptions.add("Atlanta,GA,30341");
						descriptions.add("Chicago,IL,34512");
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(weatherActivity, R.layout.autocomplete_dropdown_textview,descriptions);
						autoCompleteTextView.setAdapter(adapter);
					}
				},
				new Response.ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.d("Test",arg0.toString());
					}
					
				});
		weatherActivity.getRequestQueueObject().add(jsonRequest);
	}
	

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.savePlace:
			savePlace();
			populateListView();
			break;

		default:
			break;
		}
		
	}

	private void savePlace() {
		
		String address[] = autoCompleteTextView.getText().toString().split(",");
		Place  place     = new Place();
		
		if(address.length == 3)
		{
			place.setCity(address[0]);
			place.setState(address[1]);
			place.setId(null);
			/*
			 * TODO
			 * Currently using List for Place.
			 * When Place can hold an Id this List<Place> can be changed to TreeSet
			 * So that no duplicate values are added.
			 * As of now duplicate values are filtered before adding to list
			 * by overring equals and hashcode on Place.
			 */
			
			String      placesListJson = prefs.getString(getResources().getString(R.string.places_list), DEFAULT);
			List<Place> places         = placesListJson.equals(DEFAULT) ? new ArrayList<Place>() : (List<Place>) new Gson().fromJson(placesListJson, new TypeToken<List<Place>>(){}.getType());
			
			if(!isPlaceExist(place,places))
			{
				places.add(place);
			}
				
			editor.putString(getResources().getString(R.string.places_list), new Gson().toJson(places));
			editor.commit();
		}
		else
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(weatherActivity);
			alertDialogBuilder.setMessage("Either Select from AutoComplete or Type in this Pattern city,state,country")
								.setCancelable(false)
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.cancel();
										
									}
								});
			alertDialogBuilder.show();
		}
		
	}

	
	private boolean isPlaceExist(Place place,List<Place> places) {
		
		boolean isPlaceExist = false;
		
		for(Place placeObj:places)
		{
			if(placeObj.equals(place))
				isPlaceExist = true;
		}
		
		return isPlaceExist;
	}
}
