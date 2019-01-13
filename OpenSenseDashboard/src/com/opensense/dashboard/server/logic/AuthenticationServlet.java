package com.opensense.dashboard.server.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.DatabaseManager;
import com.opensense.dashboard.server.util.SessionUser;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

@SuppressWarnings("serial")
public class AuthenticationServlet extends RemoteServiceServlet implements AuthenticationService{

	private static final Logger LOGGER = Logger.getLogger(AuthenticationServlet.class.getName());

	/**
	 * Returns boolean true if a user logged in, false the user is guest
	 */
	@Override
	public Boolean createUserInSession() {
		if((System.getenv("DEV_MODE") != null) && "true".equalsIgnoreCase(System.getenv("DEV_MODE"))) {
			ActionResult result = this.userLoginRequest(System.getenv("USERNAME"), System.getenv("PASSWORD"));
			if(ActionResultType.SUCCESSFUL.equals(result.getActionResultType())) {
				return true;
			}
		}
		SessionUser.getInstance().removeUser();
		return false;
	}

	@Override
	public ActionResult userLoginRequest(String username, String password) {
		String body = "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
		String token;
		try {
			token = ClientRequestHandler.getInstance().sendLoginRequest(body);
			SessionUser.getInstance().createUser(1, username, token); //TODO: set our user id to 1 staticly
		} catch (Exception e) {
			SessionUser.getInstance().removeUser();
			LOGGER.log(Level.WARNING, "Failure while login", e);
			return new ActionResult(ActionResultType.FAILED);
		}
		DatabaseManager.initPooling();
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}

	@Override
	public ActionResult userLoggedOut() {
		SessionUser.getInstance().removeUser();
		DatabaseManager.clearDataSource();
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}
}
