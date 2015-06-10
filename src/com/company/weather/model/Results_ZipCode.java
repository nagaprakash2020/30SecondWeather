
package com.company.weather.model;

import java.util.List;

public class Results_ZipCode{
	
   	private List<Address_components_ZipCode> address_components;
   	private String                           formatted_address;
   	private Geometry_ZipCode                 geometry;
   	private String                           place_id;
   	private List                             postcode_localities;
   	private List                             types;

 	public List<Address_components_ZipCode> getAddress_components(){
		return this.address_components;
	}
	public void setAddress_components(List<Address_components_ZipCode> address_components){
		this.address_components = address_components;
	}
 	public String getFormatted_address(){
		return this.formatted_address;
	}
	public void setFormatted_address(String formatted_address){
		this.formatted_address = formatted_address;
	}
 	public Geometry_ZipCode getGeometry(){
		return this.geometry;
	}
	public void setGeometry(Geometry_ZipCode geometry){
		this.geometry = geometry;
	}
 	public String getPlace_id(){
		return this.place_id;
	}
	public void setPlace_id(String place_id){
		this.place_id = place_id;
	}
 	public List getPostcode_localities(){
		return this.postcode_localities;
	}
	public void setPostcode_localities(List postcode_localities){
		this.postcode_localities = postcode_localities;
	}
 	public List getTypes(){
		return this.types;
	}
	public void setTypes(List types){
		this.types = types;
	}
}
