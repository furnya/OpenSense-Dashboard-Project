package com.opensense.dashboard.client.presenter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.MapView;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class MapPresenter extends DataPanelPagePresenter implements IPresenter, MapView.Presenter{
	
	private static final Logger LOGGER = Logger.getLogger(MapPresenter.class.getName());
	
	private final MapView view;
	private static final String MAX_SENSOR_REQUEST = "20";
	
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
		buildSensorRequestAndShowMarkers(parameters);
	}

	@Override
	public void initView() {
		view.initView();
	}
	
	//get Sensor Data from Server
	public void buildSensorRequestAndShowMarkers(Map<ParamType, String> parameters) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR);
		if(parameters != null) {
			parameters.entrySet().forEach(entry -> {
			requestBuilder.addParameter(entry.getKey(),entry.getValue());
			});
		}
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && result.getSensors() != null) {
				view.showMarkers(result.getSensors());
			}else {
				view.showMarkers(result.getSensors());
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
		}, false));
	}
}
