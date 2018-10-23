package com.opensense.dashboard.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;

public interface GeneralStartService extends RemoteService {
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
	    
		private static GeneralStartService instance;

		private Util() {
		    // Hides the implicit public constructor when no other constructor is present.
		}
		
		public static GeneralStartService getInstance() {
			if (instance == null) {
				instance = GWT.create(GeneralStartService.class);
			}
			return instance;
		}
	}
}
