package com.opensense.dashboard.server.logic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	@Override
	public Integer getData(String string) {
		System.out.println("hier");
		return 1;
	}

}
