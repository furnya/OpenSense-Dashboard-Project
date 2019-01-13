package com.opensense.dashboard.server.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.DatabaseManager;
import com.opensense.dashboard.server.util.JsonAttributes;
import com.opensense.dashboard.server.util.ServerLanguages;
import com.opensense.dashboard.server.util.SessionUser;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.CreateSensorRequest;
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
			case SENSOR:
				response.setSensors(ClientRequestHandler.getInstance().getSensorList(searchRequest.getParameters(), searchRequest.getIds()));
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
				response.setUserLists(ClientRequestHandler.getInstance().getUserLists());//TODO implement with request to opensense.network or to database
				break;
			case MYSENSORS:
				response.setMySensors(ClientRequestHandler.getInstance().getMySensorIds(SessionUser.getInstance().getToken()));
				break;
			case VALUE_AGGREGATED:
				response.setValues(ClientRequestHandler.getInstance().getAggregatedValueList(searchRequest.getIds().get(0),searchRequest.getParameters(),searchRequest.getDateRange()));
				response.setSensors(ClientRequestHandler.getInstance().getSensorList(searchRequest.getParameters(), searchRequest.getIds()));
				break;
			case LICENSE:
				response.setLicenses(ClientRequestHandler.getInstance().getLicenseMap());
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
		return ClientRequestHandler.getInstance().getUserLists();
	}

	@Override
	public ActionResult addSensorsToUserList(int listId, List<Integer> sensors) {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.addSensorsToUserList(SessionUser.getInstance().getUserId(), listId, sensors);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public ActionResult deleteSensorsFromUserList(int listId, List<Integer> sensors) {//TODO:
		if(this.lists.containsKey(listId)) {
			UserList list = this.lists.get(listId);
			sensors.forEach(list.getSensorIds()::remove);
			this.lists.replace(listId, list);
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public ActionResult deleteUserList(int listId) {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.deleteUserList(SessionUser.getInstance().getUserId(), listId);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public Integer createNewUserList() {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.createNewUserList(SessionUser.getInstance().getUserId());
		}
		return null;
	}

	@Override
	public ActionResult changeUserListName(int listId, String newListName) {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.changeUserListName(SessionUser.getInstance().getUserId(), listId, newListName);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public ActionResult createSensor(CreateSensorRequest request) {
		if(SessionUser.getInstance().isGuest()) {
			ActionResult ar = new ActionResult(ActionResultType.FAILED);
			ar.setErrorMessage(Languages.notLoggedIn());
			return ar;
		}
		String withID = "";
		try{
			String response = ClientRequestHandler.getInstance().sendCreateSensorRequest(request);
			JSONObject sensor = new JSONObject(response);
			withID = " "+Languages.with()+" ID "+sensor.getInt(JsonAttributes.ID.getNameString());
		}catch(IOException e) {
			ActionResult ar = new ActionResult(ActionResultType.FAILED);
			ar.setErrorMessage(Languages.invalidParameters());
			return ar;
		}
		ActionResult ar = new ActionResult(ActionResultType.SUCCESSFUL);
		ar.setErrorMessage(Languages.sensorCreated()+withID);
		return ar;
	}

}
