package com.opensense.dashboard.server.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

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
	
	public JSONObject sendGETRequest(String urlString) throws IOException {
		urlString = buildURL(urlString);
		System.out.println(urlString);
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		int status = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		con.disconnect();
		System.out.println(content.toString());
		JSONObject response = new JSONObject(content.toString());
		System.out.println(response.toString());
		return response;
	}
	
	public RequestSender(){
		parameters = new HashMap<String,String>();
	}
}
