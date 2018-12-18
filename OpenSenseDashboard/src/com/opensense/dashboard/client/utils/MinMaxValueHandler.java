package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opensense.dashboard.shared.Value;

public class MinMaxValueHandler {
	
	private Map<Integer,List<Value>> values;
	
	private Value maxValue = null;
	private Value minValue = null;
	private Value earliestValue = null;
	private Value latestValue = null;
	
	public MinMaxValueHandler() {
		values = new HashMap<>();
	}

	/**
	 * @return the values
	 */
	public List<Value> getValues(Integer id) {
		return values.get(id);
	}
	
	public void resetMinMax() {
		maxValue = null;
		minValue = null;
		earliestValue = null;
		latestValue = null;
	}
	
	public boolean calculateMinMax() {
		Value maxValueBefore = maxValue;
		Value minValueBefore = minValue;
		Value earliestValueBefore = earliestValue;
		Value latestValueBefore = latestValue;
		for(List<Value> vList : values.values()) {
			for(Value v : vList) {
				if(minValue==null || v.getNumberValue()<minValue.getNumberValue()) {
					minValue = v;
				}
				if(maxValue==null || v.getNumberValue()>maxValue.getNumberValue()) {
					maxValue = v;
				}
				if(earliestValue==null || v.getTimestamp().compareTo(earliestValue.getTimestamp())<0) {
					earliestValue = v;
				}
				if(latestValue==null || v.getTimestamp().compareTo(latestValue.getTimestamp())>0) {
					latestValue = v;
				}
			}
		}
		return (maxValueBefore!=maxValue || minValueBefore!=minValue || earliestValueBefore!=earliestValue || latestValueBefore!=latestValue);
	}
	
	public Value getMin() {
		return minValue;
	}
	
	public Value getMax() {
		return maxValue;
	}
	
	public Value getEarliest() {
		return earliestValue;
	}
	
	public Value getLatest() {
		return latestValue;
	}
	
	public boolean setValuesForId(Integer id, List<Value> newValues) {
		this.values.put(id,newValues);
		return this.calculateMinMax();
	}
	
	public boolean addValueForId(Integer id, Value newValue) {
		if(!this.values.containsKey(id)) {
			this.values.put(id,new LinkedList<>());
		}
		this.values.get(id).add(newValue);
		return this.calculateMinMax();
	}
	
	public boolean addValuesForId(Integer id, List<Value> newValues) {
		if(!this.values.containsKey(id)) {
			this.values.put(id,new LinkedList<>());
		}
		this.values.get(id).addAll(newValues);
		return this.calculateMinMax();
	}
	
	public boolean removeValues(Integer id) {
		List<Value> removedValues = this.values.remove(id);
		if(removedValues.contains(minValue) || removedValues.contains(maxValue) || removedValues.contains(earliestValue) || removedValues.contains(latestValue)) {
			this.resetMinMax();
			return this.calculateMinMax();
		}
		return false;
	}
	
	public void reset() {
		this.resetMinMax();
		this.values = new HashMap<>();
	}
}
