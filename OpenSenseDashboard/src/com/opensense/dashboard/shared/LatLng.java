package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LatLng implements IsSerializable {
	double lat;
	double lon;
	
	public LatLng() {
	}

	public LatLng(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
}
