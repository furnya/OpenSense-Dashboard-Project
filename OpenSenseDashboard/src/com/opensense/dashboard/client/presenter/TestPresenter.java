package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.TestView;

public class TestPresenter implements IPresenter, TestView.Presenter{
	
	private final TestView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public TestPresenter(HandlerManager eventBus, AppController appController, TestView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}

	public TestView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
}
