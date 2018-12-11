package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum ResultType implements IsSerializable{
	SENSOR, UNIT, VALUE, MEASURAND, MINIMAL_SENSOR, VALUE_PREVIEW, USER_LIST, MYSENSORS;	
	private ResultType() {
	}
}
