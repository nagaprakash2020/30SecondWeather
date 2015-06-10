
package com.company.weather.model;

import java.util.List;

public class Bounds_ZipCode{
   	private Northeast_ZipCode northeast;
   	private Southwest_ZipCode southwest;

 	public Northeast_ZipCode getNortheast(){
		return this.northeast;
	}
	public void setNortheast(Northeast_ZipCode northeast){
		this.northeast = northeast;
	}
 	public Southwest_ZipCode getSouthwest(){
		return this.southwest;
	}
	public void setSouthwest(Southwest_ZipCode southwest){
		this.southwest = southwest;
	}
}
