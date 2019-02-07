package com.opensense.dashboard.client.model;

/**
 * The different parameter which are accepted in the URL and send to our server
 * @author carlr
 *
 */
public enum ParamType {

	MEASURAND_ID {@Override public String getValue() {return "measurandId";}}
	,BOUNDING_BOX {@Override public String getValue() {return "boundingBox";}}
	,MIN_ACCURACY {@Override public String getValue() {return "minAccuracy";}}
	,MAX_ACCURACY {@Override public String getValue() {return "maxAccuracy";}}
	,MAX_SENSORS {@Override public String getValue() {return "maxSensors";}}
	,MIN_TIMESTAMP {@Override public String getValue() {return "minTimestamp";}}
	,MAX_TIMESTAMP {@Override public String getValue() {return "maxTimestamp";}}
	;

	public abstract String getValue();
}
