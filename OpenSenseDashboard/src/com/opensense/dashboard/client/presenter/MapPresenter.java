package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.ListsView;
import com.opensense.dashboard.client.view.LoginView;
import com.opensense.dashboard.client.view.MapView;

public class MapPresenter implements IPresenter, MapView.Presenter{
	
	private final MapView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public MapPresenter(HandlerManager eventBus, AppController appController, MapView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}

	public MapView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
}
