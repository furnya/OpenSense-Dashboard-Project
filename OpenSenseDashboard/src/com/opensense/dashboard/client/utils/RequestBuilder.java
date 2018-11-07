package com.opensense.dashboard.client.utils;

import java.util.ArrayList;

import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.ResultType;

public class RequestBuilder {
	
	private Request request;
	
	public RequestBuilder(ResultType resultType) {
		request = new Request(resultType);
		request.setParameters(new ArrayList<Parameter>());
	}
	
	public void addParameter(ParamType paramType, String value) {
		addParameter(new Parameter(paramType.getValue(), value));
	}
	
	private void addParameter(Parameter parameter) {
		request.getParameters().add(parameter);
	}
	
	public void setId(int id) {
		request.setId(id);
	}

	public Request getRequest() {
		return request;
	}

	
}
