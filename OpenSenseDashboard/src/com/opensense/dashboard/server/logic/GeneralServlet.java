package com.opensense.dashboard.server.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.server.util.CSVFileParser;
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

	@Override
	public ActionResult addSensorsToUserList(int listId, List<Integer> sensors) {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.addSensorsToUserList(SessionUser.getInstance().getUserId(), listId, sensors);
		}
		return new ActionResult(ActionResultType.FAILED);
	}

	@Override
	public ActionResult deleteSensorsFromUserList(int listId, List<Integer> sensors) {
		if(!SessionUser.getInstance().isGuest()) {
			DatabaseManager db = new DatabaseManager();
			return db.deleteSensorsFromUserList(SessionUser.getInstance().getUserId(), listId, sensors);
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
		if(SessionUser.getInstance().isGuest() || (SessionUser.getInstance().getToken()==null)) {
			ActionResult ar = new ActionResult(ActionResultType.FAILED);
			ar.setErrorMessage(Languages.notLoggedIn());
			return ar;
		}
		String withID = "";
		int sensorId = 0;
		try{
			String response = ClientRequestHandler.getInstance().sendCreateSensorRequest(request);
			JSONObject sensor = new JSONObject(response);
			sensorId = sensor.getInt(JsonAttributes.ID.getNameString());
			withID = " "+Languages.with()+" ID "+sensorId;
		}catch(IOException e) {
			ActionResult ar = new ActionResult(ActionResultType.FAILED);
			ar.setErrorMessage(Languages.invalidParameters());
			return ar;
		}

		ActionResult ar = new ActionResult(ActionResultType.SUCCESSFUL);
		if(request.isValuesAttached() && sensorId!=0) {
			String valuesParsed = ServerLanguages.noValuesParsed();
			try {
				valuesParsed = ClientRequestHandler.getInstance().addValues(sensorId, CSVFileParser.parseValues());
			}catch(IOException e) {
				valuesParsed = ServerLanguages.noValuesParsed();
			}
			ar.setErrorMessage(Languages.sensorCreated()+withID+", "+valuesParsed);
		}else {
			ar.setErrorMessage(Languages.sensorCreated()+withID);
		}
		return ar;
	}

	@Override
	public ActionResult deleteSensorsFromMySensors(List<Integer> sensorIds) {
		if(SessionUser.getInstance().isGuest()  || (SessionUser.getInstance().getToken()==null)) {
			return new ActionResult(ActionResultType.FAILED);
		}
		boolean[] deleteFailed = {false};
		sensorIds.forEach(sensorId -> {
			try{
				ClientRequestHandler.getInstance().sendDeleteSensorRequest(sensorId);
			}catch(IOException e) {
				deleteFailed[0] = true;
			}
		});
		if(deleteFailed[0]) {
			return new ActionResult(ActionResultType.FAILED);
		}else {
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
	}
	@Override
	public String getUserName() {
		if(!SessionUser.getInstance().isGuest()) {
			return SessionUser.getInstance().getUsername();
		}
		return null;
	}

}
