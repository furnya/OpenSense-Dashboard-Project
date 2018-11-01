package com.opensense.dashboard.server.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Measurand {

	private final JSONObject rawJSON;
	private final int id;
	private final String name;
	private final int defaultUnitId;
	private final Unit defaultUnit;
	private HashMap<Integer, Sensor> sensors;
	private LinkedList<Value> values;
	
	public Measurand(JSONObject measurand){
		this.rawJSON = measurand;
		this.id = measurand.getInt("id");
		this.name = measurand.getString("name");
		this.defaultUnitId = measurand.getInt("defaultUnitId");
		Unit defaultUnit = DataHandler.getUnits().get(this.defaultUnitId);
		//TODO add unit to DataHandler units if non-existent
		this.defaultUnit = defaultUnit;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the defaultUnitId
	 */
	public int getDefaultUnitId() {
		return defaultUnitId;
	}


	/**
	 * @return the defaultUnit
	 */
	public Unit getDefaultUnit() {
		return defaultUnit;
	}
	
	public String toString() {
		return this.rawJSON.toString();
	}


	/**
	 * @return the sensors
	 */
	public HashMap<Integer, Sensor> getSensors() {
		return sensors;
	}


	/**
	 * @param sensors the sensors to set
	 */
	public void addSensor(Sensor sensor) {
		this.sensors.put(sensor.getId(), sensor);
	}
	
	/**
	 * @return the values
	 */
	public LinkedList<Value> getValues() {
		return values;
	}
	
	/**
	 * @param value the value to be added
	 */
	public void addValue(Value value) {
		this.values.add(value);
	}
	
	/**
	 * @param values the values to add
	 * @throws ParseException 
	 */
	public void addMultipleValues(LinkedList<Value> values){
		this.values.addAll(values);
	}
	
	/**
	 * @param values the values to add
	 * @throws ParseException 
	 */
	public void addMultipleValues(JSONArray values, Sensor sensor){
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		LinkedList<Value> valuesToAdd = new LinkedList<Value>();
		for(Object value : values) {
			JSONObject valueJSON = (JSONObject) value;
			Date timestamp = null;
			try {
				timestamp = inputFormat.parse(valueJSON.getString("timestamp"));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			valuesToAdd.add(new Value(timestamp,valueJSON.getDouble("numberValue"),sensor,this));
		}
		this.addMultipleValues(valuesToAdd);
	}

}
