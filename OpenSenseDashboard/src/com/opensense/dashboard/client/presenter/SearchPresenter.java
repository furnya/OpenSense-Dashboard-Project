package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.SearchView;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class SearchPresenter extends DataPanelPagePresenter implements IPresenter, SearchView.Presenter{
	
	private static final Logger LOGGER = Logger.getLogger(SearchPresenter.class.getName());
	
	private final SearchView view;
	
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
		// TODO Auto-generated method stub
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
					view.selectMeasurandId(entry.getValue());
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
	public void handleIds(List<Integer> ids) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, false);
		ids.forEach(requestBuilder::addId);
		
		requestBuilder.getRequest().getIds().forEach(id -> GWT.log("RequestIds: " + id));
		sendRequest(requestBuilder.getRequest());
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		getMeasurandsAndDispaly(runnable);
	}
	
	public void getMeasurandsAndDispaly(final Runnable runnable) {
		GeneralService.Util.getInstance().getMeasurands(new DefaultAsyncCallback<Map<Integer, String>>(result -> {
			view.setMeasurandsList(result);
			runnable.run();
		},caucht -> {
			runnable.run();
			LOGGER.log(Level.WARNING, "Failure requesting the measurands.");
		}, false));
	}

	@Override
	public void buildSensorRequestAndSend() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, true);
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
		}
		requestBuilder.getRequest().getParameters().forEach(param -> GWT.log("RequestParam: " + param.getKey() + " " + param.getValue()));
		sendRequest(requestBuilder.getRequest());
	}
	
	private void sendRequest(final Request request) {
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && request.getRequestType().equals(result.getResultType()) && result.getSensors() != null) {
				if(request.getParameters() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, request.getParameters(), false));
				}else if(request.getIds() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, false, request.getIds()));
				}
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
