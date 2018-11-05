package com.opensense.dashboard.shared;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Value implements IsSerializable{

	private final Date timestamp;
	private final double numberValue;
	private final int sensorId;
	private final int measurandId;
	
	public Value(Date timestamp, double numberValue, int sensorId, int measurandId) {
		this.timestamp = timestamp;
		this.numberValue = numberValue;
		this.sensorId = sensorId;
		this.measurandId = measurandId;
	}
	
	/**
	 * @return the numberValue
	 */
	public double getNumberValue() {
		return this.numberValue;
	}
	
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @return the sensorId
	 */
	public int getSensorId() {
		return sensorId;
	}

	/**
	 * @return the measurandId
	 */
	public int getMeasurandId() {
		return measurandId;
	}
}
