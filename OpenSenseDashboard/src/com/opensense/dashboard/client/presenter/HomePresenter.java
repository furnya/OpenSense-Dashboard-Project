package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
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
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleIds(List<Integer> ids) {
		// TODO Auto-generated method stub
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		view.initView();
		runnable.run();
	}

}
