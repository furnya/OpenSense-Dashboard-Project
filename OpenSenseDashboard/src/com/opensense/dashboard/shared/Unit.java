package com.opensense.dashboard.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Unit implements IsSerializable{

	private int id;
	private String name;
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
	public String getName() {
		return name;
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
