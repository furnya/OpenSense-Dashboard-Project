package com.opensense.dashboard.server.util;

import com.opensense.dashboard.shared.MeasurandType;

public class ServerLanguages{

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

	public static String getMeasurandName(MeasurandType measurandType) {
		String measurandName = null;
		switch(measurandType) {
		case AIR_PRESSURE:
			if(de) {
				measurandName = "Luftdruck";
			} else {
				measurandName = "Air pressure";
			}
			break;
		case BRIGHTNESS:
			if(de) {
				measurandName = "Helligkeit";
			} else {
				measurandName = "Brightness";
			}
			break;
		case CLOUDINESS:
			if(de) {
				measurandName = "Bew\u00F6lkung";
			} else {
				measurandName = "Cloudiness";
			}
			break;
		case HUMIDITY:
			if(de) {
				measurandName = "Luftfeuchtigkeit";
			} else {
				measurandName = "Humidity";
			}
			break;
		case NOISE:
			if(de) {
				measurandName = "Lautst\u00E4rke";
			} else {
				measurandName = "Noise";
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
				measurandName = "Precipitation amount";
			}
			break;
		case PRECIPITATION_TYPE:
			if(de) {
				measurandName = "Niederschlagstyp";
			} else {
				measurandName = "Precipitation type";
			}
			break;
		case TEMPERATURE:
			if(de) {
				measurandName = "Temperatur";
			} else {
				measurandName = "Temperature";
			}
			break;
		case WIND_DIRECTION:
			if(de) {
				measurandName = "Windrichtung";
			} else {
				measurandName = "Wind direction";
			}
			break;
		case WIND_SPEED:
			if(de) {
				measurandName = "Windgeschwindigkeit";
			} else {
				measurandName = "Wind speed";
			}
			break;
		case CO:
			if(de) {
				measurandName = "Kohlenstoffmonoxid";
			}else {
				measurandName = "Carbon monoxide";
			}
			break;
		case NO2:
			if(de) {
				measurandName = "Stickstoffdioxid";
			}else {
				measurandName = "Nitrogen dioxide";
			}
			break;
		case O3:
			if(de) {
				measurandName = "Sauerstoff";
			}else {
				measurandName = "Oxygen";
			}
			break;
		case SO2:
			if(de) {
				measurandName = "Schwefeldioxid";
			}else {
				measurandName = "Sulfur dioxide";
			}
			break;
		case SOLAR_RADIATION:
			if(de) {
				measurandName = "Sonnenstrahlung";
			}else {
				measurandName = "Solar radiation";
			}
			break;
		default:
			break;
		}
		return measurandName;
	}

	public static String getUnitName(UnitType unitType) {
		String unitName = null;
		switch(unitType) {
		case CELSIUS:
			unitName = "°C";
			break;
		case DECIBEL:
			unitName = "dB";
			break;
		case DEGREES:
			unitName = "°";
			break;
		case ENUM:
			unitName = "";
			break;
		case FAHRENHEIT:
			unitName = "°F";
			break;
		case HPA:
			unitName = "hPa";
			break;
		case KELVIN:
			unitName = "K";
			break;
		case KMH:
			unitName = "km/h";
			break;
		case LEVEL:
			unitName = "";
			break;
		case LUMEN:
			unitName = "lm";
			break;
		case LUX:
			unitName = "lx";
			break;
		case MBAR:
			unitName = "mbar";
			break;
		case MM:
			unitName = "mm";
			break;
		case MPS:
			unitName = "m/s";
			break;
		case PERCENT:
			unitName = "%";
			break;
		case UGPM3:
			unitName = "\u00B5g/m\u00B3";
			break;
		case JPCM2:
			unitName = "J/cm\u00B2";
			break;
		default:
			break;
		}
		return unitName;
	}

	public static String unexpectedErrorLog() {
		return "Unexpected error";
	}
}
