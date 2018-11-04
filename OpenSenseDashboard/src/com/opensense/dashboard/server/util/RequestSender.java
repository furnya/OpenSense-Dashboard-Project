package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gwt.json.client.JSONException;

public class RequestSender implements Serializable{
	private HashMap<String, String> parameters;
	
	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public HashMap<String,String> getParameters(){
		return parameters;
	}
	
	public String buildURL(String url) {
		if(parameters.size()==0) {
			return url;
		}
		String endURL = url+"?";
		for(String key: parameters.keySet()) {
			endURL += key;
			endURL += "=";
			endURL += parameters.get(key);
			endURL += "&";
		}
		endURL = endURL.substring(0, endURL.length()-1);
		return endURL;
	}
	
	public String sendGETRequest(String urlString){
		urlString = buildURL(urlString);
		System.out.println(urlString);
		URL url;
		HttpURLConnection con;
		StringBuffer content;
		try {
			url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		con.disconnect();
		return content.toString();
	}
	
	public JSONObject objectRequest(String urlString) {
		String response = sendGETRequest(urlString);
		try {
			JSONObject o = new JSONObject(response);
			return o;
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONArray arrayRequest(String urlString) {
		String response = sendGETRequest(urlString);
		try {
			JSONArray a = new JSONArray(response);
			return a;
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public RequestSender(){
		parameters = new HashMap<String,String>();
	}
}
