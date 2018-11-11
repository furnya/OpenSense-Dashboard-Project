package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Result implements IsSerializable{
	
	private List<Sensor> sensors;
	private List<Measurand> measurands;
	private List<User> users;
	private List<Unit> units;
	private List<License> licenses;
	private List<Value> values;
	
	public Result() {
	}
	
	public Result(	List<Sensor> sensors,
					List<Measurand> measurands,
					List<User> users,
					List<Unit> units,
					List<License> licenses,
					List<Value> values) {
		this.setSensors(sensors);
		this.setMeasurands(measurands);
		this.setUsers(users);
		this.setUnits(units);
		this.setLicenses(licenses);
		this.setValues(values);
	}

	/**
	 * @return the sensors
	 */
	public List<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * @param sensors the sensors to set
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	/**
	 * @return the measurands
	 */
	public List<Measurand> getMeasurands() {
		return measurands;
	}

	/**
	 * @param measurands the measurands to set
	 */
	public void setMeasurands(List<Measurand> measurands) {
		this.measurands = measurands;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the units
	 */
	public List<Unit> getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	/**
	 * @return the licenses
	 */
	public List<License> getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses the licenses to set
	 */
	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}

	/**
	 * @return the values
	 */
	public List<Value> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Value> values) {
		this.values = values;
	}
}
