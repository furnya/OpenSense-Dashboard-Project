package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchRequest implements IsSerializable{
	
//	private LatLng position;
//	private Boundary boundary;
	private List<String> type;
	private int maxSensors = 20000;
	private Double maxValue;
	private Double minValue;
	private Double maxAccuracy;
	private Double minAccuracy;
	private String minTimeStamp;
	private String maxTimeStamp;
	
	public SearchRequest() {
	}
	
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public int getMaxSensors() {
		return maxSensors;
	}
	public void setMaxSensors(int maxSensors) {
		this.maxSensors = maxSensors;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	public Double getMinValue() {
		return minValue;
	}
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	public Double getMaxAccuracy() {
		return maxAccuracy;
	}
	public void setMaxAccuracy(Double maxAccuracy) {
		this.maxAccuracy = maxAccuracy;
	}
	public Double getMinAccuracy() {
		return minAccuracy;
	}
	public void setMinAccuracy(Double minAccuracy) {
		this.minAccuracy = minAccuracy;
	}
	public String getMinTimeStamp() {
		return minTimeStamp;
	}
	public void setMinTimeStamp(String minTimeStamp) {
		this.minTimeStamp = minTimeStamp;
	}
	public String getMaxTimeStamp() {
		return maxTimeStamp;
	}
	public void setMaxTimeStamp(String maxTimeStamp) {
		this.maxTimeStamp = maxTimeStamp;
	}
}
