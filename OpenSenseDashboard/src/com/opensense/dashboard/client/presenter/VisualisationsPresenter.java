package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.VisualisationsView;

public class VisualisationsPresenter implements IPresenter, VisualisationsView.Presenter{

	private final VisualisationsView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public VisualisationsPresenter(HandlerManager eventBus, AppController appController, VisualisationsView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}
	
	public VisualisationsView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

}
