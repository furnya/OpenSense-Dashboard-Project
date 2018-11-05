package com.opensense.dashboard.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.TestClass;

public interface GeneralServiceAsync {

	void getSensorDataFromString(String searchQuery, AsyncCallback<TestClass> asyncCallback);
	
}
