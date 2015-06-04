
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Current implements Parcelable{
	
   	private String icon;
   	private String maxt;
   	private String relative_humidity;
   	private String temp_f;
   	private Weather weather;
   	private String wind_dir;
   	private String wind_mph;

 	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
 	public String getMaxt(){
		return this.maxt;
	}
	public void setMaxt(String maxt){
		this.maxt = maxt;
	}
 	public String getRelative_humidity(){
		return this.relative_humidity;
	}
	public void setRelative_humidity(String relative_humidity){
		this.relative_humidity = relative_humidity;
	}
 	public String getTemp_f(){
		return this.temp_f;
	}
	public void setTemp_f(String temp_f){
		this.temp_f = temp_f;
	}
 	public Weather getWeather(){
		return this.weather;
	}
	public void setWeather(Weather weather){
		this.weather = weather;
	}
 	public String getWind_dir(){
		return this.wind_dir;
	}
	public void setWind_dir(String wind_dir){
		this.wind_dir = wind_dir;
	}
 	public String getWind_mph(){
		return this.wind_mph;
	}
	public void setWind_mph(String wind_mph){
		this.wind_mph = wind_mph;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(icon);
		dest.writeString(maxt);
		dest.writeString(relative_humidity);
		dest.writeString(temp_f);
		dest.writeParcelable(weather, flags);
		dest.writeString(wind_dir);
		dest.writeString(wind_mph);
	}
}
