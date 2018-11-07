package com.opensense.dashboard.server.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.RequestSender;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.Sensor;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	@Override
	public Response getDataFromRequest(Request searchRequest) {
		
		return new Response();
	}

	@Override
	public Map<Integer, String> getMeasurands() {
		ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
		HashMap<Integer, Measurand> measurandMap = clientRequestHandler.getMeasurandMap();
		HashMap<Integer, String> measurandStringMap = new HashMap<>();
		measurandMap.forEach((k,v) -> measurandStringMap.put(k,v.getName()));
		return measurandStringMap;
	}

}
