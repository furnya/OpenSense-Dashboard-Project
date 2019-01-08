package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.opensense.dashboard.shared.Parameter;

public class RequestSender implements Serializable{

	private static final long serialVersionUID = 5439662018572550904L;

	private static final String APP_JSON = "application/json";
	private static final String TEXT_HTML = "text/html";

	private HashMap<String, String> parameters;
	private String parameterString = "";

	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	public Map<String,String> getParameters(){
		return this.parameters;
	}

	public void buildParameters() {
		if(this.parameters.size()==0) {
			return;
		}
		StringBuilder sb = new StringBuilder(this.getParameterString().isEmpty()? "" : this.getParameterString()+"&");
		for(Map.Entry<String, String> entry : this.parameters.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String pString = sb.toString();
		if(pString.charAt(pString.length()-1)=='&'){
			this.setParameterString(pString.substring(0, pString.length()-1));
		}else {
			this.setParameterString(pString);
		}
	}

	public String sendGETRequest(String urlString, String token) throws IOException{
		this.buildParameters();
		urlString += "?"+this.getParameterString();
		HttpURLConnection con = null;
		StringBuilder content;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setRequestProperty("Accept", APP_JSON);
			if((token!=null) && !token.isEmpty()) {
				con.setRequestProperty("Authorization", token);
			}
			con.setDoOutput(true);
			con.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			if(con != null) {
				con.disconnect();
			}
			throw e;
		}
		return content.toString();
	}

	public JSONObject objectGETRequest(String urlString) throws IOException {
		String response = this.sendGETRequest(urlString, null);
		if(response==null) {
			return null;
		}
		return new JSONObject(response);
	}

	public JSONArray arrayGETRequest(String urlString) throws IOException{
		String response = this.sendGETRequest(urlString, null);
		if(response==null) {
			return null;
		}
		return new JSONArray(response);
	}

	public JSONObject objectGETRequest(String urlString, String token) throws IOException {
		String response = this.sendGETRequest(urlString, token);
		if(response==null) {
			return null;
		}
		return new JSONObject(response);
	}

	public JSONArray arrayGETRequest(String urlString, String token) throws IOException{
		String response = this.sendGETRequest(urlString, token);
		if(response==null) {
			return null;
		}
		return new JSONArray(response);
	}

	public RequestSender(){
		this.parameters = new HashMap<>();
	}

	/**
	 * @return the parameterString
	 */
	public String getParameterString() {
		return this.parameterString;
	}

	/**
	 * @param parameterString the parameterString to set
	 */
	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}

	public void setParameters(List<Parameter> parameterList) {
		if(parameterList != null) {
			parameterList.forEach(parameter -> this.parameters.put(parameter.getKey(), parameter.getValue()));
		}
	}

	public String sendPOSTRequest(String urlString, String body, String token) throws IOException{
		HttpURLConnection con = null;
		StringBuilder content;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", APP_JSON);
			con.setRequestProperty("Content-Type", APP_JSON);
			if(token!=null) con.setRequestProperty("Authorization", token);
			con.setDoOutput(true);

			OutputStream os = con.getOutputStream();
			os.write(body.getBytes("UTF-8"));
			os.close();

			con.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			if(con != null) {
				con.disconnect();
			}
			throw e;
		}
		return content.toString();
	}

	public JSONObject objectPOSTRequest(String urlString, String body, String token) throws IOException{
		String response = this.sendPOSTRequest(urlString, body, token);
		if(response==null) {
			return null;
		}
		return new JSONObject(response);
	}


}
