package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.NavigationPanelView;

public class NavigationPanelPresenter implements IPresenter, NavigationPanelView.Presenter{
	
	private final NavigationPanelView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public NavigationPanelPresenter(HandlerManager eventBus, AppController appController, NavigationPanelView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}

	public NavigationPanelView getView() {
		return view;
	}
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

}
