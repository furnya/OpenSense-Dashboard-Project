package com.opensense.dashboard.shared;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.opensense.dashboard.server.util.Location;

public class Sensor implements IsSerializable{
	/**
	 * 
	 */
	private JSONObject rawJSON;
	private int userId;
	private double directionHorizontal;
	private double directionVertical;
	private String attributionText;
	private int measurandId;
	private int licenseId;
	private double altitudeAboveGround;
	private int id;
	private String attributionURLString;
	private String sensorModel;
	private Location location;
	private int unitId;
	private double accuracy;
	private LinkedList<Value> values;
	
	public Sensor() {
	}	
	
	public Sensor(JSONObject sensor) throws JSONException{
		this.setRawJSON(sensor);
		values = new LinkedList<Value>();
		this.setUserId(sensor.getInt("userId"));
		this.setDirectionHorizontal(sensor.getDouble("directionHorizontal"));
		this.setDirectionVertical(sensor.getDouble("directionVertical"));
		this.setAttributionText(sensor.getString("attributionText"));
		this.setMeasurandId(sensor.getInt("measurandId"));
		this.setLicenseId(sensor.getInt("licenseId"));
		this.setAltitudeAboveGround(sensor.getDouble("altitudeAboveGround"));
		this.setId(sensor.getInt("id"));
		this.setAttributionURLString(sensor.getString("attributionURL"));
		this.setSensorModel(sensor.getString("sensorModel"));
		JSONObject locationJSON = sensor.getJSONObject("location");
		this.setLocation(new Location(String.valueOf(this.getId()), locationJSON.getDouble("lat"), locationJSON.getDouble("lng")));
		this.setUnitId(sensor.getInt("unitId"));
		this.setAccuracy(sensor.getDouble("accuracy"));
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @return the directionHorizontal
	 */
	public double getDirectionHorizontal() {
		return directionHorizontal;
	}

	/**
	 * @return the directionVertical
	 */
	public double getDirectionVertical() {
		return directionVertical;
	}

	/**
	 * @return the attributionText
	 */
	public String getAttributionText() {
		return attributionText;
	}

	/**
	 * @return the measurandId
	 */
	public int getMeasurandId() {
		return measurandId;
	}

	/**
	 * @return the licenseId
	 */
	public int getLicenseId() {
		return licenseId;
	}

	/**
	 * @return the altitudeAboveGround
	 */
	public double getAltitudeAboveGround() {
		return altitudeAboveGround;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the attributionURLString
	 */
	public String getAttributionURLString() {
		return attributionURLString;
	}

	/**
	 * @return the sensorModel
	 */
	public String getSensorModel() {
		return sensorModel;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the unitId
	 */
	public int getUnitId() {
		return unitId;
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}
	
	public String toString() {
		return this.getRawJSON().toString();
	}

	/**
	 * @return the values
	 */
	public LinkedList<Value> getValues() {
		return this.values;
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
	public void addMultipleValues(JSONArray values){
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
			valuesToAdd.add(new Value(timestamp,valueJSON.getDouble("numberValue"),this.getId(),this.getMeasurandId()));
		}
		this.addMultipleValues(valuesToAdd);
	}
	
	/**
	 * @param value the value to be added
	 */
	public void addValue(Value value) {
		this.values.add(value);
	}

	/**
	 * @return the rawJSON
	 */
	public JSONObject getRawJSON() {
		return rawJSON;
	}

	/**
	 * @param rawJSON the rawJSON to set
	 */
	public void setRawJSON(JSONObject rawJSON) {
		this.rawJSON = rawJSON;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @param directionHorizontal the directionHorizontal to set
	 */
	public void setDirectionHorizontal(double directionHorizontal) {
		this.directionHorizontal = directionHorizontal;
	}

	/**
	 * @param directionVertical the directionVertical to set
	 */
	public void setDirectionVertical(double directionVertical) {
		this.directionVertical = directionVertical;
	}

	/**
	 * @param attributionText the attributionText to set
	 */
	public void setAttributionText(String attributionText) {
		this.attributionText = attributionText;
	}

	/**
	 * @param measurandId the measurandId to set
	 */
	public void setMeasurandId(int measurandId) {
		this.measurandId = measurandId;
	}

	/**
	 * @param licenseId the licenseId to set
	 */
	public void setLicenseId(int licenseId) {
		this.licenseId = licenseId;
	}

	/**
	 * @param altitudeAboveGround the altitudeAboveGround to set
	 */
	public void setAltitudeAboveGround(double altitudeAboveGround) {
		this.altitudeAboveGround = altitudeAboveGround;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param attributionURLString the attributionURLString to set
	 */
	public void setAttributionURLString(String attributionURLString) {
		this.attributionURLString = attributionURLString;
	}

	/**
	 * @param sensorModel the sensorModel to set
	 */
	public void setSensorModel(String sensorModel) {
		this.sensorModel = sensorModel;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
}
