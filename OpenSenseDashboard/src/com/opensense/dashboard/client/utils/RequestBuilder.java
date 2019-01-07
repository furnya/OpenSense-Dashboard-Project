package com.opensense.dashboard.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.ResultType;

public class RequestBuilder {

	private static final String MAX_SENSOR_REQUEST = "1000";

	private Request request;

	private boolean parameterRequest;

	public RequestBuilder(ResultType resultType, boolean parameterRequest) {
		this.parameterRequest = parameterRequest;
		this.request = new Request(resultType);
		if(this.parameterRequest) {
			this.request.setParameters(new ArrayList<Parameter>());
		}else {
			this.request.setIds(new ArrayList<Integer>());
		}
	}

	public void addParameter(ParamType paramType, String value) {
		this.addParameter(new Parameter(paramType.getValue(), value));
	}

	public void addId(Integer id) {
		if(id != null) {
			this.request.getIds().add(id);
		}
	}

	private void addParameter(Parameter parameter) {
		this.request.getParameters().add(parameter);
	}

	public void setIds(List<Integer> ids) {
		this.request.setIds(ids);
	}

	/**
	 * checks if the request contains maxSensors -> false the maxSensor param will be set to {@link #MAX_SENSOR_REQUEST}
	 * @return the builded and checked request
	 */
	public Request getRequest() {
		if(this.parameterRequest) {
			this.checkParameters();
		}
		return this.request;
	}

	private void checkParameters() {
		boolean containsNeededParams = false;
		for (Parameter param : this.request.getParameters()) {
			if(ParamType.MAX_SENSORS.getValue().equals(param.getKey())) {
				containsNeededParams = true;
				break;
			}
		}
		if(!containsNeededParams && (this.request.getRequestType()==ResultType.SENSOR)) {
			this.addParameter(ParamType.MAX_SENSORS, MAX_SENSOR_REQUEST);
		}
	}

	public void setDateRange(DateRange dateRange) {
		this.request.setDateRange(dateRange);
	}
}
