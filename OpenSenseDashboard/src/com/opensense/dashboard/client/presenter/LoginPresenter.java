package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.ListsView;
import com.opensense.dashboard.client.view.LoginView;
import com.opensense.dashboard.client.view.MapView;

public class LoginPresenter implements IPresenter, LoginView.Presenter{
	
	private final LoginView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public LoginPresenter(HandlerManager eventBus, AppController appController, LoginView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}
	
	public LoginView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
}