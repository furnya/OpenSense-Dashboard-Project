package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Request implements IsSerializable{
	
	private Integer id;
	private List<Parameter> parameters;
	private ResultType resultType;
	
	public Request() {
	}
	
	public Request(Integer id, List<Parameter> parameters, ResultType requestType) {
		this.id = id;
		this.parameters = parameters;
		this.resultType = requestType;
	}
	
	public Request(List<Parameter> parameters, ResultType requestType) {
		this.parameters = parameters;
		this.resultType = requestType;
	}
	
	public Request(ResultType requestType) {
		this.resultType = requestType;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public ResultType getRequestType() {
		return resultType;
	}

	public void setRequestType(ResultType requestType) {
		this.resultType = requestType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
