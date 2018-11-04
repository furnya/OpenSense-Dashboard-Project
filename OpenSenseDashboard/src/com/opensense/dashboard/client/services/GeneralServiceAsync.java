package com.opensense.dashboard.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeneralServiceAsync {

	void getSensorDataFromString(String searchQuery, AsyncCallback<String> asyncCallback);
	
}
