package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Measurand implements IsSerializable{

	private int id;
	private MeasurandType measurandType;
	private String displayName;
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
	public String getDisplayName() {
		return displayName;
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
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/**
	 * @param defaultUnitId the defaultUnitId to set
	 */
	public void setDefaultUnitId(int defaultUnitId) {
		this.defaultUnitId = defaultUnitId;
	}

	public MeasurandType getMeasurandType() {
		return measurandType;
	}

	public void setMeasurandType(MeasurandType measurandType) {
		this.measurandType = measurandType;
	}

}
