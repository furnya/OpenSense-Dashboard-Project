package com.opensense.dashboard.client.utils;

/**
 * The languages are set to german by default to change it use the setter
 * @author Roeber
 */
public class Languages {
	
	/**
	 * German or english
	 */
	private static boolean de = true;
	private static boolean en = false;
	
	private Languages() {
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
	
	public static String text() {
		if(de) {
			return "Das ist der text";
		}else {
			return "";
		}
	}

	public static String home() {
		if(de) {
			return "Zuhause";
		}else {
			return "Home";
		}
	}
	
	public static String search() {
		if(de) {
			return "Suche";
		}else {
			return "Search";
		}
	}
	
	public static String map() {
		if(de) {
			return "Karte";
		}else {
			return "Map";
		}
	}
	
	public static String graphics() {
		if(de) {
			return "Grafiken";
		}else {
			return "Visualisations";
		}
	}
	
	public static String list() {
		if(de) {
			return "Listen";
		}else {
			return "Lists";
		}
	}
	
	public static String user() {
		if(de) {
			return "Benutzer";
		}else {
			return "User";
		}
	}

	public static String errorDataPanelPageLoading() {
		if(de) {
			return "Fehler beim laden der Seite";
		}else {
			return "Erroe while loading the page";
		}
	}
	
	public static String searchPlace() {
		if(de) {
			return "Sensoren Ortsabhängig suchen";
		}else {
			return "Sensors ..";
		}
	}
	
	public static String maxSensors() {
		if(de) {
			return "Maximale Anzahl von Sensoren";
		}else {
			return "";
		}
	}
	
	public static String minAccuracy() {
		if(de) {
			return "Min Genauigkeit";
		}else {
			return "";
		}
	}
	
	public static String maxAccuracy() {
		if(de) {
			return "Max Genauigkeit";
		}else {
			return "";
		}
	}
	
	public static String unit() {
		if(de) {
			return "Einheit";
		}else {
			return "";
		}
	}
	
	public static String minValue() {
		if(de) {
			return "Min Wert";
		}else {
			return "";
		}
	}
	
	public static String maxValue() {
		if(de) {
			return "Max Wert";
		}else {
			return "";
		}
	}
	
	public static String minDate() {
		if(de) {
			return "Min Datum";
		}else {
			return "";
		}
	}
	
	public static String maxDate() {
		if(de) {
			return "Max Datum";
		}else {
			return "";
		}
	}
	
	public static String minTime() {
		if(de) {
			return "Min Zeit";
		}else {
			return "";
		}
	}
	
	public static String maxTime() {
		if(de) {
			return "Max Zeit";
		}else {
			return "";
		}
	}
	
	public static String type() {
		if(de) {
			return "Typ";
		}else {
			return "";
		}
	}
}
