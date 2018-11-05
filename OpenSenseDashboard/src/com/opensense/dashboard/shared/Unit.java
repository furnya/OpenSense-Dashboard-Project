package com.opensense.dashboard.shared;

import java.util.HashMap;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit implements IsSerializable{

	private final JSONObject rawJSON;
	private final int id;
	private final String name;
	
	public Unit(JSONObject unit, Measurand measurand){
		this.rawJSON = unit;
		this.id = unit.getInt("id");
		this.name = unit.getString("name");
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
		return this.rawJSON.toString();
	}

}
