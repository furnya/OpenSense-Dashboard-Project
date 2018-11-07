package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.SearchView;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class SearchPresenter extends DataPanelPagePresenter implements IPresenter, SearchView.Presenter{
	
	private final SearchView view;
	private static final int MAX_SENSOR_REQUEST = 20000;
	
	public SearchPresenter(HandlerManager eventBus, AppController appController, SearchView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void onPageReturn() {
		getMeasurandsAndDispaly();
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleParamters(Map<String, String> parameters) {
		// TODO:
	}

	@Override
	public void initView() {
		getMeasurandsAndDispaly();
	}
	
	public void getMeasurandsAndDispaly() {
		GeneralService.Util.getInstance().getMeasurands(new DefaultAsyncCallback<Map<Integer, String>>(view::setMeasurandsList,
				caucht -> GWT.log("Fehler"), false));
	}

	@Override
	public void buildSensorRequestAndSend() {
		final Request request = new Request(ResultType.SENSOR);
		List<Parameter> parameters = new ArrayList<>();
		if(view.getBounds() != null) {
			parameters.add(new Parameter("bounds", view.getBounds().toUrlValue(6)));
		}
		if(view.getMinAccuracy() != null && !view.getMinAccuracy().isEmpty()) {
			parameters.add(new Parameter("minAccuracy", view.getMinAccuracy()));
		}
		if(view.getMaxAccuracy() != null && !view.getMaxAccuracy().isEmpty()) {
			parameters.add(new Parameter("maxAccuracy", view.getMaxAccuracy()));
		}
		if(view.getMeasurandId() != null && !view.getMeasurandId().isEmpty()) {
			parameters.add(new Parameter("units", view.getMeasurandId()));
		}
		if(view.getMaxSensors() != null && !view.getMaxSensors().isEmpty()){
			parameters.add(new Parameter("maxSensor", view.getMaxSensors()));
		}else {
			parameters.add(new Parameter("maxSensor", MAX_SENSOR_REQUEST+""));
			view.setMaxSensors(MAX_SENSOR_REQUEST);
		}
		request.setParameters(parameters);
		
		parameters.forEach(param -> GWT.log(param.getKey() + " " + param.getValue()));
		
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && result.getResultType().equals(request.getRequestType()) && result.getSensors() != null) {
				view.showSensorData(result.getSensors());
			}else {
				//TODO:
			}
		},caught -> view.showLoadSensorError(), false));
	}
}
