package com.opensense.dashboard.server.util;

import com.opensense.dashboard.shared.MeasurandType;

public class ServerLanguages {
	/**
	 * German or english
	 */
	private static boolean de = true;
	private static boolean en = false;
	private static boolean es = false;

	private ServerLanguages() {
		// Empty private constructor to hide the implicit public one.
	}

	public static boolean isGerman() {
		return de;
	}

	public static boolean isEnglish() {
		return en;
	}

	public static boolean isSpanish() {
		return es;
	}

	public static void setGerman() {
		de = true;
		en = false;
		es = false;
	}

	public static void setEnglish() {
		de = false;
		en = true;
		es = false;
	}

	public static void setSpanish() {
		de = false;
		en = false;
		es = true;
	}

	public static String getMeasurandName(MeasurandType measurandType) {
		String measurandName = null;
		switch (measurandType) {
		case AIR_PRESSURE:
			if (de) {
				measurandName = "Luftdruck";
			} else if (en) {
				measurandName = "Air pressure";
			} else {
				measurandName = "Presi\u00F3n del aire";
			}
			break;
		case BRIGHTNESS:
			if (de) {
				measurandName = "Helligkeit";
			} else if (en) {
				measurandName = "Brightness";
			} else {
				measurandName = "Brillo";
			}
			break;
		case CLOUDINESS:
			if (de) {
				measurandName = "Bew\u00F6lkung";
			} else if (en) {
				measurandName = "Cloudiness";
			} else {
				measurandName = "Abundancia de nubes";
			}
			break;
		case HUMIDITY:
			if (de) {
				measurandName = "Luftfeuchtigkeit";
			} else if (en) {
				measurandName = "Humidity";
			} else {
				measurandName = "Humedad";
			}
			break;
		case NOISE:
			if (de) {
				measurandName = "Lautst\u00E4rke";
			} else if (en) {
				measurandName = "Noise";
			}else {
				measurandName = "Intensidad de sonido";
			}
			break;
		case DEFAULT:
			break;
		case PM10:
			if (de) {
				measurandName = "PM10";
			} else if (en) {
				measurandName = "PM10";
			}else {
				measurandName = "PM10";
			}
			break;
		case PM2_5:
			if (de) {
				measurandName = "PM2,5";
			} else if (en) {
				measurandName = "PM2.5";
			}else {
				measurandName = "PM2,5";
			}
			break;
		case PRECIPITATION_AMOUNT:
			if (de) {
				measurandName = "Niederschlagsmenge";
			} else if (en) {
				measurandName = "Precipitation amount";
			}else {
				measurandName = "Cantidad de precipitaci\u00F3n";
			}
			break;
		case PRECIPITATION_TYPE:
			if (de) {
				measurandName = "Niederschlagstyp";
			} else if (en) {
				measurandName = "Precipitation type";
			}else {
				measurandName = "Tipo de precipitaci\u00F3n";
			}
			break;
		case TEMPERATURE:
			if (de) {
				measurandName = "Temperatur";
			} else if (en) {
				measurandName = "Temperature";
			}else {
				measurandName = "Temperatura";
			}
			break;
		case WIND_DIRECTION:
			if (de) {
				measurandName = "Windrichtung";
			} else if (en) {
				measurandName = "Wind direction";
			}else {
				measurandName = "Direcci\u00F3n del viento";
			}
			break;
		case WIND_SPEED:
			if (de) {
				measurandName = "Windgeschwindigkeit";
			} else if (en) {
				measurandName = "Wind speed";
			}else {
				measurandName = "Velocidad del viento";
			}
			break;
		case CO:
			if (de) {
				measurandName = "Kohlenstoffmonoxid";
			} else if (en) {
				measurandName = "Carbon monoxide";
			} else {
				measurandName = "mon\u00F3xido de carb\u00F3n";
			}
			break;
		case NO2:
			if (de) {
				measurandName = "Stickstoffdioxid";
			} else if (en) {
				measurandName = "Nitrogen dioxide";
			} else {
				measurandName = "Dioxido de nitrogeno";
			}
			break;
		case O3:
			if (de) {
				measurandName = "Sauerstoff";
			} else if (en) {
				measurandName = "Oxygen";
			} else {
				measurandName = "Ox\u00EDgeno";
			}
			break;
		case SO2:
			if (de) {
				measurandName = "Schwefeldioxid";
			} else if (en) {
				measurandName = "Sulfur dioxide";
			}else {
				measurandName = "Di\u00F3xido de azufre";
			}
			break;
		case SOLAR_RADIATION:
			if (de) {
				measurandName = "Sonnenstrahlung";
			} else if (en) {
				measurandName = "Solar radiation";
			}else {
				measurandName = "Radiaci\u00F3n solar";
			}
			break;
		default:
			break;
		}
		return measurandName;
	}

	public static String getUnitName(UnitType unitType) {
		String unitName = null;
		switch (unitType) {
		case CELSIUS:
			unitName = "\u00BAC";
			break;
		case DECIBEL:
			unitName = "dB";
			break;
		case DEGREES:
			unitName = "\u00BA";
			break;
		case ENUM:
			unitName = "";
			break;
		case FAHRENHEIT:
			unitName = "\u00BAF";
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

	public static String newList() {
		if (de) {
			return "Neue Liste";
		} else if (en) {
			return "New list";
		}else {
			return "Lista nueva";
		}
	}

	public static String wrongPassword() {
		if (de) {
			return "Das eingegebende Passwort ist nicht korrekt";
		} else if (en) {
			return "The entered password is not correct";
		} else {
			return "La contrase\u00F1a introducida es incorrecta";
		}
	}
}
