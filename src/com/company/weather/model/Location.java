
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable{
	
   	private String address;
   	private String city;
   	private String county;
   	private String state;

 	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
 	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city = city;
	}
 	public String getCounty(){
		return this.county;
	}
	public void setCounty(String county){
		this.county = county;
	}
 	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(address);
		dest.writeString(city);
		dest.writeString(county);
		dest.writeString(state);
	}
	
	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel source) {
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
	};
	
	public Location(Parcel pc)
	{
		address = pc.readString();
		city    = pc.readString();
		county  = pc.readString();
		state   = pc.readString();
	}
	
}
