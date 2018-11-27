package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Map<String,String> getParameters(){
		return parameters;
	}
	
	public void buildParameters() {
		if(parameters.size()==0) {
			return;
		}
		StringBuilder sb = new StringBuilder(this.getParameterString().isEmpty()? "" : this.getParameterString()+"&");
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
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
		HttpURLConnection con;
		StringBuffer content;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.getResponseMessage();
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
	
	public JSONObject objectGETRequest(String urlString) {
		String response = sendGETRequest(urlString);
		if(response==null) {
			return null;
		}
		try {
			JSONObject o = new JSONObject(response);
			return o;
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONArray arrayGETRequest(String urlString) {
		String response = sendGETRequest(urlString);
		if(response==null) {
			return null;
		}
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
	
	public String sendPOSTRequest(String urlString, String body){
		HttpURLConnection con;
		StringBuffer content;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			
			OutputStream os = con.getOutputStream();
			os.write(body.getBytes("UTF-8"));
			os.close();
			
			con.getResponseMessage();
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
	
	public JSONObject objectPOSTRequest(String urlString, String body) {
		String response = sendPOSTRequest(urlString, body);
		if(response==null) {
			return null;
		}
		try {
			JSONObject o = new JSONObject(response);
			return o;
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
