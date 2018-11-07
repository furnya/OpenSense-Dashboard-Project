package com.opensense.dashboard.client.presenter;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.SearchView;
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
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR);
		if(view.getBounds() != null) {
			requestBuilder.addParameter(ParamType.BOUNDING_BOX, "[" + view.getBounds().toUrlValue(6) + "]");
		}
		if(view.getMinAccuracy() != null && !view.getMinAccuracy().isEmpty()) {
			requestBuilder.addParameter(ParamType.MIN_ACCURACY, view.getMinAccuracy());
		}
		if(view.getMaxAccuracy() != null && !view.getMaxAccuracy().isEmpty()) {
			requestBuilder.addParameter(ParamType.MAX_ACCURACY, view.getMaxAccuracy());
		}
		if(view.getMeasurandId() != null && !view.getMeasurandId().isEmpty()) {
			requestBuilder.addParameter(ParamType.MEASURAND_ID, view.getMeasurandId());
		}
		if(view.getMaxSensors() != null && !view.getMaxSensors().isEmpty()){
			requestBuilder.addParameter(ParamType.MAX_SENSORS, view.getMaxSensors());
		}else {
			requestBuilder.addParameter(ParamType.MAX_SENSORS, String.valueOf(MAX_SENSOR_REQUEST));
			view.setMaxSensors(MAX_SENSOR_REQUEST);
		}
		
		requestBuilder.getRequest().getParameters().forEach(param -> GWT.log(param.getKey() + " " + param.getValue()));
		
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && result.getSensors() != null) {
				view.showSensorData(result.getSensors());
			}else {
				//TODO:
			}
		},caught -> view.showLoadSensorError(), false));
	}
}
