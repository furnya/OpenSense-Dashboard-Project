package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum ResultType implements IsSerializable{
	SENSOR, UNIT, VALUE, MEASURAND; //kannst gerne alle hinzufügen von den du glaubst, dass wir sie brauchen
	
	private ResultType() {
	}
}
