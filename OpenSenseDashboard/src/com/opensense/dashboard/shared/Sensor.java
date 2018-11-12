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
	private int id;
	private String attributionURLString;
	private String sensorModel;
	private int unitId;
	private Unit unit;
	private double accuracy;
	private LatLng location;
	
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
	
}
