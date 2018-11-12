package com.opensense.dashboard.server.logic;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	@Override
	public Response getDataFromRequest(Request searchRequest) {
		Response response = new Response();
		ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
		response.setResultType(searchRequest.getRequestType());
		if(searchRequest.getRequestType()==null) {
			return response;
		}
		switch(searchRequest.getRequestType()) {
		case MEASURAND:
			response.setMeasurands(clientRequestHandler.getMeasurandMap());
			break;
		case SENSOR:
			response.setSensors(clientRequestHandler.getSensorList(searchRequest.getParameters(), searchRequest.getIds()));
			break;
		case UNIT:
			response.setUnits(clientRequestHandler.getUnitMap());
			break;
		case VALUE:
			response.setValues(clientRequestHandler.getValueList(searchRequest.getIds().get(0),searchRequest.getParameters()));
			break;
		default:
			break;
		}
		return response;
	}

	@Override
	public Map<Integer, String> getMeasurands() {
		ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
		HashMap<Integer, Measurand> measurandMap = clientRequestHandler.getMeasurandMap();
		HashMap<Integer, String> measurandStringMap = new HashMap<>();
		if(measurandMap==null) {
			return null;
		}
		measurandMap.forEach((id,measurand) -> measurandStringMap.put(id,measurand.getDisplayName()));
		return measurandStringMap;
	}

}
