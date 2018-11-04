package com.opensense.dashboard.shared;

import java.util.HashMap;

import org.json.JSONObject;

public class Unit {

	private final JSONObject rawJSON;
	private final int id;
	private final String name;
	private HashMap<Integer, Sensor> sensors;
	private HashMap<Integer, Measurand> measurands;
	
	public Unit(JSONObject unit, Measurand measurand){
		this.rawJSON = unit;
		this.id = unit.getInt("id");
		this.name = unit.getString("name");
		this.measurands = new HashMap<Integer, Measurand>();
		addMeasurand(measurand);
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
	 * @return the measurands
	 */
	public HashMap<Integer, Measurand> getMeasurands() {
		return measurands;
	}


	/**
	 * @param measurand the measurand to be added
	 */
	public void addMeasurand(Measurand measurand) {
		this.measurands.put(measurand.getId(),measurand);
	}

}
