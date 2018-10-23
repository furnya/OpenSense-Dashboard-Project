package com.opensense.dashboard.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;

public class Dashboard implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final HandlerManager eventBus = new HandlerManager(null);
		new AppController(eventBus);	
	}
}