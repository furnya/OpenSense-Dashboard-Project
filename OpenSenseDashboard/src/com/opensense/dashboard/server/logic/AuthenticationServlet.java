package com.opensense.dashboard.server.logic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

@SuppressWarnings("serial")
public class AuthenticationServlet extends RemoteServiceServlet implements AuthenticationService{
	
	@Override
	public ActionResult userLoginRequest(String userName, String password) {
		String body = "{\"username\":\""+userName+"\",\"password\":\""+password+"\"}";
		ClientRequestHandler.getInstance().sendLoginRequest(body);
		return new ActionResult(ActionResultType.FAILED, "Unimplemented");
	}
}
