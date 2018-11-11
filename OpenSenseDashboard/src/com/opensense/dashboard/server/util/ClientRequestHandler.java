package com.opensense.dashboard.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opensense.dashboard.shared.LatLng;
import com.opensense.dashboard.shared.Measurand;
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
		HashMap<Integer, Unit> unitMap = new HashMap<>();
		for(Object o : unitArrayJSON) {
			JSONObject unitJSON = (JSONObject) o;
			Unit u = new Unit();
			u.setId(unitJSON.getInt("id"));
			u.setName(unitJSON.getString("name"));
			u.setMeasurandId(unitJSON.getInt("measurandId"));
			unitMap.put(u.getId(),u);
		}
		return unitMap;
	}
	
	public HashMap<Integer, Measurand> getMeasurandMap(){
		RequestSender rs = new RequestSender();
		JSONArray measurandArrayJSON = rs.arrayRequest(baseURL+"/measurands");
		HashMap<Integer, Measurand> measurandMap= new HashMap<>();
		for(Object o : measurandArrayJSON) {
			JSONObject measurandJSON = (JSONObject) o;
			Measurand m = new Measurand();
			m.setId(measurandJSON.getInt("id"));
			m.setDisplayName(measurandJSON.getString("name"));
			m.setDefaultUnitId(measurandJSON.getInt("defaultUnitId"));
			measurandMap.put(m.getId(),m);
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
		HashMap<Integer, Measurand> measurandMap = getMeasurandMap();
		HashMap<Integer, Unit> unitMap = getUnitMap();
		for(Object o : sensorArrayJSON) {
			JSONObject sensorJSON = (JSONObject) o;
			Sensor s = new Sensor();
			s.setId(sensorJSON.getInt("id"));
			s.setAccuracy(sensorJSON.getDouble("accuracy"));
			s.setUserId(sensorJSON.getInt("userId"));
			s.setId(sensorJSON.getInt("id"));
			s.setDirectionHorizontal(sensorJSON.getDouble("directionHorizontal"));
			s.setDirectionVertical(sensorJSON.getDouble("directionVertical"));
			s.setAttributionText(sensorJSON.getString("attributionText"));
//			s.setMeasurandId(sensorJSON.getInt("measurandId"));
			s.setMeasurand(measurandMap.get(sensorJSON.getInt("measurandId")));
			s.setLicenseId(sensorJSON.getInt("licenseId"));
			s.setAltitudeAboveGround(sensorJSON.getDouble("altitudeAboveGround"));
			s.setAttributionURLString(sensorJSON.getString("attributionURL"));
			s.setSensorModel(sensorJSON.getString("sensorModel"));
			JSONObject locationJSON = sensorJSON.getJSONObject("location");
			s.setLocation(new LatLng(locationJSON.getDouble("lat"), locationJSON.getDouble("lng")));
			s.setUnitId(sensorJSON.getInt("unitId"));
			s.setUnit(unitMap.get(s.getUnitId()));
			sensorList.add(s);
		}
		return sensorList;
	}
	
	public Sensor getSensor(int id){
		RequestSender rs = new RequestSender();
		JSONObject sensorJSON = rs.objectRequest(baseURL+"/sensors/"+id);
		HashMap<Integer, Measurand> measurandMap = getMeasurandMap();
		HashMap<Integer, Unit> unitMap = getUnitMap();
		Sensor s = new Sensor();
		s.setId(sensorJSON.getInt("id"));
		s.setAccuracy(sensorJSON.getDouble("accuracy"));
		s.setUserId(sensorJSON.getInt("userId"));
		s.setId(sensorJSON.getInt("id"));
		s.setDirectionHorizontal(sensorJSON.getDouble("directionHorizontal"));
		s.setDirectionVertical(sensorJSON.getDouble("directionVertical"));
		s.setAttributionText(sensorJSON.getString("attributionText"));
//		s.setMeasurandId(sensorJSON.getInt("measurandId"));
		s.setMeasurand(measurandMap.get(sensorJSON.getInt("measurandId")));
		s.setLicenseId(sensorJSON.getInt("licenseId"));
		s.setAltitudeAboveGround(sensorJSON.getDouble("altitudeAboveGround"));
		s.setAttributionURLString(sensorJSON.getString("attributionURL"));
		s.setSensorModel(sensorJSON.getString("sensorModel"));
		JSONObject locationJSON = sensorJSON.getJSONObject("location");
		s.setLocation(new LatLng(locationJSON.getDouble("lat"), locationJSON.getDouble("lng")));
		s.setUnitId(sensorJSON.getInt("unitId"));
		s.setUnit(unitMap.get(s.getUnitId()));
		return s;
	}
	
	public List<Value> getValueList(int id, List<Parameter> parameterList){
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		JSONObject sensorJSON = rs.objectRequest(baseURL+"/sensors/"+id+"/values");
		int sensorId = sensorJSON.getInt("id");
		int measurandId = sensorJSON.getInt("measurandId");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		JSONArray valueArrayJSON = sensorJSON.getJSONArray("values");
		LinkedList<Value> valueList = new LinkedList<>();
		for(Object o : valueArrayJSON) {
			JSONObject valueJSON = (JSONObject) o;
			Date timestamp;
			try {
				timestamp = inputFormat.parse(valueJSON.getString("timestamp"));
			} catch (ParseException e) {
				timestamp = null;
			}
			if(timestamp==null) continue;
			Value v = new Value(timestamp,valueJSON.getDouble("numberValue"),sensorId,measurandId);
			valueList.add(v);
		}
		return valueList;
	}

}
