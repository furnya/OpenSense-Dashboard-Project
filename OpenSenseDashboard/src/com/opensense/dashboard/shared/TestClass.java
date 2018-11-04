package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TestClass implements IsSerializable{
	
	private List<String> s;
	
	public TestClass(List<String> s) {
		this.s = s;
	}

	public List<String> getS() {
		return s;
	}

	public void setS(List<String> s) {
		this.s = s;
	}
}
