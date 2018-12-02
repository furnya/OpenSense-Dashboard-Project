package com.opensense.dashboard.server.logic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.SessionUser;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

@SuppressWarnings("serial")
public class AuthenticationServlet extends RemoteServiceServlet implements AuthenticationService{
	
	private static final byte[] GWT_DES_KEY = new byte[]{
			(byte)5,(byte)6,(byte)1,(byte)1,(byte)0,(byte)1,(byte)8,(byte)7,
			(byte)3,(byte)9,(byte)2,(byte)2,(byte)3,(byte)6,(byte)1,(byte)0,
			(byte)9,(byte)2,(byte)1,(byte)1,(byte)5,(byte)7,(byte)1,(byte)1,};
	/**
	 * Returns boolean true if a user logged in, false the user is guest
	 */
	@Override
	public Boolean createUserInSession() {
		if(System.getenv("dev_mode") != null && "true".equalsIgnoreCase(System.getenv("dev_mode"))) {
			ActionResult result = userLoginRequest(System.getenv("username"), System.getenv("password"));
			if(ActionResultType.SUCCESSFUL.equals(result.getActionResultType())) {
				return true;
			}
		}
		SessionUser.getInstance().setGuest(true);
		return false;
		//TODO: how to save a user that logged in last time cookie ?
	}
	
	@Override
	public ActionResult userLoginRequest(String username, String password) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(GWT_DES_KEY);
		String decryptedPassword = "";
		try {
			decryptedPassword = cipher.decrypt(password);
		} catch (Exception e) {
			//TODO: exception handling
			e.printStackTrace();
		}
		System.out.println(decryptedPassword);
		String body = "{\"username\":\""+username+"\",\"password\":\""+decryptedPassword+"\"}";
		String token = ClientRequestHandler.getInstance().sendLoginRequest(body);
		SessionUser.getInstance().setToken(token);
		SessionUser.getInstance().setUsername(username);
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
