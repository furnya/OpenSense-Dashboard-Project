package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;

import org.pepstock.charba.client.AbstractChart;

import com.opensense.dashboard.shared.Unit;

public class UnitMapper {

	private static UnitMapper instance;
	
	private Map<Integer,Unit> unitMap = new HashMap<>();
	
	private UnitMapper() {}
	
	public static UnitMapper getInstance() {
		if(instance == null) {
			instance = new UnitMapper();
		}
		return instance;
	}
	
	public void putUnit(Integer sensorId, Unit unit) {
		unitMap.put(sensorId, unit);
	}
	
	public Map<Integer,Unit> getUnitMap(AbstractChart<?, ?> chart){
		return unitMap;
	}
	
	public Unit getUnit(Integer sensorId) {
		return unitMap.get(sensorId);
	}
}
