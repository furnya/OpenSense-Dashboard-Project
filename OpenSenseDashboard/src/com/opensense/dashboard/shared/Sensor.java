package com.opensense.dashboard.shared;


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

import com.google.gwt.user.client.rpc.IsSerializable;
import com.opensense.dashboard.server.util.DataHandler;
import com.opensense.dashboard.server.util.Location;

public class Sensor implements IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9092878107269506316L;
	private final JSONObject rawJSON;
	private final int userId;
	private final double directionHorizontal;
	private final double directionVertical;
	private final String attributionText;
	private final int measurandId;
	private final int licenseId;
	private final double altitudeAboveGround;
	private final int id;
	private final String attributionURLString;
	private final String sensorModel;
	private final Location location;
	private final int unitId;
	private final double accuracy;
	private LinkedList<Value> values;
	
	public Sensor(JSONObject sensor) throws JSONException{
		this.rawJSON = sensor;
		values = new LinkedList<Value>();
		this.userId = sensor.getInt("userId");
		this.directionHorizontal = sensor.getDouble("directionHorizontal");
		this.directionVertical = sensor.getDouble("directionVertical");
		this.attributionText = sensor.getString("attributionText");
		this.measurandId = sensor.getInt("measurandId");
		this.licenseId = sensor.getInt("licenseId");
		this.altitudeAboveGround = sensor.getDouble("altitudeAboveGround");
		this.id = sensor.getInt("id");
		this.attributionURLString = sensor.getString("attributionURL");
		this.sensorModel = sensor.getString("sensorModel");
		JSONObject locationJSON = sensor.getJSONObject("location");
		this.location = new Location(String.valueOf(this.getId()), locationJSON.getDouble("lat"), locationJSON.getDouble("lng"));
		this.unitId = sensor.getInt("unitId");
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
	
}
