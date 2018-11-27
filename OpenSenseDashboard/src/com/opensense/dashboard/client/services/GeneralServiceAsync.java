package com.opensense.dashboard.client.services;


import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;

public interface GeneralServiceAsync {

	void getDataFromRequest(Request searchRequest, AsyncCallback<Response> asyncCallback);
	void getMeasurands(AsyncCallback<Map<Integer, String>> asyncCallback);
	void setServerLanguage(String lang, AsyncCallback<Void> asyncCallback);
	void userLoginRequest(Request loginRequest, AsyncCallback<String> asyncCallback);
	
}
