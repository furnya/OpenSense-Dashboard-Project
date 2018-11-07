package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Parameter implements IsSerializable{

	private String key;
	private String value;
	
	public Parameter() {
	}
	
	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
