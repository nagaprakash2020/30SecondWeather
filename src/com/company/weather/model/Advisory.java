
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Advisory implements Parcelable{
	
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
	
	public static final Parcelable.Creator<Advisory> CREATOR = new Parcelable.Creator<Advisory>() {

		@Override
		public Advisory createFromParcel(Parcel source) {
			return new Advisory(source);
		}

		@Override
		public Advisory[] newArray(int size) {
			return new Advisory[size];
		}
	};
	
	public Advisory(Parcel pc)
	{
		type = pc.readString();
	}
}
