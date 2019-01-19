package com.opensense.dashboard.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.opensense.dashboard.shared.ActionResult;

@RemoteServiceRelativePath("../authentication")
public interface AuthenticationService extends RemoteService {
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
	    
		private static AuthenticationServiceAsync instance;

		private Util() {
		    // Hides the implicit public constructor when no other constructor is present.
		}
		
		public static AuthenticationServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(AuthenticationService.class);
			}
			return instance;
		}
	}

	ActionResult userLoginRequest(String userName, String password);
	Boolean createUserInSession();
	ActionResult userLoggedOut();
	ActionResult userRegisterRequest(String username, String password, String email);
	ActionResult forgotPasswordRequest(String email);
}
