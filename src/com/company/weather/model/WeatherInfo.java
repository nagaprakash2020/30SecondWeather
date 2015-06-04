
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable{
   	private Result result;

 	public Result getResult(){
		return this.result;
	}
	public void setResult(Result result){
		this.result = result;
	}
	
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeParcelable(this.result, flags);
		
	}
	
	
	
}
