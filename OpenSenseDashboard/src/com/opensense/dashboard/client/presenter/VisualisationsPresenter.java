package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.view.VisualisationsView;

public class VisualisationsPresenter extends DataPanelPagePresenter implements IPresenter, VisualisationsView.Presenter{

	private final VisualisationsView view;
	
	public VisualisationsPresenter(HandlerManager eventBus, AppController appController, VisualisationsView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public VisualisationsView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void onPageReturn() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onPageLeave() {
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
