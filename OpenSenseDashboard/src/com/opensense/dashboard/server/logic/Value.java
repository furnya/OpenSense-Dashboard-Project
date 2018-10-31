package com.opensense.dashboard.server.logic;

import java.io.Serializable;
import java.util.Date;

public class Value implements Serializable{

	private final Date timestamp;
	private final double numberValue;
	
	public Value(Date timestamp, double numberValue) {
		this.timestamp = timestamp;
		this.numberValue = numberValue;
	}
	
	public double getNumberValue() {
		return this.numberValue;
	}
	
	public Date getTimestamp() {
		return this.timestamp;
	}
}
