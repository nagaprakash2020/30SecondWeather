
package com.company.weather.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable{
	
   	private Current      current;
   	private List<Hourly> hourly;
   	private Location     location;
   	private Today        today;
   	private String       video;
   	private Wwa          wwa;

 	public Current getCurrent(){
		return this.current;
	}
	public void setCurrent(Current current){
		this.current = current;
	}
 	public List<Hourly> getHourly(){
		return this.hourly;
	}
	public void setHourly(List<Hourly> hourly){
		this.hourly = hourly;
	}
 	public Location getLocation(){
		return this.location;
	}
	public void setLocation(Location location){
		this.location = location;
	}
 	public Today getToday(){
		return this.today;
	}
	public void setToday(Today today){
		this.today = today;
	}
 	public String getVideo(){
		return this.video;
	}
	public void setVideo(String video){
		this.video = video;
	}
 	public Wwa getWwa(){
		return this.wwa;
	}
	public void setWwa(Wwa wwa){
		this.wwa = wwa;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeParcelable(current, flags);
		dest.writeTypedList(hourly);
		dest.writeParcelable(location, flags);
		dest.writeParcelable(today, flags);
		dest.writeString(video);
		dest.writeParcelable(wwa, flags);
		
	}
}
