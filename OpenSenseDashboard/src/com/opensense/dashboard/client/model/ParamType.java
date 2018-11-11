package com.opensense.dashboard.client.model;

public enum ParamType {
	
	MEASURAND_ID {@Override public String getValue() {return "measurandId";}}
	,BOUNDING_BOX {@Override public String getValue() {return "boundingBox";}}
	,MIN_ACCURACY {@Override public String getValue() {return "minAccuracy";}}
	,MAX_ACCURACY {@Override public String getValue() {return "maxAccuracy";}}
	,MAX_SENSORS {@Override public String getValue() {return "maxSensors";}}
	;
	
	public abstract String getValue();
}
