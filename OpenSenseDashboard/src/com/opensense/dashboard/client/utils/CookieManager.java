package com.opensense.dashboard.client.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.user.client.Cookies;
import com.opensense.dashboard.client.AppController;

public class CookieManager {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(CookieManager.class.getName());

	public static final String DASHBOARD_LANGUAGE = "dashboard_language";
	public static final String DASHBOARD_FAVORITES = "dashboard_favorites";
	public static final String DASHBOARD_SHOW_TOURS = "dashboard_show_tours";
	public static final String DASHBOARD_FINISHED_TOURS = "dashboard_finished_tours";

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

	@SuppressWarnings("deprecation")
	public static void writeFavoriteListCookie(List<Integer> list){
		Date date = new Date();
		date.setDate(date.getDate() + 91);
		StringBuilder listAsString = new StringBuilder();
		list.forEach(item -> {
			listAsString.append(item);
			listAsString.append(",");
		});
		Cookies.setCookie(DASHBOARD_FAVORITES, listAsString.substring(0, listAsString.length() - 1), date);
	}

	public static List<Integer> getFavoriteList() {
		List<Integer> idList = new ArrayList<>();
		String name = DASHBOARD_FAVORITES;
		String cookieString = Cookies.getCookie(name);
		if((cookieString != null) && !cookieString.isEmpty()) {
			String[] slittedString = cookieString.split(",");
			for(String value : slittedString) {
				if(value.matches("^[0-9]*$")) {
					idList.add(Integer.valueOf(value));
				}
			}
		}
		return idList;
	}

	public static boolean getShowAutomaticallyTours() {
		String name = DASHBOARD_SHOW_TOURS;
		if(Cookies.getCookie(name) != null) {
			Boolean bool = Boolean.valueOf(Cookies.getCookie(name));
			if(bool != null){
				return bool;
			}
		}
		return true;
	}

	public static List<String> getFinishedTours() {
		List<String> idList = new ArrayList<>();
		String name = DASHBOARD_FINISHED_TOURS;
		String cookieString = Cookies.getCookie(name);
		if((cookieString != null) && !cookieString.isEmpty()) {
			String[] slittedString = cookieString.split(",");
			for(String value : slittedString) {
				idList.add(value);
			}
		}
		return idList;
	}

	@SuppressWarnings("deprecation")
	public static void saveFinishTours(List<String> finshedTours) {
		Date date = new Date();
		date.setDate(date.getDate() + 91);
		StringBuilder listAsString = new StringBuilder();
		finshedTours.forEach(item -> {
			listAsString.append(item);
			listAsString.append(",");
		});
		Cookies.setCookie(DASHBOARD_FINISHED_TOURS, listAsString.substring(0, listAsString.length() - 1), date);
	}

	@SuppressWarnings("deprecation")
	public static void setShowToursAutomatically(boolean show) {
		Date date = new Date();
		date.setDate(date.getDate() + 91);
		Cookies.setCookie(DASHBOARD_SHOW_TOURS, String.valueOf(show), date);
	}
}
