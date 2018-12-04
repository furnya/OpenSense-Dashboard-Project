package com.opensense.dashboard.server.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
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
		if(System.getenv("DEV_MODE") != null && "true".equalsIgnoreCase(System.getenv("DEV_MODE"))) {
			ActionResult result = userLoginRequest(System.getenv("USERNAME"), System.getenv("PASSWORD"));
			if(ActionResultType.SUCCESSFUL.equals(result.getActionResultType())) {
				return true;
			}
		}
		SessionUser.getInstance().setGuest(true);
		return false;
	}
	
	@Override
	public ActionResult userLoginRequest(String username, String password) {
		String body = "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
		String token;
		try {
			token = ClientRequestHandler.getInstance().sendLoginRequest(body);
			SessionUser.getInstance().setToken(token);
			SessionUser.getInstance().setUsername(username);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failure", e);
			return new ActionResult(ActionResultType.FAILED);
		}
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}

	@Override
	public ActionResult userLoggedOut() {
		SessionUser.getInstance().setToken(null);
		SessionUser.getInstance().setUsername(null);
		SessionUser.getInstance().setGuest(true);
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}
}
