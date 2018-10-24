package com.opensense.dashboard.server.logic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralStartService;

@SuppressWarnings("serial")
public class GeneralStartServlet extends RemoteServiceServlet implements GeneralStartService{

	@Override
	public Integer getData(String string) {
		return 1;
	}

}
