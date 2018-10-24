package com.opensense.dashboard.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeneralStartServiceAsync {

	void getData(String string, AsyncCallback<Integer> asyncCallback);
	
}
