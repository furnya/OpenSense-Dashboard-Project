package com.opensense.dashboard.client.presenter;

import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.IDataPanelPageView;

public abstract class DataPanelPagePresenter implements IDataPanelPagePresenter{

	private final IDataPanelPageView view;

	protected final HandlerManager eventBus;
	protected final AppController appController;
	private Map<String, String> parameters;

	public DataPanelPagePresenter(IDataPanelPageView view, HandlerManager eventBus, AppController appController) {
		this.view = view;
		this.eventBus = eventBus;
		this.appController = appController;
	}
	
	@Override
	public final AppController getAppController() {
		return appController;
	}
	
	public HandlerManager getEventBus() {
		return eventBus;
	}
}

