package com.opensense.dashboard.client.presenter;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private static final Logger LOGGER = Logger.getLogger(SearchPresenter.class.getName());
	
	private final SearchView view;
	private static final String MAX_SENSOR_REQUEST = "20000";
	
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
	public void handleParamters(Map<ParamType, String> parameters) {
		parameters.entrySet().forEach(entry -> {
			switch(entry.getKey()) {
				case MEASURAND_ID:
					break;
				case BOUNDING_BOX:
					break;
				case MAX_ACCURACY:
					view.setMinAccuracy(entry.getValue());
					break;
				case MAX_SENSORS:
					view.setMaxSensors(entry.getValue());
					break;
				case MIN_ACCURACY:
					view.setMinAccuracy(entry.getValue());
					break;
				default:
					break;
			}
		});
		if(view.isSearchButtonEnabled()) {
			buildSensorRequestAndSend();
		}
	}

	@Override
	public void initView() {
		getMeasurandsAndDispaly();
	}
	
	public void getMeasurandsAndDispaly() {
		GeneralService.Util.getInstance().getMeasurands(new DefaultAsyncCallback<Map<Integer, String>>(view::setMeasurandsList,
				caucht -> LOGGER.log(Level.WARNING, "Failure requesting the measurands."), false));
	}

	@Override
	public void buildSensorRequestAndSend() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR_LIST);
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
		
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && result.getSensors() != null) {
				view.showSensorData(result.getSensors());
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
				view.showLoadSensorError();
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
			view.showLoadSensorError();
		}, false));
	}
}
