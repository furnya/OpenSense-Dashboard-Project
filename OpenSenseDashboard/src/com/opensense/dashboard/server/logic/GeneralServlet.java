package com.opensense.dashboard.server.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.server.util.RequestSender;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.TestClass;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	private static RequestSender requestSender;
	
	@Override
	public TestClass getSensorDataFromString(String searchQuery) {
		requestSender = new RequestSender();
		requestSender.addParameter("maxSensors", "100");
		JSONArray sensorArray = requestSender.arrayRequest("https://www.opensense.network/beta/api/v1.0/sensors");
		LinkedList<String> sensorList = new LinkedList<>();
		for(Object o : sensorArray) {
			JSONObject sensorJSON = (JSONObject) o;
			sensorList.add(new Sensor(sensorJSON).getId()+"");
		}
		return new TestClass(sensorList);
	}
	@Override 
	public List<String> getMapSensorData() {
		List<String> testList = new ArrayList<>();
		testList.add("52.521918,13.513215");
		testList.add("52.42341,13.34235");
		testList.add("52.43523,13.52335");
		testList.add("52.54441,13.4235");
		return testList;
	}
	
}
