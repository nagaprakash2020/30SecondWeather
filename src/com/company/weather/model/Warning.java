
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Warning implements Parcelable{
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
	
	public static final Parcelable.Creator<Warning> CREATOR = new Parcelable.Creator<Warning>() {

		@Override
		public Warning createFromParcel(Parcel source) {
			return new Warning(source);
		}

		@Override
		public Warning[] newArray(int size) {
			return new Warning[size];
		}
	};
	
	public Warning(Parcel pc)
	{
		type = pc.readString();
	}
}
