package com.opensense.dashboard.shared;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Measurand implements IsSerializable{

	private JSONObject rawJSON;
	private int id;
	private String name;
	private int defaultUnitId;
	
	public Measurand() {
	}
	
	public Measurand(JSONObject measurand){
		this.setRawJSON(measurand);
		this.setId(measurand.getInt("id"));
		this.setName(measurand.getString("name"));
		this.setDefaultUnitId(measurand.getInt("defaultUnitId"));
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
		return this.getRawJSON().toString();
	}


	/**
	 * @return the rawJSON
	 */
	public JSONObject getRawJSON() {
		return rawJSON;
	}


	/**
	 * @param rawJSON the rawJSON to set
	 */
	public void setRawJSON(JSONObject rawJSON) {
		this.rawJSON = rawJSON;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param defaultUnitId the defaultUnitId to set
	 */
	public void setDefaultUnitId(int defaultUnitId) {
		this.defaultUnitId = defaultUnitId;
	}

}
