package com.opensense.dashboard.client.services;


import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ValuePreview;

public interface GeneralServiceAsync {

	void getDataFromRequest(Request searchRequest, AsyncCallback<Response> asyncCallback);
	void getMeasurands(AsyncCallback<Map<Integer, String>> asyncCallback);
	void setServerLanguage(String lang, AsyncCallback<Void> asyncCallback);
	void getSensorValuePreview(List<Integer> ids, AsyncCallback<Map<Integer, ValuePreview>> asyncCallback);
	void getUserLists(AsyncCallback<Map<Integer, List<Integer>>> asyncCallback);
	void getMySensorsUserList(AsyncCallback<List<Integer>> asyncCallback);
	void createNewUserList(AsyncCallback<Integer> asyncCallback);
	void deleteUserList(int listId, AsyncCallback<ActionResult> asyncCallback);
}
