package com.opensense.dashboard.client.services;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;

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
	void setServerLanguage(String lang);
	Integer createNewUserList();
	ActionResult deleteUserList(int listId);
	ActionResult changeUserListName(int listId, String newListName);
	ActionResult addSensorsToUserList(int listId, List<Integer> sensors);
	ActionResult deleteSensorsFromUserList(int listId, List<Integer> sensors);
	ActionResult createSensor(CreateSensorRequest request);
	ActionResult deleteSensorsFromMySensors(List<Integer> sensorIds);
	String getUserName();
}
