package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MinimalSensor implements IsSerializable{

	private int sensorId;
	private Measurand measurand;
	//perhaps we can add something like location = Berlin (create that at the server site if the performance is ok)

	public MinimalSensor() {
	}

	/**
	 * @return the id
	 */
	public int getSensorId() {
		return this.sensorId;
	}

	/**
	 * @param id the id to set
	 */
	public void setSensorId(int id) {
		this.sensorId = id;
	}


	/**
	 * @return the measurand
	 */
	public Measurand getMeasurand() {
		return this.measurand;
	}

	/**
	 * @param measurand the measurand to set
	 */
	public void setMeasurand(Measurand measurand) {
		this.measurand = measurand;
	}

}
