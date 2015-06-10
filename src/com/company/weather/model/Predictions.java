
package com.company.weather.model;

import java.util.List;

public class Predictions{
   	private String                   description;
   	private String                   id;
   	private List<Matched_substrings> matched_substrings;
   	private String                   place_id;
   	private String                   reference;
   	private List<Terms>              terms;
   	private List                     types;

 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public List<Matched_substrings> getMatched_substrings(){
		return this.matched_substrings;
	}
	public void setMatched_substrings(List<Matched_substrings> matched_substrings){
		this.matched_substrings = matched_substrings;
	}
 	public String getPlace_id(){
		return this.place_id;
	}
	public void setPlace_id(String place_id){
		this.place_id = place_id;
	}
 	public String getReference(){
		return this.reference;
	}
	public void setReference(String reference){
		this.reference = reference;
	}
 	public List<Terms> getTerms(){
		return this.terms;
	}
	public void setTerms(List<Terms> terms){
		this.terms = terms;
	}
 	public List getTypes(){
		return this.types;
	}
	public void setTypes(List types){
		this.types = types;
	}
}
