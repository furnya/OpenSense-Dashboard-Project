package com.opensense.dashboard.server.logic;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import com.opensense.dashboard.server.util.Location;

public class Sensor {
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
	private final URL attributionURL;
	private final String sensorModel;
	private final Location location;
	private final int unitId;
	private final double accuracy;
	private HashMap<Date, Double> values;
	
	public Sensor(JSONObject sensor) throws MalformedURLException {
		this.rawJSON = sensor;
		values = new HashMap<Date, Double>();
		this.userId = sensor.getInt("userId");
		this.directionHorizontal = sensor.getDouble("directionHorizontal");
		this.directionVertical = sensor.getDouble("directionVertical");
		this.attributionText = sensor.getString("attributionText");
		this.measurandId = sensor.getInt("measurandId");
		this.licenseId = sensor.getInt("licenseId");
		this.altitudeAboveGround = sensor.getDouble("altitudeAboveGround");
		this.id = sensor.getInt("id");
		this.attributionURLString = sensor.getString("attributionURL");
		this.attributionURL = new URL(this.getAttributionURLString());
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
	public HashMap<Date, Double> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 * @throws ParseException 
	 */
	public void setValues(JSONArray values) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		for(Object value : values) {
			JSONObject valueJSON = (JSONObject) value;
			this.values.put(inputFormat.parse(valueJSON.getString("timestamp")), valueJSON.getDouble("numberValue"));
		}
	}
	
	
}
