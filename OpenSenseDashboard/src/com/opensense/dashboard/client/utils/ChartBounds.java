package com.opensense.dashboard.client.utils;

import java.util.Date;

public class ChartBounds {

	private Double minValue;
	private Double maxValue;
	private Date minTimestamp;
	private Date maxTimestamp;
	
	public ChartBounds(Double minValue, Double maxValue, Date minTimestamp, Date maxTimestamp) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.minTimestamp = minTimestamp;
		this.maxTimestamp = maxTimestamp;
	}

	/**
	 * @return the minValue
	 */
	public Double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public Double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the minTimestamp
	 */
	public Date getMinTimestamp() {
		return minTimestamp;
	}

	/**
	 * @param minTimestamp the minTimestamp to set
	 */
	public void setMinTimestamp(Date minTimestamp) {
		this.minTimestamp = minTimestamp;
	}

	/**
	 * @return the maxTimestamp
	 */
	public Date getMaxTimestamp() {
		return maxTimestamp;
	}

	/**
	 * @param maxTimestamp the maxTimestamp to set
	 */
	public void setMaxTimestamp(Date maxTimestamp) {
		this.maxTimestamp = maxTimestamp;
	}

}
