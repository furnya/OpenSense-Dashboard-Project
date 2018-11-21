package com.opensense.dashboard.server.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.icu.util.Calendar;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;

public class ClientRequestHandler {

	private static final boolean USE_DEFAULT_URL = true;
	
	private static final String BASE_URL = "https://www.opensense.network/beta/api/v1.0";
	private static final String BASE_URL_DEFAULT = "https://www.opensense.network/progprak/beta/api/v1.0";
	
	private static ClientRequestHandler instance;
	
	public static ClientRequestHandler getInstance() {
		if(instance == null) {
			instance = new ClientRequestHandler();
		}
		return instance;
	}
	
	private ClientRequestHandler() {
	}
	
	public Map<Integer, Unit> getUnitMap(){
		RequestSender rs = new RequestSender();
		JSONArray unitArrayJSON = rs.arrayRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/units");
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
	
	public Map<Integer, Measurand> getMeasurandMap(){
		RequestSender rs = new RequestSender();
		JSONArray measurandArrayJSON = rs.arrayRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/measurands");
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
		JSONArray sensorArrayJSON = rs.arrayRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors");
		if(sensorArrayJSON==null) {
			return sensorList;
		}
		Map<Integer, Measurand> measurandMap = getMeasurandMap();
		Map<Integer, Unit> unitMap = getUnitMap();
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
		JSONObject sensorJSON = rs.objectRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors/"+id);
		if(sensorJSON==null) {
			return null;
		}
		Map<Integer, Measurand> measurandMap = getMeasurandMap();
		Map<Integer, Unit> unitMap = getUnitMap();
		return DataObjectBuilder.buildSensor(sensorJSON, measurandMap, unitMap);
	}
	
	public List<Value> getValueList(int id, List<Parameter> parameterList, DateRange dateRange){
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		setTimestampParameters(rs, dateRange);
		JSONObject sensorJSON = rs.objectRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors/"+id+"/values");
		if(sensorJSON==null) {
			return null;
		}
		LinkedList<Value> valueList = new LinkedList<>();
		SimpleDateFormat inputFormat;
		try {
			inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		} catch(Exception e) {
			e.printStackTrace(); //TODO: create logger
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
	
	private void setTimestampParameters(RequestSender rs, DateRange dateRange) {
		Calendar cal = Calendar.getInstance();
		switch(dateRange) {
		case CUSTOM:
			break;
		case PAST_24HOURS:
			rs.addParameter("maxTimestamp", cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.DATE, -1);
			rs.addParameter("minTimestamp", cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_MONTH:
			rs.addParameter("maxTimestamp", cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.MONTH, -1);
			rs.addParameter("minTimestamp", cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_WEEK:
			rs.addParameter("maxTimestamp", cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.DATE, -7);
			rs.addParameter("minTimestamp", cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_YEAR:
			rs.addParameter("maxTimestamp", cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.YEAR, -1);
			rs.addParameter("minTimestamp", cal.getTime().toString().replace(" ", "%20"));
			break;
		default:
			break;
		}
	}

}
