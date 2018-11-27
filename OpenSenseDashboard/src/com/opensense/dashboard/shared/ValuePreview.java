package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ValuePreview implements IsSerializable{
	
	private Value minValue;
	private Value maxValue;

	public ValuePreview() {
	}
	
	public ValuePreview(Value minValue, Value maxValue) {
		this.setMinValue(minValue);
		this.setMaxValue(maxValue);
	}

	public Value getMinValue() {
		return minValue;
	}

	public void setMinValue(Value minValue) {
		this.minValue = minValue;
	}

	public Value getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Value maxValue) {
		this.maxValue = maxValue;
	}
	
}
