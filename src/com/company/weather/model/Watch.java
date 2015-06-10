
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Watch implements Parcelable{
   	private String type;

 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(type);
	}
	
	public static final Parcelable.Creator<Watch> CREATOR = new Parcelable.Creator<Watch>() {

		@Override
		public Watch createFromParcel(Parcel source) {
			return new Watch(source);
		}

		@Override
		public Watch[] newArray(int size) {
			return new Watch[size];
		}
	};
	
	public Watch(Parcel pc)
	{
		type = pc.readString();
	}
}
