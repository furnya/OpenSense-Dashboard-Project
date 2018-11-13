package com.opensense.dashboard.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensense.dashboard.shared.LatLng;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;

public class ClientRequestHandler {

	private static String baseURL = "https://www.opensense.network/progprak/beta/api/v1.0";
	
	public ClientRequestHandler() {
	}
	
	public HashMap<Integer, Unit> getUnitMap(){
		RequestSender rs = new RequestSender();
		JSONArray unitArrayJSON = rs.arrayRequest(baseURL+"/units");
		if(unitArrayJSON==null) {
			return null;
		}
		HashMap<Integer, Unit> unitMap = new HashMap<>();
		for(Object o : unitArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject unitJSON = (JSONObject) o;
			Unit u = DataObjectBuilder.buildUnit(unitJSON);
			if(u!=null) {
				unitMap.put(u.getId(),u);
			}
		}
		return unitMap;
	}
	
	public HashMap<Integer, Measurand> getMeasurandMap(){
		RequestSender rs = new RequestSender();
		JSONArray measurandArrayJSON = rs.arrayRequest(baseURL+"/measurands");
		if(measurandArrayJSON==null) {
			return null;
		}
		HashMap<Integer, Measurand> measurandMap= new HashMap<>();
		for(Object o : measurandArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject measurandJSON = (JSONObject) o;
			Measurand m = DataObjectBuilder.buildMeasurand(measurandJSON);
			if(m!=null) {
				measurandMap.put(m.getId(),m);
			}
		}
		return measurandMap;
	}
	
	public List<Sensor> getSensorList(List<Parameter> parameterList, List<Integer> ids){
		LinkedList<Sensor> sensorList = new LinkedList<>();
		if(ids!=null && !ids.isEmpty()) {
			for(int id : ids) {
				sensorList.add(getSensor(id));
			}
			return sensorList;
		}
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		JSONArray sensorArrayJSON = rs.arrayRequest(baseURL+"/sensors");
		if(sensorArrayJSON==null) {
			return sensorList;
		}
		HashMap<Integer, Measurand> measurandMap = getMeasurandMap();
		HashMap<Integer, Unit> unitMap = getUnitMap();
		for(Object o : sensorArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject sensorJSON = (JSONObject) o;
			Sensor s = DataObjectBuilder.buildSensor(sensorJSON, measurandMap, unitMap);
			if(s!=null) {
				sensorList.add(s);
			}
		}
		return sensorList;
	}
	
	public Sensor getSensor(int id){
		RequestSender rs = new RequestSender();
		JSONObject sensorJSON = rs.objectRequest(baseURL+"/sensors/"+id);
		if(sensorJSON==null) {
			return null;
		}
		HashMap<Integer, Measurand> measurandMap = getMeasurandMap();
		HashMap<Integer, Unit> unitMap = getUnitMap();
		return DataObjectBuilder.buildSensor(sensorJSON, measurandMap, unitMap);
	}
	
	public List<Value> getValueList(int id, List<Parameter> parameterList){
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		JSONObject sensorJSON = rs.objectRequest(baseURL+"/sensors/"+id+"/values");
		if(sensorJSON==null) {
			return null;
		}
		LinkedList<Value> valueList = new LinkedList<>();
		SimpleDateFormat inputFormat;
		try {
			inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		} catch(Exception e) {
			e.printStackTrace();
			return valueList;
		}
		JSONArray valueArrayJSON;
		try {
			valueArrayJSON = sensorJSON.getJSONArray("values");
		} catch(JSONException e) {
			e.printStackTrace();
			return valueList;
		}
		for(Object o : valueArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject valueJSON = (JSONObject) o;
			Value v = DataObjectBuilder.buildValue(valueJSON, inputFormat);
			if(v!=null) {
				valueList.add(v);
			}
		}
		return valueList;
	}

}
