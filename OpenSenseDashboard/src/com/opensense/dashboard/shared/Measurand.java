package com.opensense.dashboard.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.opensense.dashboard.server.util.DataHandler;

public class Measurand implements IsSerializable{

	private final JSONObject rawJSON;
	private final int id;
	private final String name;
	private final int defaultUnitId;
	
	public Measurand(JSONObject measurand){
		this.rawJSON = measurand;
		this.id = measurand.getInt("id");
		this.name = measurand.getString("name");
		this.defaultUnitId = measurand.getInt("defaultUnitId");
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the defaultUnitId
	 */
	public int getDefaultUnitId() {
		return defaultUnitId;
	}
	
	public String toString() {
		return this.rawJSON.toString();
	}

}
