package com.opensense.dashboard.server.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.icu.util.Calendar;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.License;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;
import com.opensense.dashboard.shared.ValuePreview;

public class ClientRequestHandler {

	private static final boolean USE_DEFAULT_URL = true;

	private static final String BASE_URL = "https://www.opensense.network/beta/api/v1.0";
	private static final String BASE_URL_DEFAULT = "https://www.opensense.network/progprak/beta/api/v1.0";

	private static ClientRequestHandler instance;

	private static final String MAX_TIMESTAMP = "maxTimestamp";
	private static final String MIN_TIMESTAMP = "minTimestamp";
	private static final String AGGREGATION_TYPE = "aggregationType";
	private static final String AGGREGATION_RANGE = "aggregationRange";
	private static final String SENSORS = "/sensors/";

	private static final Logger LOGGER = Logger.getLogger(ClientRequestHandler.class.getName());

	public static ClientRequestHandler getInstance() {
		if(instance == null) {
			instance = new ClientRequestHandler();
		}
		return instance;
	}

	private ClientRequestHandler() {
	}

	public Map<Integer, Unit> getUnitMap() throws IOException{
		RequestSender rs = new RequestSender();
		JSONArray unitArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/units");
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

	public Map<Integer, Measurand> getMeasurandMap() throws IOException{
		RequestSender rs = new RequestSender();
		JSONArray measurandArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/measurands");
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

	public List<Sensor> getSensorList(List<Parameter> parameterList, List<Integer> ids) throws IOException{
		LinkedList<Sensor> sensorList = new LinkedList<>();
		if((ids!=null) && !ids.isEmpty()) {
			for(int id : ids) {
				sensorList.add(this.getSensor(id));
			}
			return sensorList;
		}
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		JSONArray sensorArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors");
		if(sensorArrayJSON==null) {
			return sensorList;
		}
		Map<Integer, Measurand> measurandMap = this.getMeasurandMap();
		Map<Integer, Unit> unitMap = this.getUnitMap();
		for(Object o : sensorArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject sensorJSON = (JSONObject) o;
			Sensor s = DataObjectBuilder.buildSensor(sensorJSON, measurandMap, unitMap);
			if(s == null) {
				continue;
			}
			s.setValuePreview(this.getValuePreview(s.getSensorId()));
			sensorList.add(s);
		}
		return sensorList;
	}
	public List<Sensor> getSensorListOnlyWithValues(List<Parameter> parameterList, List<Integer> ids) throws IOException{
		List<Sensor> sensors = this.getSensorList(parameterList, ids);
		List<Sensor> filteredSensors = new LinkedList<>();
		for(Sensor sensor : sensors) {
			ValuePreview vp = this.getValuePreview(sensor.getSensorId());
			if(vp!=null) {
				filteredSensors.add(sensor);
			}
		}
		return filteredSensors;
	}

	public Sensor getSensor(int id) throws IOException{
		RequestSender rs = new RequestSender();
		JSONObject sensorJSON = rs.objectGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+ SENSORS +id);
		if(sensorJSON==null) {
			return null;
		}
		Sensor s = DataObjectBuilder.buildSensor(sensorJSON, this.getMeasurandMap(), this.getUnitMap()); // TODO: WIeso units und measurands nicht speichern, sodass nicht immer noch 2 anfragen pro sensor gemacht werden?
		if(s == null) {
			return null;
		}
		s.setValuePreview(this.getValuePreview(s.getSensorId()));
		return s;
	}

	public List<Value> getValueList(int id, List<Parameter> parameterList, DateRange dateRange) throws IOException{
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		this.setTimestampParameters(rs, dateRange);
		JSONObject sensorJSON = rs.objectGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+SENSORS+id+"/values");
		if(sensorJSON==null) {
			return null;
		}
		LinkedList<Value> valueList = new LinkedList<>();
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		JSONArray valueArrayJSON = sensorJSON.getJSONArray("values");
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

	public List<Value> getAggregatedValueList(int id, List<Parameter> parameterList, DateRange dateRange) throws IOException{
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		this.setTimestampParameters(rs, dateRange);
		this.setAggregationParameters(rs, dateRange, parameterList);

		JSONObject sensorJSON = rs.objectGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+SENSORS+id+"/values");
		if(sensorJSON==null) {
			return null;
		}
		LinkedList<Value> valueList = new LinkedList<>();
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		JSONArray valueArrayJSON = sensorJSON.getJSONArray("values");
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
			rs.addParameter(MAX_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.DATE, -1);
			rs.addParameter(MIN_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_MONTH:
			rs.addParameter(MAX_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.MONTH, -1);
			rs.addParameter(MIN_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_WEEK:
			rs.addParameter(MAX_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.DATE, -7);
			rs.addParameter(MIN_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			break;
		case PAST_YEAR:
			rs.addParameter(MAX_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			cal.add(Calendar.YEAR, -1);
			rs.addParameter(MIN_TIMESTAMP, cal.getTime().toString().replace(" ", "%20"));
			break;
		default:
			break;
		}
	}

	private void setAggregationParameters(RequestSender rs, DateRange dateRange, List<Parameter> parameterList) {
		Calendar cal = Calendar.getInstance();
		switch(dateRange) {
		case CUSTOM:
			SimpleDateFormat inputFormat = new SimpleDateFormat("d MMM yyy HH:mm:ss zzz", Locale.UK);
			Date from, to;
			try{
				String fromString = parameterList.get(0).getValue().replace("%20", " ");
				String toString = parameterList.get(1).getValue().replace("%20", " ");
				from = inputFormat.parse(fromString);
				to = inputFormat.parse(toString);
			}catch(ParseException e) {
				break;
			}
			cal.setTime(to);
			int diffDays = Math.abs(cal.fieldDifference(from, Calendar.DATE));
			if((5<=diffDays) && (diffDays<=100)) {
				rs.addParameter(AGGREGATION_TYPE, "avg");
				rs.addParameter(AGGREGATION_RANGE, "hourly");
			}else if((100<diffDays) && (diffDays<=700)) {
				rs.addParameter(AGGREGATION_TYPE, "avg");
				rs.addParameter(AGGREGATION_RANGE, "daily");
			}else if((700<diffDays) && (diffDays<=3150)) {
				rs.addParameter(AGGREGATION_TYPE, "avg");
				rs.addParameter(AGGREGATION_RANGE, "weekly");
			}else if((3150<diffDays) && (diffDays<=15000)) {
				rs.addParameter(AGGREGATION_TYPE, "avg");
				rs.addParameter(AGGREGATION_RANGE, "monthly");
			}else if(15000<diffDays) {
				rs.addParameter(AGGREGATION_TYPE, "avg");
				rs.addParameter(AGGREGATION_RANGE, "yearly");
			}
			break;
		case PAST_24HOURS:
			break;
		case PAST_MONTH:
			rs.addParameter(AGGREGATION_TYPE, "avg");
			rs.addParameter(AGGREGATION_RANGE, "hourly");
			break;
		case PAST_WEEK:
			rs.addParameter(AGGREGATION_TYPE, "avg");
			rs.addParameter(AGGREGATION_RANGE, "hourly"); //? correct
			break;
		case PAST_YEAR:
			rs.addParameter(AGGREGATION_TYPE, "avg");
			rs.addParameter(AGGREGATION_RANGE, "daily");
			break;
		default:
			break;
		}
	}

	public String sendLoginRequest(String body) throws IOException {
		RequestSender rs = new RequestSender();
		JSONObject idJSON = rs.objectPOSTRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/users/login", body, null);
		if(idJSON==null) {
			return null;
		}
		return idJSON.getString("id");
	}

	public ValuePreview getValuePreview(Integer id) throws IOException{
		RequestSender rs = new RequestSender();
		JSONObject sensorJSON = rs.objectGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+SENSORS+id+"/values/firstlast");
		if(sensorJSON==null) {
			return null;
		}
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		JSONArray valueArrayJSON = sensorJSON.getJSONArray("values");
		if(valueArrayJSON.isEmpty()) {
			return null;
		}
		Object firstValueObject = valueArrayJSON.get(0);
		Object lastValueObject = valueArrayJSON.get(1);
		if(!(firstValueObject instanceof JSONObject) || !(lastValueObject instanceof JSONObject)) {
			return null;
		}
		Value firstValue = DataObjectBuilder.buildValue((JSONObject) firstValueObject, inputFormat);
		Value lastValue = DataObjectBuilder.buildValue((JSONObject) lastValueObject, inputFormat);
		if((firstValue == null) || (lastValue == null)) {
			return null;
		}
		return new ValuePreview(firstValue,lastValue);
	}

	public Map<Integer,ValuePreview> getValuePreview(List<Integer> ids){
		HashMap<Integer, ValuePreview> previewMap = new HashMap<>();
		ids.forEach(id -> {
			try {
				previewMap.put(id, this.getValuePreview(id));
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Failure", e);
			}
		});
		return previewMap;
	}

	public List<Sensor> getMySensors(String token) throws IOException{
		LinkedList<Sensor> sensorList = new LinkedList<>();
		RequestSender rs = new RequestSender();
		JSONArray sensorArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors/mysensors", token);
		if(sensorArrayJSON==null) {
			return sensorList;
		}
		Map<Integer, Measurand> measurandMap = this.getMeasurandMap();
		Map<Integer, Unit> unitMap = this.getUnitMap();
		for(Object o : sensorArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject sensorJSON = (JSONObject) o;
			Sensor s = DataObjectBuilder.buildSensor(sensorJSON, measurandMap, unitMap);
			if(s == null) {
				continue;
			}
			s.setValuePreview(this.getValuePreview(s.getSensorId()));
			sensorList.add(s);
		}
		return sensorList;
	}

	public List<Integer> getMySensorIds(String token) throws IOException{
		LinkedList<Integer> sensorIdList = new LinkedList<>();
		RequestSender rs = new RequestSender();
		JSONArray sensorArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors/mysensorids", token);
		if(sensorArrayJSON==null) {
			return sensorIdList;
		}
		for(Object o : sensorArrayJSON) {
			if(!(o instanceof Integer)) {
				continue;
			}
			Integer sensorId = (Integer) o;
			sensorIdList.add(sensorId);
		}
		return sensorIdList;
	}

	public List<MinimalSensor> getMinimalSensorList(List<Parameter> parameterList, List<Integer> ids) throws IOException{
		LinkedList<MinimalSensor> sensorList = new LinkedList<>();
		if((ids!=null) && !ids.isEmpty()) {
			for(int id : ids) {
				sensorList.add(this.getMinimalSensor(id));
			}
			return sensorList;
		}
		RequestSender rs = new RequestSender();
		rs.setParameters(parameterList);
		JSONArray sensorArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors");
		if(sensorArrayJSON==null) {
			return sensorList;
		}
		Map<Integer, Measurand> measurandMap = this.getMeasurandMap();
		for(Object o : sensorArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject sensorJSON = (JSONObject) o;
			MinimalSensor s = DataObjectBuilder.buildMinimalSensor(sensorJSON, measurandMap);
			if(s!=null) {
				sensorList.add(s);
			}
		}
		return sensorList;
	}

	public MinimalSensor getMinimalSensor(int id) throws IOException{
		RequestSender rs = new RequestSender();
		JSONObject sensorJSON = rs.objectGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+SENSORS+id);
		if(sensorJSON==null) {
			return null;
		}
		Map<Integer, Measurand> measurandMap = this.getMeasurandMap();
		return DataObjectBuilder.buildMinimalSensor(sensorJSON, measurandMap);
	}

	public Map<Integer, License> getLicenseMap() throws IOException{
		RequestSender rs = new RequestSender();
		JSONArray licenseArrayJSON = rs.arrayGETRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/licenses");
		if(licenseArrayJSON==null) {
			return null;
		}
		HashMap<Integer, License> licenseMap = new HashMap<>();
		for(Object o : licenseArrayJSON) {
			if(!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject licenseJSON = (JSONObject) o;
			License l = DataObjectBuilder.buildLicense(licenseJSON);
			if(l!=null) {
				licenseMap.put(l.getId(),l);
			}
		}
		return licenseMap;
	}
	
	public String sendCreateSensorRequest(CreateSensorRequest request) throws IOException {
		RequestSender rs = new RequestSender();
		JSONObject bodyJSON = new JSONObject();
		bodyJSON.put(JsonAttributes.MEASURAND_ID.getNameString(), request.getMeasurandId());
		bodyJSON.put(JsonAttributes.UNIT_ID.getNameString(), request.getUnitId());
		JSONObject location = new JSONObject();
		location.put(JsonAttributes.LAT.getNameString(), request.getLatitude());
		location.put(JsonAttributes.LNG.getNameString(), request.getLongitude());
		bodyJSON.put(JsonAttributes.LOCATION.getNameString(), location);
		bodyJSON.put(JsonAttributes.LICENSE_ID.getNameString(), request.getLicenseId());
		bodyJSON.put(JsonAttributes.ALTITUDE_ABOVE_GROUND.getNameString(), request.getAltitudeAboveGround());
		bodyJSON.put(JsonAttributes.DIRECTION_VERTICAL.getNameString(), request.getDirectionVertical());
		bodyJSON.put(JsonAttributes.DIRECTION_HORIZONTAL.getNameString(), request.getDirectionHorizontal());
		bodyJSON.put(JsonAttributes.SENSOR_MODEL.getNameString(), request.getSensorModel());
		bodyJSON.put(JsonAttributes.ACCURACY.getNameString(), request.getAccuracy());
		bodyJSON.put(JsonAttributes.ATTRIBUTION_TEXT.getNameString(), request.getAttributionText());
		bodyJSON.put(JsonAttributes.ATTRIBUTION_URL.getNameString(), request.getAttributionURL());
		String body = bodyJSON.toString();
		JSONObject idJSON = rs.objectPOSTRequest((USE_DEFAULT_URL ? BASE_URL_DEFAULT : BASE_URL)+"/sensors/addSensor", body, SessionUser.getInstance().getToken());
		if(idJSON==null) {
			return null;
		}
		return idJSON.toString();
	}

}
