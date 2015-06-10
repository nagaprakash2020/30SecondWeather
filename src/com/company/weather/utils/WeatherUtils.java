package com.company.weather.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.text.format.DateFormat;

public class WeatherUtils {

	public static boolean isLocationEnabled(Context context) {
	    int locationMode = 0;
	    String locationProviders;
	 
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
	        try { 
	            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
	 
	        } catch (SettingNotFoundException e) {
	            e.printStackTrace();
	        } 
	 
	        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
	 
	    }else{ 
	        locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        return !TextUtils.isEmpty(locationProviders);
	    } 
	}
	
	public static String getLocationUrl(Location location,Context context)
	{
		Geocoder      geoCoder;
		List<Address> addresses;
		Address       address;
		Calendar      now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String        locationUrl = "http://www.30secondweather.com/videos/";
		
		try 
		{
			geoCoder     = new Geocoder(context);
			addresses    = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			address      = addresses== null ?null:addresses.get(0);
// http://www.30secondweather.com/videos/Miramar025.978693700080.29173020002015040116/play.mp4
// http://www.30secondweather.com/videos/Miramar025.978618000080.29184160002015040116/play.mp4	
// 			
			
			if(address != null)
			{
//				locationUrl = locationUrl + address.getLocality() +"0"+location.getLatitude()+"000"+Math.abs(location.getLongitude())+"000"+now.get(Calendar.YEAR)+
//						now.get(Calendar.MONTH)+now.get(Calendar.DAY_OF_MONTH)+now.get(Calendar.HOUR_OF_DAY)+"/play.mp4";
			
				locationUrl = locationUrl + address.getLocality() +"0"+location.getLatitude()+"000"+Math.abs(location.getLongitude())+"000"+sdf.format(now.getTime())
						+now.get(Calendar.HOUR_OF_DAY)+"/play.mp4";
			}
			else
			{
				locationUrl = null;
			}
			
		} catch (IOException e) {
			locationUrl = null;
			e.printStackTrace();
		}
		
		return locationUrl;
	}
	
	public static String getHourlyTime(String originalTime)
	{
		SimpleDateFormat original = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",Locale.getDefault());
	    SimpleDateFormat target   = new SimpleDateFormat("h a", Locale.getDefault());
	    Date             date     = null;
		try {
			date = original.parse(originalTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return target.format(date);
	}
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false; 
		} 
		int length = str.length();
		if (length == 0) {
			return false; 
		} 
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false; 
			} 
			i = 1;
		} 
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false; 
			} 
		} 
		return true; 
	} 
}
