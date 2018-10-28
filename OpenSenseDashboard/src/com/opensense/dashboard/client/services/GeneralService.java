package com.opensense.dashboard.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../general")
public interface GeneralService extends RemoteService {
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
	    
		private static GeneralServiceAsync instance;

		private Util() {
		    // Hides the implicit public constructor when no other constructor is present.
		}
		
		public static GeneralServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(GeneralService.class);
			}
			return instance;
		}
	}
	
	public Integer getData(String string);
}
