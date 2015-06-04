package com.company.weather.fragements;

import com.company.weather.activities.WeatherActivity;
import com.company.weather.interfaces.MyLocationChangedInterface;
import com.company.weather.model.WeatherInfo;
import com.company.weather.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoFragment extends Fragment implements MyLocationChangedInterface{

	WeatherActivity weatherActivity;
	View            videoView;
	TextView        latitude;
	TextView        longitude;
	TextView        address;
	Context         context;
	VideoView       video;
	ProgressBar     progressBar;
	WeatherInfo     currentWeatherInfo;
	MediaController mediaController;
	RelativeLayout  videoLayout;
	
	public VideoFragment(){}
	
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
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		videoView 		= inflater.inflate(R.layout.videofragment,container,false);
		video     		= (VideoView) videoView.findViewById(R.id.video);
		videoLayout     = (RelativeLayout) videoView.findViewById(R.id.videoFrame);
		mediaController = new MediaController(video.getContext());
		progressBar     = (ProgressBar)videoView.findViewById(R.id.videoProgress);
		
		return videoView;
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

	@Override
	public void onLocationChanged(WeatherInfo weatherInfo) {
		Log.d("Location","From Video Fragment");
		updateUI(weatherInfo);
		
	}
	
	private void updateUI(WeatherInfo weatherInfo)
	{
		if(weatherInfo!= null)
		{
			currentWeatherInfo = weatherInfo;
			//address.setText(weatherInfo.getResult().getLocation().getAddress());
			
			mediaController.setAnchorView(videoView);
			video.setMediaController(mediaController);
			if(weatherInfo.getResult().getVideo()!= null)
				video.setVideoURI(Uri.parse(weatherInfo.getResult().getVideo()));
			
			video.requestFocus();
			progressBar.setVisibility(View.VISIBLE);
			
			video.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					
					progressBar.setVisibility(View.INVISIBLE);
					//video.pause();
					video.start();
					
				}
			});
			
			video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					progressBar.setVisibility(View.INVISIBLE);
					return false;
				}
			});
//			Toast.makeText(weatherActivity, "Address updated", Toast.LENGTH_SHORT).show();
		}
		else
		{
//			Toast.makeText(weatherActivity, "Pulling Location", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(video!= null && video.isPlaying())
			video.pause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		int          height     = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.0f,getResources().getDisplayMetrics());
		ActionBar    actionBar  = weatherActivity.getSupportActionBar();
		LayoutParams params     = videoLayout.getLayoutParams();
		
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			if(actionBar.isShowing())
				actionBar.hide();
			
			
			params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
			params.height = ViewGroup.LayoutParams.MATCH_PARENT;
			
			videoLayout.requestLayout();
		}
		else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			if(!actionBar.isShowing())
				actionBar.show();
			
			params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
			params.height = height;
			
			videoLayout.requestLayout();
		}
		
		super.onConfigurationChanged(newConfig);
	}	
}
