package com.opensense.dashboard.server.util;

import com.opensense.dashboard.shared.MeasurandType;

public class ServerLanguages {
	
	/**
	 * German or english
	 */
	private static boolean de = true;
	private static boolean en = false;
	
	private ServerLanguages() {
	    // Empty private constructor to hide the implicit public one.
	}
	
	public static boolean isGerman() {
		return de;
	}
	
	public static boolean isEnglish() {
		return en;
	}
	
	public static void setGerman() {
		de = true;
		en = false;
	}
	
	public static void setEnglish() {
		de = false;
		en = true;
	}
	
	public static String languageChange() {
		if(de) {
			return "EN";
		}else {
			return "DE";
		}
	}
	
	public static String getMeasurandName(MeasurandType measurandType) {
		String measurandName = null;
		switch(measurandType) {
		case AIR_PRESSURE:
			if(de) {
				measurandName = "Luftdruck";
			} else {
				measurandName = "air pressure";
			}
			break;
		case BRIGHTNESS:
			if(de) {
				measurandName = "Helligkeit";
			} else {
				measurandName = "brightness";
			}
			break;
		case CLOUDINESS:
			if(de) {
				measurandName = "Bewölkung";
			} else {
				measurandName = "cloudiness";
			}
			break;
		case HUMIDITY:
			if(de) {
				measurandName = "Luftfeuchtigkeit";
			} else {
				measurandName = "humidity";
			}
			break;
		case NOISE:
			if(de) {
				measurandName = "Lautstärke";
			} else {
				measurandName = "noise";
			}
			break;
		case DEFAULT:
			break;
		case PM10:
			if(de) {
				measurandName = "PM10";
			} else {
				measurandName = "PM10";
			}
			break;
		case PM2_5:
			if(de) {
				measurandName = "PM2,5";
			} else {
				measurandName = "PM2.5";
			}
			break;
		case PRECIPITATION_AMOUNT:
			if(de) {
				measurandName = "Niederschlagsmenge";
			} else {
				measurandName = "precipitation amount";
			}
			break;
		case PRECIPITATION_TYPE:
			if(de) {
				measurandName = "Niederschlagstyp";
			} else {
				measurandName = "precipitation type";
			}
			break;
		case TEMPERATURE:
			if(de) {
				measurandName = "Temperatur";
			} else {
				measurandName = "temperature";
			}
			break;
		case WIND_DIRECTION:
			if(de) {
				measurandName = "Windrichtung";
			} else {
				measurandName = "wind direction";
			}
			break;
		case WIND_SPEED:
			if(de) {
				measurandName = "Windgeschwindigkeit";
			} else {
				measurandName = "wind speed";
			}
			break;
		default:
			break;
		}
		return measurandName;
	}
}
