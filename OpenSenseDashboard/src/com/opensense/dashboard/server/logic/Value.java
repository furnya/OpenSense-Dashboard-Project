package com.opensense.dashboard.server.logic;

import java.io.Serializable;
import java.util.Date;

public class Value implements Serializable{

	private final Date timestamp;
	private final double numberValue;
	private final int sensorId;
	private final Sensor sensor;
	private final int measurandId;
	private final Measurand measurand;
	
	public Value(Date timestamp, double numberValue, Sensor sensor, Measurand measurand) {
		this.timestamp = timestamp;
		this.numberValue = numberValue;
		this.sensor = sensor;
		this.measurand = measurand;
		this.sensorId = this.sensor.getId();
		this.measurandId = this.measurand.getId();
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
	 * @return the sensor
	 */
	public Sensor getSensor() {
		return this.sensor;
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
