package com.opensense.dashboard.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.ServerLanguages;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.UserList;
import com.opensense.dashboard.shared.ValuePreview;

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
	public Map<Integer, String> getMeasurands() { //TODO: change the way of request  to getDataFromRequest
		Map<Integer, Measurand> measurandMap;
		try {
			measurandMap = ClientRequestHandler.getInstance().getMeasurandMap();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failure", e);
			return null;
		}
		if(measurandMap==null) {
			return null;
		}
		Map<Integer, String> measurandStringMap = new HashMap<>();
		measurandMap.forEach((id,measurand) -> measurandStringMap.put(id,measurand.getDisplayName()));
		return measurandStringMap;
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
	public Map<Integer, ValuePreview> getSensorValuePreview(List<Integer> ids) {  //TODO: change the way of request  to getDataFromRequest
		HashMap<Integer, ValuePreview> previewMap = new HashMap<>();
		ids.forEach(id -> {
			try {
				previewMap.put(id, ClientRequestHandler.getInstance().getValuePreview(id));
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Failure", e);
			}
		});
		return previewMap;
	}

	private final Map<Integer, UserList> lists = new HashMap<>();
	int idNumber = 0;

	@Override
	public List<UserList> getUserLists() { // this method could be added in the get data from request (LIST)
		//checks user is logged in, in all sensible server calls
		// TODO Auto-generated method stub
		//list of list ids with sensor ids in it (yes the ids are duplicated)
		List<UserList> list = new ArrayList<>();
		this.lists.values().forEach(list::add);
		return list;
	}

	public ActionResult addSensorsToUserList() { //TODO:
		return null;

	}

	public ActionResult addSensorToMySensorsUserList() { //TODO:
		return null;

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
	public List<Integer> getMySensorsUserList() { // this method could be added in the get data from request (LIST)
		// TODO Auto-generated method stub
		//list of sensor ids in it
		List<Integer> sensorList = new ArrayList<>();
		return sensorList;
	}

	@Override
	public UserList createNewUserList() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.lists.get(listId).setListName(newListName);
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}

	@Override
	public List<MinimalSensor> getMinimalSensorData(List<Integer> sensorIds) { // this method could be added in the get data from request (LIST)
		// TODO Auto-generated method stub
		return null;
	}
}
