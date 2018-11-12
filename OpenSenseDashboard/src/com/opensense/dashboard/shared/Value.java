package com.opensense.dashboard.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Value implements IsSerializable{

	private Date timestamp;
	private double numberValue;
	
	public Value() {
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
}
