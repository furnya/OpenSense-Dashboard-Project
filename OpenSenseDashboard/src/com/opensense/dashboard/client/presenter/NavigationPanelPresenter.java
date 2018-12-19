package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.view.NavigationPanelView;

public class NavigationPanelPresenter implements IPresenter, NavigationPanelView.Presenter{

	private final NavigationPanelView view;
	private final AppController appController;
	private final HandlerManager eventBus;

	public NavigationPanelPresenter(HandlerManager eventBus, AppController appController, NavigationPanelView view) {
		this.appController = appController;
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	public NavigationPanelView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public HandlerManager getEventBus() {
		return this.eventBus;
	}

	public AppController getAppController() {
		return this.appController;
	}

	public void setActiveDataPanelPage(DataPanelPage page) {
		this.view.setActiveDataPanelPage(page);
	}

	@Override
	public boolean isGuest() {
		return this.appController.isGuest();
	}

	@Override
	public void onLogoutButtonClicked() {
		this.appController.logout();
	}

	public void onUserLoggedIn() {
		this.view.setLastButtonActive(true);
	}

	public void onUserLoggedOut() {
		this.view.setLastButtonActive(false);
	}
}
