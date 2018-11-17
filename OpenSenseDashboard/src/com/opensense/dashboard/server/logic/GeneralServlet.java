package com.opensense.dashboard.server.logic;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.ServerLanguages;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	@Override
	public Response getDataFromRequest(Request searchRequest) {
		Response response = new Response();
		response.setResultType(searchRequest.getRequestType());
		if(searchRequest.getRequestType()==null) {
			return response;
		}
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
			response.setValues(ClientRequestHandler.getInstance().getValueList(searchRequest.getIds().get(0),searchRequest.getParameters()));
			break;
		default:
			break;
		}
		return response;
	}

	@Override
	public Map<Integer, String> getMeasurands() {
		Map<Integer, Measurand> measurandMap = ClientRequestHandler.getInstance().getMeasurandMap();
		Map<Integer, String> measurandStringMap = new HashMap<>();
		if(measurandMap==null) {
			return null;
		}
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
		System.out.println(ServerLanguages.isGerman());
	}

}
