package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.ListsView;

public class ListsPresenter implements IPresenter, ListsView.Presenter{
	
	private final ListsView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public ListsPresenter(HandlerManager eventBus, AppController appController, ListsView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}
	
	public ListsView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
	
}
