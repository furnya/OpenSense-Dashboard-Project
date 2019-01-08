package com.opensense.dashboard.client.utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;

import com.opensense.dashboard.shared.Value;

public class ValueHandler {

	private List<Value> values;
	
	public ValueHandler() {
	}
	
	public ValueHandler(List<Value> values) {
		this.setValues(values);
	}

	/**
	 * @return the values
	 */
	public List<Value> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Value> values) {
		this.values = values;
	}
	
	public Value getMin() {
		if(values==null) return null;
		Value minValue = values.get(0);
		for(Value v : values) {
			if(v.getNumberValue()<minValue.getNumberValue()) {
				minValue = v;
			}
		}
		return minValue;
	}
	
	public Value getMax() {
		if(values==null) return null;
		Value maxValue = values.get(0);
		for(Value v : values) {
			if(v.getNumberValue()>maxValue.getNumberValue()) {
				maxValue = v;
			}
		}
		return maxValue;
	}
	
	public Double getSum() {
		Double sum = 0.0;
		for(Value v : values) {
			sum += v.getNumberValue();
		}
		return sum;
	}
	
	public Double getAverage() {
		if(values==null) return Double.NaN;
		return getSum()/values.size();
	}
	
	public Value getEarliest() {
		if(values==null) return null;
		Value minValue = values.get(0);
		for(Value v : values) {
			if(v.getTimestamp().compareTo(minValue.getTimestamp())<0) {
				minValue = v;
			}
		}
		return minValue;
	}
	
	public static DataPoint getMinOfDataset(LineDataset dataset) {
		if(dataset==null || dataset.getDataPoints()==null || dataset.getDataPoints().isEmpty()) return null;
		DataPoint minValue = dataset.getDataPoints().get(0);
		for(DataPoint dp : dataset.getDataPoints()) {
			if(dp.getY()<minValue.getY()) {
				minValue = dp;
			}
		}
		return minValue;
	}
	
	public static DataPoint getMinOfDatasets(List<LineDataset> datasets) {
		if(datasets==null || datasets.isEmpty()) return null;
		DataPoint minValue = getMinOfDataset(datasets.get(0));
		for(LineDataset dataset : datasets) {
			DataPoint m = getMinOfDataset(dataset);
			if(minValue==null || (m!=null && m.getY()<minValue.getY())) {
				minValue = m;
			}
		}
		return minValue;
	}
	
	public static DataPoint getMaxOfDataset(LineDataset dataset) {
		if(dataset==null || dataset.getDataPoints()==null || dataset.getDataPoints().isEmpty()) return null;
		DataPoint maxValue = dataset.getDataPoints().get(0);
		for(DataPoint dp : dataset.getDataPoints()) {
			if(dp.getY()>maxValue.getY()) {
				maxValue = dp;
			}
		}
		return maxValue;
	}
	
	public static DataPoint getMaxOfDatasets(List<LineDataset> datasets) {
		if(datasets==null || datasets.isEmpty()) return null;
		DataPoint maxValue = getMinOfDataset(datasets.get(0));
		for(LineDataset dataset : datasets) {
			DataPoint m = getMinOfDataset(dataset);
			if(maxValue==null || (m!=null && m.getY()>maxValue.getY())) {
				maxValue = m;
			}
		}
		return maxValue;
	}
	
	public Value getLatest() {
		if(values==null) return null;
		Value maxValue = values.get(0);
		for(Value v : values) {
			if(v.getTimestamp().compareTo(maxValue.getTimestamp())>0) {
				maxValue = v;
			}
		}
		return maxValue;
	}
	
	public List<Value> filterByDate(Date from, Date to){
		List<Value> filteredValues = new LinkedList<>();
		for(Value v : values) {
			if(from.compareTo(v.getTimestamp())<=0 && to.compareTo(v.getTimestamp())>=0) {
				filteredValues.add(v);
			}
		}
		return filteredValues;
	}
	
	public List<Value> filterByValue(Double from, Double to){
		List<Value> filteredValues = new LinkedList<>();
		for(Value v : values) {
			if(from<=v.getNumberValue() && to>=v.getNumberValue()) {
				filteredValues.add(v);
			}
		}
		return filteredValues;
	}
	
	public void addValues(List<Value> newValues) {
		newValues.forEach(this.values::add);
	}
	
	public void addValue(Value newValue) {
		this.values.add(newValue);
	}

}
