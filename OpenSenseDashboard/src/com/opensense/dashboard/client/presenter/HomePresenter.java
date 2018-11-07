package com.opensense.dashboard.client.presenter;

import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.HomeView;

public class HomePresenter extends DataPanelPagePresenter implements HomeView.Presenter {
	
	private final HomeView view;

	public HomePresenter(HandlerManager eventBus, AppController appController, HomeView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageReturn() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleParamters(Map<String, String> parameters) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initView() {
		view.initView();
	}

}
