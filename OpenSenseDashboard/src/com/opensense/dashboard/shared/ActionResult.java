package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ActionResult implements IsSerializable{
	
	private ActionResultType actionResultType;
	private String errorMessage;
	
	@SuppressWarnings("unused")
	private ActionResult() {
		// Empty default constructor for GWT serialization.
	}
	
	public ActionResult(ActionResultType actionResult) {
		this.actionResultType = actionResult;
	}
	
	public ActionResult(ActionResultType actionResult, String errorMessage) {
		this.actionResultType = actionResult;
		this.errorMessage = errorMessage;
	}
	
	public ActionResultType getActionResultType() {
		return actionResultType;
	}
	public void setActionResultType(ActionResultType actionResult) {
		this.actionResultType = actionResult;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
