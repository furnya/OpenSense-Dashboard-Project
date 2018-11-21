package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.opensense.dashboard.shared.ValuePreview;

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
		// TODO write parameter back to history
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		view.showLoadingIndicator();
		view.clearSensorData();
		view.showDataContainer(true);
		parameters.entrySet().forEach(entry -> {
			switch(entry.getKey()) {
				case MEASURAND_ID:
					view.selectMeasurandId(entry.getValue());
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
				case PLACE:
//					view.setPlaceString(entry.getValue());
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
		view.showLoadingIndicator();
		view.clearSensorData();
		view.showDataContainer(true);
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, false);
		ids.forEach(requestBuilder::addId);
		
		sendSensorRequestAndShow(requestBuilder.getRequest());
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		getMeasurandsAndDispaly(runnable);
	}
	
	private void getMeasurandsAndDispaly(final Runnable runnable) {
		GeneralService.Util.getInstance().getMeasurands(new DefaultAsyncCallback<Map<Integer, String>>(result -> {
			if(result != null) {
				view.setMeasurandsList(result);
			}else {
				//TODO: show Error
			}
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
		sendSensorRequestAndShow(requestBuilder.getRequest());
	}
	
	private void sendSensorRequestAndShow(final Request request) {
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
				//TODO: show error
				view.showLoadSensorError();
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
			view.showLoadSensorError();
		}, false));
	}
		
	public void getSensorValuePreviewAndShow(List<Integer> ids) {
			GeneralService.Util.getInstance().getSensorValuePreview(ids, new DefaultAsyncCallback<Map<Integer, ValuePreview>>(result -> {
				if(result != null) {
					view.showSensorValuePreview(result);
				}else {
					LOGGER.log(Level.WARNING, "SensorValuePreview result is null.");
					//TODO: show error
				}
			},caught -> {
				LOGGER.log(Level.WARNING, "Failure requesting the sensorValuePreview.");
				//TODO:showError
			}, false));
		}
}
