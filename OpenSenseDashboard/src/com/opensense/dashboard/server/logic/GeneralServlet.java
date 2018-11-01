package com.opensense.dashboard.server.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	private RequestSender requestSender;
	
	@Override
	public List<Sensor> getSensorDataFromString(String searchQuery) {
		this.requestSender = new RequestSender();
		this.requestSender.addParameter("maxSensors", "100");
		JSONArray sensorArray = this.requestSender.arrayRequest("https://www.opensense.network/beta/api/v1.0/sensors");
		LinkedList<Sensor> sensorList = new LinkedList<Sensor>();
		for(Object o : sensorArray) {
			JSONObject sensorJSON = (JSONObject) o;
			sensorList.add(new Sensor(sensorJSON));
		}
		return sensorList;
	}

}
