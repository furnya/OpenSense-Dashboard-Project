package com.opensense.dashboard.server.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class DataHandler {
	
	private static HashMap<Integer, Sensor> sensors = new HashMap<>();
	private static HashMap<Integer, Measurand> measurands = new HashMap<>();
	private static HashMap<Integer, User> users = new HashMap<>();
	private static HashMap<Integer, Unit> units = new HashMap<>();
	private static HashMap<Integer, License> licenses = new HashMap<>();
	private static LinkedList<Value> values = new LinkedList<>();
	
	public DataHandler() {
	}

	/**
	 * @return the sensors
	 */
	public static Map<Integer, Sensor> getSensors() {
		return sensors;
	}
	
	/**
	 * @param sensorId
	 * @return the respective sensor, if it exists
	 */
	public static Sensor getSensor(int sensorId) {
		Sensor sensor = getSensors().get(sensorId);
		if(sensor == null) {
			RequestSender rs = new RequestSender();
			JSONObject sensorJSON = rs.objectRequest("https://www.opensense.network/beta/api/v1.0/sensors/"+String.valueOf(sensorId));
			try {
				sensor = new Sensor(sensorJSON);
			} catch (JSONException e) {
				System.out.println("Sensor you are looking for does not exist");
				sensor = null;
			}
		}
		if(!getSensors().containsValue(sensor)) {
			addSensor(sensor);
		}
		return sensor;
	}

	/**
	 * @param sensor the sensor to be added
	 */
	public static void addSensor(Sensor sensor) {
		DataHandler.sensors.put(sensor.getId(), sensor);
	}

	/**
	 * @return the measurands
	 */
	public static Map<Integer, Measurand> getMeasurands() {
		return measurands;
	}

	/**
	 * @param measurand the measurand to be added
	 */
	public static void addMeasurands(Measurand measurand) {
		DataHandler.measurands.put(measurand.getId(),measurand);
	}

	/**
	 * @return the users
	 */
	public static Map<Integer, User> getUsers() {
		return users;
	}

	/**
	 * @param user the user to be added
	 */
	public static void addUser(User user) {
		DataHandler.users.put(user.getId(), user);
	}

	/**
	 * @return the units
	 */
	public static Map<Integer, Unit> getUnits() {
		return units;
	}

	/**
	 * @param unit the unit to be added
	 */
	public static void addUnit(Unit unit) {
		DataHandler.units.put(unit.getId(), unit);
	}

	/**
	 * @return the licenses
	 */
	public static Map<Integer, License> getLicenses() {
		return licenses;
	}

	/**
	 * @param license the license to be added
	 */
	public static void addLicense(License license) {
		DataHandler.licenses.put(license.getId(), license);
	}

	/**
	 * @return the values
	 */
	public static List<Value> getValues() {
		return values;
	}

	/**
	 * @param value the value to be added
	 */
	public static void addValue(Value value) {
		DataHandler.values.add(value);
	}

}
