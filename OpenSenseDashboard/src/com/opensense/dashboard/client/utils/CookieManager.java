package com.opensense.dashboard.client.utils;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.user.client.Cookies;
import com.opensense.dashboard.client.AppController;

public class CookieManager {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(CookieManager.class.getName());
	 
	public static final String DASHBOARD_LANGUAGE = "dashboard_language";
	public static final String DASHBOARD_COOKIE = "dashboard_cookie";
	
	@SuppressWarnings("unused")
	private static AppController appController;
	
	private CookieManager() {
	}
	
	/**
	 * Default if there is no cookie is German 
	 * @returns true if the language is english, false if the language is german
	 */
	public static String getLanguage(){
		String name = DASHBOARD_LANGUAGE;
		if(Cookies.getCookie(name) != null) {
			return Cookies.getCookie(name);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static void writeLanguageCookie(String language){
		Date date = new Date();
		date.setDate(date.getDate() + 91);
		Cookies.setCookie(DASHBOARD_LANGUAGE, language, date);
	}
}
