package com.company.weather.fragements;

import com.company.weather.R;
import com.company.weather.activities.WeatherActivity;
import com.company.weather.adapter.HourlyWeatherListAdapter;
import com.company.weather.interfaces.MyLocationChangedInterface;
import com.company.weather.model.WeatherInfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HourlyFragment extends Fragment implements MyLocationChangedInterface{
	
	WeatherActivity weatherActivity;
	WeatherInfo     currentWeatherInfo;
	View            view;
	ListView        listViewHourly;
	
	
	@Override
	public void onAttach(Activity activity) {
		weatherActivity    = (WeatherActivity) activity;
		currentWeatherInfo = weatherActivity.getWeatherInfo();
		
		if(weatherActivity != null)
		{
			weatherActivity.locationChangedInterface = this;
		}
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view           = inflater.inflate(R.layout.hourly_list_view	,container, false);
		listViewHourly = (ListView) view.findViewById(R.id.hourly_list_view);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		updateUI(currentWeatherInfo);
		super.onStart();
	}
	
	// Implementing MyLocationChangedInterface
	@Override
	public void onLocationChanged(WeatherInfo weatherInfo) {
		updateUI(weatherInfo);
	}

	private void updateUI(WeatherInfo weatherInfo) {
		
		if(weatherInfo != null)
		{
			currentWeatherInfo = weatherInfo;
			HourlyWeatherListAdapter adapter = new HourlyWeatherListAdapter(weatherActivity, weatherInfo.getResult().getHourly());
			listViewHourly.setAdapter(adapter);
			
		}
		
	}
}
