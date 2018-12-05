package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Sensor implements IsSerializable{
	/**
	 * 
	 */
	private int userId; 
	private double directionHorizontal;
	private double directionVertical;
	private String attributionText;
	private Measurand measurand;
	private int licenseId;
	private double altitudeAboveGround;
	private int sensorId; 
	private String attributionURLString;
	private String sensorModel;
	private Unit unit;
	private double accuracy;
	private LatLng location;
	private ValuePreview valuePreview;
	
	public Sensor() {
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
	public int getSensorId() {
		return sensorId;
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
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
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
	public void setSensorId(int id) {
		this.sensorId = id;
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
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the location
	 */
	public LatLng getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LatLng location) {
		this.location = location;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * @return the measurand
	 */
	public Measurand getMeasurand() {
		return measurand;
	}

	/**
	 * @param measurand the measurand to set
	 */
	public void setMeasurand(Measurand measurand) {
		this.measurand = measurand;
	}

	/**
	 * @return the valuePreview
	 */
	public ValuePreview getValuePreview() {
		return valuePreview;
	}

	/**
	 * @param valuePreview the valuePreview to set
	 */
	public void setValuePreview(ValuePreview valuePreview) {
		this.valuePreview = valuePreview;
	}
	
}
