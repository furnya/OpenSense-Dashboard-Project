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
	
	public static String seach() {
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
}
