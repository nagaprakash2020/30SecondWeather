
package com.company.weather.model;

public class Geometry_ZipCode{
	
   	private Bounds_ZipCode   bounds;
   	private Location_ZipCode location_ZipCode;
   	private String           location_type;
   	private Viewport_ZipCode viewport;

 	public Bounds_ZipCode getBounds(){
		return this.bounds;
	}
	public void setBounds(Bounds_ZipCode bounds){
		this.bounds = bounds;
	}
 	public Location_ZipCode getLocation(){
		return this.location_ZipCode;
	}
	public void setLocation(Location_ZipCode location_ZipCode){
		this.location_ZipCode = location_ZipCode;
	}
 	public String getLocation_type(){
		return this.location_type;
	}
	public void setLocation_type(String location_type){
		this.location_type = location_type;
	}
 	public Viewport_ZipCode getViewport(){
		return this.viewport;
	}
	public void setViewport(Viewport_ZipCode viewport){
		this.viewport = viewport;
	}
}
