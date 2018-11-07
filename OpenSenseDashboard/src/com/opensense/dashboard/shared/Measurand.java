package com.opensense.dashboard.shared;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Measurand implements IsSerializable{

	private int id;
	private String name;
	private int defaultUnitId;
	
	public Measurand() {
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
