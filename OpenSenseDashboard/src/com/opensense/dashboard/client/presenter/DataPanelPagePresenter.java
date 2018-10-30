package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.view.IDataPanelPageView;

public abstract class DataPanelPagePresenter implements IDataPanelPagePresenter{

	private final IDataPanelPageView view;
	
	private DataPanelPagePresenter activeDataPanelPagePresenter = null;
	
	protected final HandlerManager eventBus;
	protected final AppController appController;

	public DataPanelPagePresenter(IDataPanelPageView view, HandlerManager eventBus, AppController appController) {
		this.view = view;
		this.eventBus = eventBus;
		this.appController = appController;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
	
	@Override
	public final AppController getAppController() {
		return appController;
	}
	
	public HandlerManager getEventBus() {
		return eventBus;
	}
	
	public void showPage(DataPanelPage page) {
		History.newItem(page.toString());
	}
	
	public IDataPanelPagePresenter getDataPanelContentPresenter() {
		return activeDataPanelPagePresenter;
	}
	
}

