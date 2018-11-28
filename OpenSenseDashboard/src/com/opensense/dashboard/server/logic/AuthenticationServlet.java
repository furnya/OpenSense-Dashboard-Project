package com.opensense.dashboard.server.logic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

@SuppressWarnings("serial")
public class AuthenticationServlet extends RemoteServiceServlet implements AuthenticationService{
	
	private static final byte[] GWT_DES_KEY = new byte[]{
			(byte)5,(byte)6,(byte)1,(byte)1,(byte)0,(byte)1,(byte)8,(byte)7,
			(byte)3,(byte)9,(byte)2,(byte)2,(byte)3,(byte)6,(byte)1,(byte)0,
			(byte)9,(byte)2,(byte)1,(byte)1,(byte)5,(byte)7,(byte)1,(byte)1,};
	
	@Override
	public ActionResult userLoginRequest(String userName, String password) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(GWT_DES_KEY);
		String decryptedPassword = "";
		try {
			decryptedPassword = cipher.decrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(decryptedPassword);
		String body = "{\"username\":\""+userName+"\",\"password\":\""+decryptedPassword+"\"}";
		ClientRequestHandler.getInstance().sendLoginRequest(body);
		return new ActionResult(ActionResultType.FAILED, "Unimplemented");
	}
}
