package com.opensense.dashboard.shared;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Response implements IsSerializable {
	
	private ResultType resultType;
	private Sensor sensor;
	private List<Sensor> sensors;
	private Map<Integer, Measurand> measurands;
	private Map<Integer, Unit> units;
	private List<Value> values;
	
	public Response() {
	}
	
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	/**
	 * @return the measurands
	 */
	public Map<Integer, Measurand> getMeasurands() {
		return measurands;
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
		return units;
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
		return sensor;
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
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Value> values) {
		this.values = values;
	}
}
