package com.opensense.dashboard.server.util;

public enum JsonAttributes {
	ID("id"),
	SENSOR_ID("sensorId"),
	NAME("name"),
	MEASURAND_ID("measurandId"),
	DEFAULT_UNIT_ID("defaultUnitId"),
	USER_ID("userId"),
	UNIT_ID("unitId"),
	LOCATION("location"),
	ALTITUDE_ABOVE_GROUND("altitudeAboveGround"),
	DIRECTION_VERTICAL("directionVertical"),
	DIRECTION_HORIZONTAL("directionHorizontal"),
	SENSOR_MODEL("sensorModel"),
	ACCURACY("accuracy"),
	ATTRIBUTION_TEXT("attributionText"),
	ATTRIBUTION_URL("attributionURL"),
	LICENSE_ID("licenseId"),
	LAT("lat"),
	LNG("lng"),
	NUMBER_VALUE("numberValue"),
	TIMESTAMP("timestamp"),
	FULL_NAME("fullName"),
	COLLAPSED_MESSAGES("collapsedMessages");
	
	private final String nameString;
	
	JsonAttributes(String nameString) {
		this.nameString = nameString;
	}
	
	public String getNameString() {
		return this.nameString;
	}
}
