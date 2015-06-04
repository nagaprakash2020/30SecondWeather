
package com.company.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wwa implements Parcelable{
	
   	private Advisory advisory;
   	private Warning  warning;
   	private Watch    watch;

 	public Advisory getAdvisory(){
		return this.advisory;
	}
	public void setAdvisory(Advisory advisory){
		this.advisory = advisory;
	}
 	public Warning getWarning(){
		return this.warning;
	}
	public void setWarning(Warning warning){
		this.warning = warning;
	}
 	public Watch getWatch(){
		return this.watch;
	}
	public void setWatch(Watch watch){
		this.watch = watch;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeParcelable(advisory, flags);
		dest.writeParcelable(warning, flags);
		dest.writeParcelable(watch, flags);
	}
	
	public static final Parcelable.Creator<Wwa> CREATOR = new Parcelable.Creator<Wwa>() {

		@Override
		public Wwa createFromParcel(Parcel source) {
			return new Wwa(source);
		}

		@Override
		public Wwa[] newArray(int size) {
			return new Wwa[size];
		}
	};
	
	public Wwa(Parcel pc)
	{
		advisory = pc.readParcelable(Advisory.class.getClassLoader());
		warning  = pc.readParcelable(Warning.class.getClassLoader());
		watch    = pc.readParcelable(Warning.class.getClassLoader());
	}
}
