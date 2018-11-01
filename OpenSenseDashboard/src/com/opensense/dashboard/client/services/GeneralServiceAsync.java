package com.opensense.dashboard.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.server.logic.Sensor;

public interface GeneralServiceAsync {

	void getSensorDataFromString(String searchQuery, AsyncCallback<List<Sensor>> asyncCallback);
	
}
