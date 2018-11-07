package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Response implements IsSerializable {
	
	private ResultType resultType;
	private List<Sensor> sensors;
	//List of Values
	
	public Response() {
	}
	
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
}
