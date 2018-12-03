package com.opensense.dashboard.server.util;

public enum UnitType {
	CELSIUS("celsius"),
	DECIBEL("decibel"),
	PERCENT("percent"),
	LUX("lux"),
	HPA("hPa"),
	MPS("m/s"),
	DEGREES("degrees"),
	LEVEL("level"),
	MM("mm"),
	ENUM("enum"),
	UGPM3("ug/m3"),
	FAHRENHEIT("fahrenheit"),
	KELVIN("kelvin"),
	LUMEN("lumen"),
	MBAR("mbar"),
	KMH("km/h"),
	JPCM2("j/cm2"),
	DEFAULT("");
	
	private final String displayName;
	
	UnitType(String nameString) {
		this.displayName = nameString;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
}
