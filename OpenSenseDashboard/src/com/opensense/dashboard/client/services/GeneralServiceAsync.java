package com.opensense.dashboard.client.services;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;

public interface GeneralServiceAsync {

	void getDataFromRequest(Request searchRequest, AsyncCallback<Response> asyncCallback);
	void setServerLanguage(String lang, AsyncCallback<Void> asyncCallback);
	void createNewUserList(AsyncCallback<Integer> asyncCallback);
	void deleteUserList(int listId, AsyncCallback<ActionResult> asyncCallback);
	void changeUserListName(int listId, String newListName, AsyncCallback<ActionResult> asyncCallback);
	void addSensorsToUserList(int listId, List<Integer> sensors, AsyncCallback<ActionResult> asyncCallback);
	void deleteSensorsFromUserList(int listId, List<Integer> sensors, AsyncCallback<ActionResult> asyncCallback);
	void createSensor(CreateSensorRequest request, AsyncCallback<ActionResult> asyncCallback);
	void deleteSensorsFromMySensors(List<Integer> sensorIds, AsyncCallback<ActionResult> asyncCallback);
}
