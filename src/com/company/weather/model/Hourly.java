
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hourly implements Parcelable{
	
   	private String desc;
   	private String icon;
   	private String temp;
   	private String time;
   	
 	public String getDesc(){
		return this.desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
 	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
 	public String getTemp(){
		return String.format("%sF", this.temp + (char) 0x00B0);
	}
	public void setTemp(String temp){
		this.temp = temp;
	}
 	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time = time;
	}
	

	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(desc);
		dest.writeString(icon);
		dest.writeString(temp);
		dest.writeString(time);
	}
	
	public static final Parcelable.Creator<Hourly> CREATOR = new Parcelable.Creator<Hourly>() {

		@Override
		public Hourly createFromParcel(Parcel source) {
			return new Hourly(source);
		}

		@Override
		public Hourly[] newArray(int size) {
			return new Hourly[size];
		}
	};
	
	public Hourly(Parcel pc)
	{
		desc = pc.readString();
		icon = pc.readString();
		temp = pc.readString();
		time = pc.readString();
	}
}
