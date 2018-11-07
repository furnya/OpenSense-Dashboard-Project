package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchRequest implements IsSerializable{
	
	private List<Parameter> parameters;
	private RequestType requestType;
	
	public SearchRequest() {
	}
	
	public SearchRequest(List<Parameter> parameters, RequestType requestType) {
		this.parameters = parameters;
		this.requestType = requestType;
	}
	
	public SearchRequest(RequestType requestType) {
		this.requestType = requestType;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
}
