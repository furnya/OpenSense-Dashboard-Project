package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Request implements IsSerializable{

	private List<Integer> ids;
	private List<Parameter> parameters;
	private ResultType resultType;
	private DateRange dateRange;

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
		return this.parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public ResultType getRequestType() {
		return this.resultType;
	}

	public void setRequestType(ResultType requestType) {
		this.resultType = requestType;
	}

	public List<Integer> getIds() {
		return this.ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	/**
	 * @return the dateRange
	 */
	public DateRange getDateRange() {
		return this.dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
}
