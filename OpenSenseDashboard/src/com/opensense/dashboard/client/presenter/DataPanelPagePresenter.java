package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.IDataPanelPageView;

public abstract class DataPanelPagePresenter implements IDataPanelPagePresenter{

	private final IDataPanelPageView view;
	
	protected final HandlerManager eventBus;
	protected final AppController appController;

	public DataPanelPagePresenter(IDataPanelPageView view, HandlerManager eventBus, AppController appController) {
		this.view = view;
		this.eventBus = eventBus;
		this.appController = appController;
	}
	
	@Override
	public final void initIfNeeded(final Runnable runnable) {
		if(!view.isInitialized()) {
			waitUntilViewInit(runnable);
			view.setInitializedToTrue();
		}else {
			runnable.run();
		}
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
}

