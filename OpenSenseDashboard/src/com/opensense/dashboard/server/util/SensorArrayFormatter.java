package com.opensense.dashboard.server.util;

import java.util.ArrayList;
import java.util.List;

import cern.colt.Arrays;

public class SensorArrayFormatter {

	public List<Integer> asArray(String sensorsAsString){
		sensorsAsString = sensorsAsString.replaceAll("\\[", "");
		sensorsAsString = sensorsAsString.replaceAll("\\]", "");
		List<Integer> sensorIds = new ArrayList<>();
		if(!sensorsAsString.isEmpty()) {
			String[] slittedString = sensorsAsString.split(", ");
			for(String value : slittedString) {
				if(value.matches("^[0-9]*$")) {
					sensorIds.add(Integer.valueOf(value));
				}
			}
		}
		return sensorIds;
	}

	public String asString(List<Integer> sensorsAsArray){
		return Arrays.toString(sensorsAsArray.toArray());
	}

	public String concatenateStringArrays(String sensorsAsString, String sensorsAsString2) {
		sensorsAsString = sensorsAsString.replaceAll("\\]", ", ");
		sensorsAsString2 = sensorsAsString2.replaceAll("\\[", "");
		return sensorsAsString + sensorsAsString2;
	}
}
