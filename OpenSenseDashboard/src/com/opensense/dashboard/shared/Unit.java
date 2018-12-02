package com.opensense.dashboard.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit implements IsSerializable{

	private int id;
	private String displayName;
	private int measurandId;
	
	public Unit() {
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
	public String getDisplayName() {
		return displayName;
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
	public void setDisplayName(String name) {
		this.displayName = name;
	}

	/**
	 * @return the measurandId
	 */
	public int getMeasurandId() {
		return measurandId;
	}

	/**
	 * @param measurandId the measurandId to set
	 */
	public void setMeasurandId(int measurandId) {
		this.measurandId = measurandId;
	}

}
