package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CreateSensorRequest implements IsSerializable{

	private int measurandId;
	private int unitId;
	private int licenseId;
	private double latitude;
	private double longitude;
	private double altitudeAboveGround;
	private double directionVertical;
	private double directionHorizontal;
	private String sensorModel;
	private double accuracy;
	private String attributionText;
	private String attributionURL;

	public CreateSensorRequest() {
	}

	/**
	 * @return the measurandId
	 */
	public int getMeasurandId() {
		return this.measurandId;
	}

	/**
	 * @param measurandId the measurandId to set
	 */
	public void setMeasurandId(int measurandId) {
		this.measurandId = measurandId;
	}

	/**
	 * @return the unitId
	 */
	public int getUnitId() {
		return this.unitId;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the licenseId
	 */
	public int getLicenseId() {
		return this.licenseId;
	}

	/**
	 * @param licenseId the licenseId to set
	 */
	public void setLicenseId(int licenseId) {
		this.licenseId = licenseId;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the altitudeAboveGround
	 */
	public double getAltitudeAboveGround() {
		return this.altitudeAboveGround;
	}

	/**
	 * @param altitudeAboveGround the altitudeAboveGround to set
	 */
	public void setAltitudeAboveGround(double altitudeAboveGround) {
		this.altitudeAboveGround = altitudeAboveGround;
	}

	/**
	 * @return the directionVertical
	 */
	public double getDirectionVertical() {
		return this.directionVertical;
	}

	/**
	 * @param directionVertical the directionVertical to set
	 */
	public void setDirectionVertical(double directionVertical) {
		this.directionVertical = directionVertical;
	}

	/**
	 * @return the directionHorizontal
	 */
	public double getDirectionHorizontal() {
		return this.directionHorizontal;
	}

	/**
	 * @param directionHorizontal the directionHorizontal to set
	 */
	public void setDirectionHorizontal(double directionHorizontal) {
		this.directionHorizontal = directionHorizontal;
	}

	/**
	 * @return the sensorModel
	 */
	public String getSensorModel() {
		return this.sensorModel;
	}

	/**
	 * @param sensorModel the sensorModel to set
	 */
	public void setSensorModel(String sensorModel) {
		this.sensorModel = sensorModel;
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return this.accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the attributionText
	 */
	public String getAttributionText() {
		return this.attributionText;
	}

	/**
	 * @param attributionText the attributionText to set
	 */
	public void setAttributionText(String attributionText) {
		this.attributionText = attributionText;
	}

	/**
	 * @return the attributionURL
	 */
	public String getAttributionURL() {
		return this.attributionURL;
	}

	/**
	 * @param attributionURL the attributionURL to set
	 */
	public void setAttributionURL(String attributionURL) {
		this.attributionURL = attributionURL;
	}
}
