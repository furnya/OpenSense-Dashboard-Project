package com.opensense.dashboard.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.ResultType;

public class RequestBuilder {
	
	private static final String MAX_SENSOR_REQUEST = "1000";
	
	private Request request;
	
	private boolean parameterRequest;
	
	public RequestBuilder(ResultType resultType, boolean parameterRequest) {
		this.parameterRequest = parameterRequest;
		request = new Request(resultType);
		if(this.parameterRequest) {
			request.setParameters(new ArrayList<Parameter>());
		}else {
			request.setIds(new ArrayList<Integer>());
		}
	}
	
	public void addParameter(ParamType paramType, String value) {
		addParameter(new Parameter(paramType.getValue(), value));
	}	
	
	public void addId(Integer id) {
		if(id != null) {
			request.getIds().add(id);
		}
	}
	
	private void addParameter(Parameter parameter) {
		request.getParameters().add(parameter);
	}
	
	public void setIds(List<Integer> ids) {
		request.setIds(ids);
	}
	
	/**
	 * checks if the request contains maxSensors -> false the maxSensor param will be set to {@link #MAX_SENSOR_REQUEST}
	 * @return the builded and checked request
	 */
	public Request getRequest() {
		if(this.parameterRequest) {
			checkParameters();
		}
		return request;
	}
	
	private void checkParameters() {
		boolean containsNeededParams = false;
		for (Parameter param : request.getParameters()) {
			if(ParamType.MAX_SENSORS.getValue().equals(param.getKey())) {
				containsNeededParams = true;
				break;
			}
		}
		if(!containsNeededParams) {
			addParameter(ParamType.MAX_SENSORS, MAX_SENSOR_REQUEST);
		}
	}
}
