package com.opensense.dashboard.client.services;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.TestClass;

public interface GeneralServiceAsync {

	void getSensorDataFromString(String searchQuery, AsyncCallback<TestClass> asyncCallback);

	void getMapSensorData(AsyncCallback<List<String>> asyncCallback);

}
