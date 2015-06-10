
package com.company.weather.model;

import java.util.List;

public class AutoCompletePlaces{
   	private List<Predictions>   predictions;
   	private String status;

 	public List<Predictions> getPredictions(){
		return this.predictions;
	}
	public void setPredictions(List<Predictions> predictions){
		this.predictions = predictions;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
