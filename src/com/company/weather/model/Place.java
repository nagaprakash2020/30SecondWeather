package com.company.weather.model;
/*
 * This object holds data from the search results 
 * from the autocomplete view in PlacesFragment.
 */

public class Place{

	// Currently not using ID
	String id;
	String city;
	String State;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		boolean isEqual = false;
		
		if(obj instanceof Place)
		{
			Place place = (Place) obj;
			if(place.city.equals(this.city))
				isEqual = true;
		}
		
		return isEqual;
	}
}
