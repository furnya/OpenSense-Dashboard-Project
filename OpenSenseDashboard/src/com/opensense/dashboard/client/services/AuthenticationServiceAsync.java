package com.opensense.dashboard.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opensense.dashboard.shared.ActionResult;

public interface AuthenticationServiceAsync {

	void userLoginRequest(String userName, String password, AsyncCallback<ActionResult> asyncCallback);
	void createUserInSession(AsyncCallback<Boolean> asyncCallback);
	void userLoggedOut(AsyncCallback<ActionResult> asyncCallback);
	void userRegisterRequest(String username, String password, String email, AsyncCallback<ActionResult> asyncCallback);
	void forgotPasswordRequest(String email, AsyncCallback<ActionResult> asyncCallback);
	void changePassword(String oldPassword, String newPassword,	AsyncCallback<ActionResult> asyncCallback);
}
