
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Today implements Parcelable{
	
   	private String desc;
   	private String icon;
   	private String tmp;

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
 	public String getTmp(){
		return this.tmp;
	}
	public void setTmp(String tmp){
		this.tmp = tmp;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(desc);
		dest.writeString(icon);
		dest.writeString(tmp);
	}
	
	public static final Parcelable.Creator<Today> CREATOR = new Parcelable.Creator<Today>() {

		@Override
		public Today createFromParcel(Parcel source) {
			return new Today(source);
		}

		@Override
		public Today[] newArray(int size) {
			return new Today[size];
		}
	};
	
	public Today(Parcel pc)
	{
		desc = pc.readString();
		icon = pc.readString();
		tmp  = pc.readString();
	}
}
