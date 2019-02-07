package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.IDataPanelPageView;


/**
 * Parent of all dataPanelPresenter to handle needed methods and save the AppController, EventBus, View centralized
 * @author carlr
 */
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
		if(!this.view.isInitialized()) {
			this.waitUntilViewInit(runnable);
			this.view.setInitializedToTrue();
		}else {
			runnable.run();
		}
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public final AppController getAppController() {
		return this.appController;
	}

	public HandlerManager getEventBus() {
		return this.eventBus;
	}
}

