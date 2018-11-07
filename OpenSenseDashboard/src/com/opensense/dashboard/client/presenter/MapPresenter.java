package com.opensense.dashboard.client.presenter;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.MapView;

public class MapPresenter extends DataPanelPagePresenter implements IPresenter, MapView.Presenter{
	
	private final MapView view;
	
	public MapPresenter(HandlerManager eventBus, AppController appController, MapView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	public MapView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		getAndShowSensorData();
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
	public void initView() {
		view.initView();
	}
	
	//get Sensor Data from Server
	public void getAndShowSensorData() {
		GeneralService.Util.getInstance().getMapSensorData(new DefaultAsyncCallback<List<String>>(view::showMarkers));
	}
}
