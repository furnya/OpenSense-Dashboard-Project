package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddValuesRequest implements IsSerializable{

	private int sensorId;

	public AddValuesRequest() {
	}

	/**
	 * @return the sensorId
	 */
	public int getSensorId() {
		return sensorId;
	}

	/**
	 * @param sensorId the sensorId to set
	 */
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
}
