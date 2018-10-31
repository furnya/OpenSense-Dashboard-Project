package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.SearchView;

public class SearchPresenter  implements IPresenter, SearchView.Presenter{
	
	private final SearchView view;
	private HandlerManager eventBus;
	private AppController appController;
	
	public SearchPresenter(HandlerManager eventBus, AppController appController, SearchView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		view.setPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
}
