package com.opensense.dashboard.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public class License {

	private final JSONObject rawJSON;
	private final int id;
	private final String shortName;
	private final String fullName;
	private final String version;
	private final String referenceLink;
	private final String description;
	private Date created;
	private final boolean allowsRedistribution;
	private final boolean allowsDerivatives;
	private final boolean noncommercial;
	private final boolean requiresAttribution;
	private final boolean requiresShareAlike;
	private final boolean requiresKeepOpen;
	private final boolean requiresChangeNote;
	private HashMap<Integer, Sensor> sensors;
	
	public License(JSONObject license){
		this.rawJSON = license;
		this.id = license.getInt("id");
		this.shortName = license.getString("shortName");
		this.fullName = license.getString("fullName");
		this.version = license.getString("version");
		this.referenceLink = license.getString("referenceLink");
		this.description = license.getString("description");
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'");
		Date timestamp = null;
		try {
			timestamp = inputFormat.parse(license.getString("created"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.created = timestamp;
		this.allowsRedistribution = Boolean.parseBoolean(license.getString("allowsRedistribution"));
		this.allowsDerivatives = Boolean.parseBoolean(license.getString("allowsDerivatives"));
		this.noncommercial = Boolean.parseBoolean(license.getString("noncommercial"));
		this.requiresAttribution = Boolean.parseBoolean(license.getString("requiresAttribution"));
		this.requiresShareAlike = Boolean.parseBoolean(license.getString("requiresShareAlike"));
		this.requiresKeepOpen = Boolean.parseBoolean(license.getString("requiresKeepOpen"));
		this.requiresChangeNote = Boolean.parseBoolean(license.getString("requiresChangeNote"));
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}


	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @return the referenceLink
	 */
	public String getReferenceLink() {
		return referenceLink;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the allowsRedistribution
	 */
	public boolean isAllowsRedistribution() {
		return allowsRedistribution;
	}


	/**
	 * @return the allowsDerivatives
	 */
	public boolean isAllowsDerivatives() {
		return allowsDerivatives;
	}


	/**
	 * @return the noncommercial
	 */
	public boolean isNoncommercial() {
		return noncommercial;
	}


	/**
	 * @return the requiresAttribution
	 */
	public boolean isRequiresAttribution() {
		return requiresAttribution;
	}


	/**
	 * @return the requiresShareAlike
	 */
	public boolean isRequiresShareAlike() {
		return requiresShareAlike;
	}


	/**
	 * @return the requiresKeepOpen
	 */
	public boolean isRequiresKeepOpen() {
		return requiresKeepOpen;
	}


	/**
	 * @return the requiresChangeNote
	 */
	public boolean isRequiresChangeNote() {
		return requiresChangeNote;
	}

}
