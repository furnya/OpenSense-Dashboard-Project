package com.opensense.dashboard.shared;

public enum MeasurandType {
	TEMPERATURE("temperature"),
	NOISE("noise"),
	HUMIDITY("humidity"),
	BRIGHTNESS("brightness"),
	AIR_PRESSURE("air_pressure"),
	WIND_SPEED("wind_speed"),
	WIND_DIRECTION("wind_direction"),
	CLOUDINESS("cloudiness"),
	PRECIPITATION_AMOUNT("precipitation_amount"),
	PRECIPITATION_TYPE("precipitation_type"),
	PM10("pm10"),
	PM2_5("pm2_5"),
	SOLAR_RADIATION("solar_radiation"),
	CO("CO"),
	NO2("NO2"),
	O3("O3"),
	SO2("SO2"),
	DEFAULT("");

	private final String nameString;
	
	MeasurandType(String nameString) {
		this.nameString = nameString;
	}
	
	public String getNameString() {
		return this.nameString;
	}
}
