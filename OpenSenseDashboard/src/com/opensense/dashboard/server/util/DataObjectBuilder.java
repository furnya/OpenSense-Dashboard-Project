package com.opensense.dashboard.server.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensense.dashboard.shared.LatLng;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;
import com.opensense.dashboard.shared.ValuePreview;

public class DataObjectBuilder {
	
	private DataObjectBuilder() {
	}
	
	public static Unit buildUnit(JSONObject unitJSON) {
		Unit u = new Unit();
		String nameJSON;
		try {
			u.setId(unitJSON.getInt(JsonAttributes.ID.getNameString()));
			u.setMeasurandId(unitJSON.getInt(JsonAttributes.MEASURAND_ID.getNameString()));
			nameJSON = unitJSON.getString(JsonAttributes.NAME.getNameString());
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
		UnitType ut = UnitType.DEFAULT;
		for(UnitType uType : UnitType.values()) {
			if(uType.getDisplayName().equals(nameJSON)) {
				ut = uType;
				break;
			}
		}
		if(ut==UnitType.DEFAULT) {
			u.setDisplayName(nameJSON);
		}else {
			u.setDisplayName(ServerLanguages.getUnitName(ut));
		}
		return u;
	}
	
	public static Measurand buildMeasurand(JSONObject measurandJSON) {
		Measurand m = new Measurand();
		String nameJSON;
		try {
			m.setId(measurandJSON.getInt(JsonAttributes.ID.getNameString()));
			m.setDefaultUnitId(measurandJSON.getInt(JsonAttributes.DEFAULT_UNIT_ID.getNameString()));
			nameJSON = measurandJSON.getString(JsonAttributes.NAME.getNameString());
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
		m.setMeasurandType(MeasurandType.DEFAULT);
		for(MeasurandType mType : MeasurandType.values()) {
			if(mType.getNameString().equals(nameJSON)){
				m.setMeasurandType(mType);
				break;
			}
		}
		if(m.getMeasurandType()==MeasurandType.DEFAULT) {
			m.setDisplayName(nameJSON);
		}else {
			m.setDisplayName(ServerLanguages.getMeasurandName(m.getMeasurandType()));
		}
		return m;
	}
	
	public static Sensor buildSensor(JSONObject sensorJSON, Map<Integer, Measurand> measurandMap, Map<Integer, Unit> unitMap, ValuePreview valuePreview) {
		Sensor s = new Sensor();
		try {
			s.setSensorId(sensorJSON.getInt(JsonAttributes.ID.getNameString()));
			s.setAccuracy(sensorJSON.getDouble(JsonAttributes.ACCURACY.getNameString()));
			s.setUserId(sensorJSON.getInt(JsonAttributes.USER_ID.getNameString()));
			s.setDirectionHorizontal(sensorJSON.getDouble(JsonAttributes.DIRECTION_HORIZONTAL.getNameString()));
			s.setDirectionVertical(sensorJSON.getDouble(JsonAttributes.DIRECTION_VERTICAL.getNameString()));
			s.setAttributionText(sensorJSON.getString(JsonAttributes.ATTRIBUTION_TEXT.getNameString()));
			s.setMeasurand(measurandMap.get(sensorJSON.getInt(JsonAttributes.MEASURAND_ID.getNameString())));
			s.setLicenseId(sensorJSON.getInt(JsonAttributes.LICENSE_ID.getNameString()));
			s.setAltitudeAboveGround(sensorJSON.getDouble(JsonAttributes.ALTITUDE_ABOVE_GROUND.getNameString()));
			s.setAttributionURLString(sensorJSON.getString(JsonAttributes.ATTRIBUTION_URL.getNameString()));
			s.setSensorModel(sensorJSON.getString(JsonAttributes.SENSOR_MODEL.getNameString()));
			JSONObject locationJSON = sensorJSON.getJSONObject(JsonAttributes.LOCATION.getNameString());
			s.setLocation(new LatLng(locationJSON.getDouble(JsonAttributes.LAT.getNameString()), locationJSON.getDouble(JsonAttributes.LNG.getNameString())));
			s.setUnit(unitMap.get(sensorJSON.getInt(JsonAttributes.UNIT_ID.getNameString())));
			s.setValuePreview(valuePreview);
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	public static Value buildValue(JSONObject valueJSON, SimpleDateFormat inputFormat) {
		Value v = new Value();
		try {
			v.setTimestamp(inputFormat.parse(valueJSON.getString(JsonAttributes.TIMESTAMP.getNameString())));
			v.setNumberValue(valueJSON.getDouble(JsonAttributes.NUMBER_VALUE.getNameString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return v;
	}

}
