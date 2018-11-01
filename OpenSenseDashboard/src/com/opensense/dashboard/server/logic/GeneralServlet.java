package com.opensense.dashboard.server.logic;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.opensense.dashboard.client.services.GeneralService;

@SuppressWarnings("serial")
public class GeneralServlet extends RemoteServiceServlet implements GeneralService{

	@Override
	public List<Sensor> getSensorDataFromString(String searchQuery) {
		return new ArrayList<Sensor>();
	}

}
