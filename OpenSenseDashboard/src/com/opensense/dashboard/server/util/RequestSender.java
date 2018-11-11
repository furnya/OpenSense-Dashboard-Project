package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gwt.json.client.JSONException;
import com.opensense.dashboard.shared.Parameter;

public class RequestSender implements Serializable{
	
	private HashMap<String, String> parameters;
	private String parameterString = "";
	
	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public HashMap<String,String> getParameters(){
		return parameters;
	}
	
	public void buildParameters() {
		if(parameters.size()==0) {
			return;
		}
		StringBuilder sb = new StringBuilder(this.getParameterString().isEmpty()? "" : this.getParameterString()+"&");
		for(String key: parameters.keySet()) {
			sb.append(key).append("=").append(parameters.get(key)).append("&");
		}
		String pString = sb.toString();
		if(pString.charAt(pString.length()-1)=='&'){
			this.setParameterString(pString.substring(0, pString.length()-1));
		}else {
			this.setParameterString(pString);
		}
	}
	
	public String sendGETRequest(String urlString){
		buildParameters();
		urlString += "?"+this.getParameterString();
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

	/**
	 * @return the parameterString
	 */
	public String getParameterString() {
		return parameterString;
	}

	/**
	 * @param parameterString the parameterString to set
	 */
	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}
	
	public void setParameters(List<Parameter> parameterList) {
		parameterList.forEach(parameter -> this.parameters.put(parameter.getKey(), parameter.getValue()));
	}
}
