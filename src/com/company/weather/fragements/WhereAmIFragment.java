package com.company.weather.fragements;

import com.company.weather.activities.WeatherActivity;
import com.company.weather.interfaces.MyLocationChangedInterface;
import com.company.weather.model.WeatherInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.company.weather.R;

import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class WhereAmIFragment extends Fragment implements MyLocationChangedInterface{

	WeatherActivity weatherActivity;
	GoogleMap       googleMap;
	
	@Override
	public void onAttach(Activity activity) {
		weatherActivity = (WeatherActivity) activity;
		
		if(weatherActivity != null)
		{
			weatherActivity.locationChangedInterface = this;
		}
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View               view           = inflater.inflate(R.layout.where_am_i_maps, container,false);
		FragmentManager    fragmentManger = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP ? getFragmentManager() : getChildFragmentManager();
		SupportMapFragment mapFragment    = (SupportMapFragment) fragmentManger.findFragmentById(R.id.map);
		googleMap                         = mapFragment.getMap();
		
		if(googleMap == null)
		{
			Toast.makeText(weatherActivity, "Unable to create a Map", Toast.LENGTH_SHORT).show();
		}
		else
		{
			setMapDetails(weatherActivity.getLocation());
		}
		return view;
	}

	private void setMapDetails(Location location) {

		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(12).build();
		MarkerOptions  marker         = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
		 
		marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

		googleMap.addMarker(marker);
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}

	@Override
	public void onLocationChanged(WeatherInfo weatherInfo) {
		
		if(googleMap != null)
		{
//			setMapDetails();
		}
		
	}
}
