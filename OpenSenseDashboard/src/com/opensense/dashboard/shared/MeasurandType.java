package com.opensense.dashboard.shared;

public enum MeasurandType {
	TEMPERATURE,NOISE,HUMIDITY,BRIGHTNESS,AIR_PRESSURE,WIND_SPEED,WIND_DIRECTION,CLOUDINESS,PRECIPITATION_AMOUNT,PRECIPITATION_TYPE,PM10,PM2_5,DEFAULT;
	
	public static MeasurandType getTypeFromString(String name) {
		MeasurandType measurandType = DEFAULT;
		switch(name) {
		case "air_pressure":
			measurandType = AIR_PRESSURE;
			break;
		case "brightness":
			measurandType = BRIGHTNESS;
			break;
		case "cloudiness":
			measurandType = CLOUDINESS;
			break;
		case "humidity":
			measurandType = HUMIDITY;
			break;
		case "noise":
			measurandType = NOISE;
			break;
		case "pm10":
			measurandType = PM10;
			break;
		case "pm2_5":
			measurandType = PM2_5;
			break;
		case "precipitation_amount":
			measurandType = PRECIPITATION_AMOUNT;
			break;
		case "precipitation_type":
			measurandType = PRECIPITATION_TYPE;
			break;
		case "temperature":
			measurandType = TEMPERATURE;
			break;
		case "wind_direction":
			measurandType = WIND_DIRECTION;
			break;
		case "wind_speed":
			measurandType = WIND_SPEED;
			break;
		default:
			break;
		}
		return measurandType;
	}
}
