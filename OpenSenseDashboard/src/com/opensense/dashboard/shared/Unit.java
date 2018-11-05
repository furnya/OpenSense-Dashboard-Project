package com.opensense.dashboard.shared;


import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit implements IsSerializable{

	private JSONObject rawJSON;
	private int id;
	private String name;
	
	public Unit() {
	}
	
	public Unit(JSONObject unit, Measurand measurand){
		this.setRawJSON(unit);
		this.setId(unit.getInt("id"));
		this.setName(unit.getString("name"));
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

}
