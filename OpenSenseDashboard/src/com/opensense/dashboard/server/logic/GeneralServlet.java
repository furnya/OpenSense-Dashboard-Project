package com.opensense.dashboard.server.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.ServerLanguages;
import com.opensense.dashboard.server.util.SessionUser;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.UserList;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	private static final Logger LOGGER = Logger.getLogger(GeneralServlet.class.getName());

	@Override
	public Response getDataFromRequest(Request searchRequest) {
		Response response = new Response();
		response.setResultType(searchRequest.getRequestType());
		if(searchRequest.getRequestType()==null) {
			return response;
		}
		try {
			switch(searchRequest.getRequestType()) {
			case MEASURAND:
				response.setMeasurands(ClientRequestHandler.getInstance().getMeasurandMap());
				break;
			case SENSOR: //TODO: add searchRequest.isOnlySensorsWithValues();
				if(searchRequest.isOnlySensorsWithValues()) {
					response.setSensors(ClientRequestHandler.getInstance().getSensorListOnlyWithValues(searchRequest.getParameters(), searchRequest.getIds()));
				}else {
					response.setSensors(ClientRequestHandler.getInstance().getSensorList(searchRequest.getParameters(), searchRequest.getIds()));
				}
				break;
			case UNIT:
				response.setUnits(ClientRequestHandler.getInstance().getUnitMap());
				break;
			case VALUE:
				response.setValues(ClientRequestHandler.getInstance().getValueList(searchRequest.getIds().get(0),searchRequest.getParameters(),searchRequest.getDateRange()));
				response.setSensors(ClientRequestHandler.getInstance().getSensorList(searchRequest.getParameters(), searchRequest.getIds()));
				break;
			case VALUE_PREVIEW:
				response.setValuePreviews(ClientRequestHandler.getInstance().getValuePreview(searchRequest.getIds()));
				break;
			case MINIMAL_SENSOR:
				response.setMinimalSensors(ClientRequestHandler.getInstance().getMinimalSensorList(searchRequest.getParameters(), searchRequest.getIds()));
				break;
			case USER_LIST:
				response.setUserLists(this.getUserLists());//TODO implement with request to opensense.network or to database
				break;
			case MYSENSORS:
				response.setMySensors(ClientRequestHandler.getInstance().getMySensorIds(SessionUser.getInstance().getToken()));
				break;
			default:
				break;
			}
		}catch(Exception e){
			LOGGER.log(Level.WARNING, "Failure", e);
			return null;
		}
		return response;
	}

	@Override
	public void setServerLanguage(String lang) {
		if("en".equals(lang)) {
			ServerLanguages.setEnglish();
		}else {
			ServerLanguages.setGerman();
		}
	}

	private final Map<Integer, UserList> lists = new HashMap<>();
	int idNumber = 0;

	@Override
	public List<UserList> getUserLists() { // this method could be added in the get data from request (LIST)
		if(SessionUser.getInstance().isGuest()) {
			return new LinkedList<>();
		}
		List<UserList> list = new ArrayList<>();
		this.lists.values().forEach(list::add);
		return list;
	}

	@Override
	public ActionResult addSensorsToUserList(int listId, List<Integer> sensors) {
		if(this.lists.containsKey(listId)) {
			UserList list = this.lists.get(listId);
			sensors.forEach(list.getSensorIds()::add);
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	public ActionResult addSensorToMySensorsUserList() { //TODO:
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public ActionResult deleteUserList(int listId) {
		if(this.lists.containsKey(listId)) {
			this.lists.remove(listId);
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public UserList createNewUserList() {
		//list of list ids with sensor ids in it
		UserList listItem = new UserList();
		listItem.setListId(this.idNumber++);
		listItem.setListName("Neue Liste " + listItem.getListId());
		listItem.setSensorIds(new ArrayList<>());
		this.lists.put(listItem.getListId(), listItem);
		return listItem;
	}

	@Override
	public ActionResult changeUserListName(int listId, String newListName) {
		if(this.lists.containsKey(listId)) {
			this.lists.get(listId).setListName(newListName);
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Deprecated
	public List<MinimalSensor> getMinimalSensorData(List<Integer> sensorIds) {
		try{
			return ClientRequestHandler.getInstance().getMinimalSensorList(null, sensorIds);
		}catch(IOException e) {
			LOGGER.log(Level.WARNING, "Failure", e);
			return new LinkedList<>();
		}
	}
}
