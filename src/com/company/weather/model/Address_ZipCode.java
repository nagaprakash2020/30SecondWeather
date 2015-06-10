
package com.company.weather.model;

import java.util.List;

public class Address_ZipCode{
	
   	private boolean               exclude_from_slo;
   	private List<Results_ZipCode> results;
   	private String                status;

 	public boolean getExclude_from_slo(){
		return this.exclude_from_slo;
	}
	public void setExclude_from_slo(boolean exclude_from_slo){
		this.exclude_from_slo = exclude_from_slo;
	}
 	public List<Results_ZipCode> getResults(){
		return this.results;
	}
	public void setResults(List<Results_ZipCode> results){
		this.results = results;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
