package com.opensense.dashboard.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Value implements IsSerializable{

	private Date timestamp;
	private double numberValue;
	private int sensorId;
	private int measurandId;
	
	public Value() {
	}
	
	public Value(Date timestamp, double numberValue, int sensorId, int measurandId) {
		this.setTimestamp(timestamp);
		this.setNumberValue(numberValue);
		this.setSensorId(sensorId);
		this.setMeasurandId(measurandId);
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

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @param numberValue the numberValue to set
	 */
	public void setNumberValue(double numberValue) {
		this.numberValue = numberValue;
	}

	/**
	 * @param sensorId the sensorId to set
	 */
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * @param measurandId the measurandId to set
	 */
	public void setMeasurandId(int measurandId) {
		this.measurandId = measurandId;
	}
}
