package com.opensense.dashboard.shared;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Response implements IsSerializable {

	private ResultType resultType;
	private Sensor sensor; //TODO: not needed
	private List<Sensor> sensors;
	private Map<Integer, Measurand> measurands;
	private Map<Integer, Unit> units;  //TODO: not needed
	private List<Value> values;
	private Map<Integer, ValuePreview> valuePreviews;
	private List<MinimalSensor> minimalSensors;
	private List<UserList> userLists;
	private List<Integer> myListSensors;
	private Map<Integer, License> licenses;

	public Response() {
	}

	public ResultType getResultType() {
		return this.resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public List<Sensor> getSensors() {
		return this.sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	/**
	 * @return the measurands
	 */
	public Map<Integer, Measurand> getMeasurands() {
		return this.measurands;
	}

	/**
	 * @param measurands the measurands to set
	 */
	public void setMeasurands(Map<Integer, Measurand> measurands) {
		this.measurands = measurands;
	}

	/**
	 * @return the units
	 */
	public Map<Integer, Unit> getUnits() {
		return this.units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Map<Integer, Unit> units) {
		this.units = units;
	}

	/**
	 * @return the sensor
	 */
	public Sensor getSensor() {
		return this.sensor;
	}

	/**
	 * @param sensor the sensor to set
	 */
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	/**
	 * @return the values
	 */
	public List<Value> getValues() {
		return this.values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Map<Integer, ValuePreview> getValuePreviews() {
		return this.valuePreviews;
	}

	public void setValuePreviews(Map<Integer, ValuePreview> valuePreviews) {
		this.valuePreviews = valuePreviews;
	}

	public List<MinimalSensor> getMinimalSensors() {
		return this.minimalSensors;
	}

	public void setMinimalSensors(List<MinimalSensor> minimalSensors) {
		this.minimalSensors = minimalSensors;
	}

	public List<UserList> getUserLists() {
		return this.userLists;
	}

	public void setUserLists(List<UserList> userLists) {
		this.userLists = userLists;
	}

	/**
	 * @return the myListSensors
	 */
	public List<Integer> getMyListSensors() {
		return myListSensors;
	}

	/**
	 * @param myListSensors the myListSensors to set
	 */
	public void setMySensors(List<Integer> myListSensors) {
		this.myListSensors = myListSensors;
	}

	/**
	 * @return the licenses
	 */
	public Map<Integer, License> getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses the licenses to set
	 */
	public void setLicenses(Map<Integer, License> licenses) {
		this.licenses = licenses;
	}
}
