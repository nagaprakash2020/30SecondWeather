
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
	
   	private String daytime;
   	private String label;
   	private String weather;

 	public String getDaytime(){
		return this.daytime;
	}
	public void setDaytime(String daytime){
		this.daytime = daytime;
	}
 	public String getLabel(){
		return this.label;
	}
	public void setLabel(String label){
		this.label = label;
	}
 	public String getWeather(){
		return this.weather;
	}
	public void setWeather(String weather){
		this.weather = weather;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(daytime);
		dest.writeString(label);
		dest.writeString(weather);
	}
	
	public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {

		@Override
		public Weather createFromParcel(Parcel source) {
			return new Weather(source);
		}

		@Override
		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};
	
	public Weather(Parcel pc)
	{
		daytime = pc.readString();
		label   = pc.readString();
		weather = pc.readString();
	}
}
