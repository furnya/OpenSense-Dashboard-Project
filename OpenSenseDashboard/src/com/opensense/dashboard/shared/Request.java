package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Request implements IsSerializable{
	
	private List<Integer> ids; //TODO: List<String> ?
	private List<Parameter> parameters;
	private ResultType resultType;
	private DateRange dateRange;
	private String username;
	private String password;
	
	public Request() {
	}
	
	public Request(List<Integer> ids, List<Parameter> parameters, ResultType requestType) {
		this.ids = ids;
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

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	/**
	 * @return the dateRange
	 */
	public DateRange getDateRange() {
		return dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
