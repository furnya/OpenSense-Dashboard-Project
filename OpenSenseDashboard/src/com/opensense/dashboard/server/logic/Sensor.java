package com.opensense.dashboard.server.logic;


import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensense.dashboard.server.util.Location;

public class Sensor implements Serializable{
	private final JSONObject rawJSON;
	private final int userId;
	private final User user;
	private final double directionHorizontal;
	private final double directionVertical;
	private final String attributionText;
	private final int measurandId;
	private final Measurand measurand;
	private final int licenseId;
	private final License license;
	private final double altitudeAboveGround;
	private final int id;
	private final String attributionURLString;
	private final URL attributionURL;
	private final String sensorModel;
	private final Location location;
	private final int unitId;
	private final Unit unit;
	private final double accuracy;
	private LinkedList<Value> values;
	
	public Sensor(JSONObject sensor) throws JSONException{
		this.rawJSON = sensor;
		values = new LinkedList<Value>();
		this.userId = sensor.getInt("userId");
		User user = DataHandler.getUsers().get(this.userId);
		//TODO add user to DataHandler users if non-existent
		this.user = user;
		this.directionHorizontal = sensor.getDouble("directionHorizontal");
		this.directionVertical = sensor.getDouble("directionVertical");
		this.attributionText = sensor.getString("attributionText");
		this.measurandId = sensor.getInt("measurandId");
		Measurand measurand = DataHandler.getMeasurands().get(this.measurandId);
		//TODO add measurand to DataHandler measurands if non-existent
		this.measurand = measurand;
		this.licenseId = sensor.getInt("licenseId");
		License license = DataHandler.getLicenses().get(this.licenseId);
		//TODO add license to DataHandler licenses if non-existent
		this.license = license;
		this.altitudeAboveGround = sensor.getDouble("altitudeAboveGround");
		this.id = sensor.getInt("id");
		this.attributionURLString = sensor.getString("attributionURL");
		URL tryURL;
		try {
			tryURL = new URL(this.getAttributionURLString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			tryURL = null;
		}
		this.attributionURL = tryURL;
		this.sensorModel = sensor.getString("sensorModel");
		JSONObject locationJSON = sensor.getJSONObject("location");
		this.location = new Location(String.valueOf(this.getId()), locationJSON.getDouble("lat"), locationJSON.getDouble("lng"));
		this.unitId = sensor.getInt("unitId");
		Unit unit = DataHandler.getUnits().get(this.unitId);
		//TODO add unit to DataHandler units if non-existent
		this.unit = unit;
		this.accuracy = sensor.getDouble("accuracy");
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
	 * @return the attributionURL
	 */
	public URL getAttributionURL() {
		return attributionURL;
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
		return this.rawJSON.toString();
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
			valuesToAdd.add(new Value(timestamp,valueJSON.getDouble("numberValue"),this,this.getMeasurand()));
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the measurand
	 */
	public Measurand getMeasurand() {
		return measurand;
	}

	/**
	 * @return the license
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}
	
	
}
