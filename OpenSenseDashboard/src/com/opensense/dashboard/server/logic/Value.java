package com.opensense.dashboard.server.logic;

import java.io.Serializable;
import java.util.Date;

public class Value implements Serializable{

	private final Date timestamp;
	private final double numberValue;
	private final Sensor sensor;
	
	public Value(Date timestamp, double numberValue, Sensor sensor) {
		this.timestamp = timestamp;
		this.numberValue = numberValue;
		this.sensor = sensor;
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
}
