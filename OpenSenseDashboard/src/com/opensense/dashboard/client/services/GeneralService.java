package com.opensense.dashboard.client.services;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.UserList;
import com.opensense.dashboard.shared.ValuePreview;

@RemoteServiceRelativePath("../general")
public interface GeneralService extends RemoteService {

	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {

		private static GeneralServiceAsync instance;

		private Util() {
			// Hides the implicit public constructor when no other constructor is present.
		}

		public static GeneralServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(GeneralService.class);
			}
			return instance;
		}
	}

	Response getDataFromRequest(Request searchRequest);
	Map<Integer, String> getMeasurands();
	void setServerLanguage(String lang);
	Map<Integer, ValuePreview> getSensorValuePreview(List<Integer> ids);
	List<UserList> getUserLists();
	List<Integer> getMySensorsUserList();
	UserList createNewUserList();
	ActionResult deleteUserList(int listId);
	ActionResult changeUserListName(int listId, String newListName);
	List<MinimalSensor> getMinimalSensorData(List<Integer> sensorIds);
}
