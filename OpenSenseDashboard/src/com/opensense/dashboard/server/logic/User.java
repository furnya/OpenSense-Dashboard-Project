package com.opensense.dashboard.server.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public class User {

	private final JSONObject rawJSON;
	private final int id;
	private final String username;
	//private String password;
	private final String email;
	private final String emailVerified;
	private final Date created;
	private HashMap<Integer, Sensor> sensors;
	
	public User(JSONObject unit){
		this.rawJSON = unit;
		this.id = unit.getInt("id");
		this.username = unit.getString("username");
		this.email = unit.getString("email");
		this.emailVerified = unit.getString("emailVerified");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		Date timestamp = null;
		try {
			timestamp = inputFormat.parse(unit.getString("created"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.created = timestamp;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return the emailVerified
	 */
	public String getEmailVerified() {
		return emailVerified;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

}
