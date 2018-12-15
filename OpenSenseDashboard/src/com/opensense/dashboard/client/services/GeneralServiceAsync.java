package com.opensense.dashboard.client.services;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.UserList;

public interface GeneralServiceAsync {

	void getDataFromRequest(Request searchRequest, AsyncCallback<Response> asyncCallback);
	void setServerLanguage(String lang, AsyncCallback<Void> asyncCallback);
	void getUserLists(AsyncCallback<List<UserList>> asyncCallback);
	void createNewUserList(AsyncCallback<ActionResult> asyncCallback);
	void deleteUserList(int listId, AsyncCallback<ActionResult> asyncCallback);
	void changeUserListName(int listId, String newListName, AsyncCallback<ActionResult> asyncCallback);
	void addSensorsToUserList(int listId, List<Integer> sensors, AsyncCallback<ActionResult> asyncCallback);
	void deleteSensorsFromUserList(int listId, List<Integer> sensors, AsyncCallback<ActionResult> asyncCallback);
}
