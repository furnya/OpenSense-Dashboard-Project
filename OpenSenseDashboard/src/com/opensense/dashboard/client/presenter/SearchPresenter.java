package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
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
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public void onPageReturn() {
		if(!this.view.getShownSensorIds().isEmpty()) {
			final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, true);
			if(this.view.getBounds() != null) {
				requestBuilder.addParameter(ParamType.BOUNDING_BOX, "[" + this.view.getBounds().toUrlValue(6) + "]");
			}
			if((this.view.getMinAccuracy() != null) && !this.view.getMinAccuracy().isEmpty()) {
				requestBuilder.addParameter(ParamType.MIN_ACCURACY, this.view.getMinAccuracy());
			}
			if((this.view.getMaxAccuracy() != null) && !this.view.getMaxAccuracy().isEmpty()) {
				requestBuilder.addParameter(ParamType.MAX_ACCURACY, this.view.getMaxAccuracy());
			}
			if((this.view.getMeasurandId() != null) && !this.view.getMeasurandId().isEmpty()) {
				requestBuilder.addParameter(ParamType.MEASURAND_ID, this.view.getMeasurandId());
			}
			if((this.view.getMaxSensors() != null) && !this.view.getMaxSensors().isEmpty()){
				requestBuilder.addParameter(ParamType.MAX_SENSORS, this.view.getMaxSensors());
			}
			this.eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, requestBuilder.getRequest().getParameters(), false));
		}
	}

	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		this.view.showLoadingIndicator();
		this.view.clearSensorData();
		this.view.showDataContainer(true);
		parameters.entrySet().forEach(entry -> {
			switch(entry.getKey()) {
			case MEASURAND_ID:
				this.view.selectMeasurandId(entry.getValue());
				break;
			case BOUNDING_BOX:
				break;
			case MAX_ACCURACY:
				this.view.setMaxAccuracy(entry.getValue());
				break;
			case MAX_SENSORS:
				this.view.setMaxSensors(entry.getValue());
				break;
			case MIN_ACCURACY:
				this.view.setMinAccuracy(entry.getValue());
				break;
			case PLACE:
				//					view.setPlaceString(entry.getValue());
				break;
			default:
				break;
			}
		});
		if(this.view.isSearchButtonEnabled()) {
			this.buildSensorRequestAndSend();
		}
	}

	@Override
	public void handleIds(List<Integer> ids) {
		this.view.showLoadingIndicator();
		this.view.clearSensorData();
		this.view.showDataContainer(true);
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, false);
		ids.forEach(requestBuilder::addId);
		this.sendSensorRequestAndShow(requestBuilder.getRequest());
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		this.getMeasurandsAndDispaly(runnable);
	}

	private void getMeasurandsAndDispaly(final Runnable runnable) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.MEASURAND, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getMeasurands() != null)) {
				this.view.setMeasurandsList(result.getMeasurands());
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
		if(this.view.getBounds() != null) {
			requestBuilder.addParameter(ParamType.BOUNDING_BOX, "[" + this.view.getBounds().toUrlValue(6) + "]");
		}
		if((this.view.getMinAccuracy() != null) && !this.view.getMinAccuracy().isEmpty()) {
			requestBuilder.addParameter(ParamType.MIN_ACCURACY, this.view.getMinAccuracy());
		}
		if((this.view.getMaxAccuracy() != null) && !this.view.getMaxAccuracy().isEmpty()) {
			requestBuilder.addParameter(ParamType.MAX_ACCURACY, this.view.getMaxAccuracy());
		}
		if((this.view.getMeasurandId() != null) && !this.view.getMeasurandId().isEmpty()) {
			requestBuilder.addParameter(ParamType.MEASURAND_ID, this.view.getMeasurandId());
		}
		if((this.view.getMaxSensors() != null) && !this.view.getMaxSensors().isEmpty()){
			requestBuilder.addParameter(ParamType.MAX_SENSORS, this.view.getMaxSensors());
		}
		requestBuilder.setOnlySensorsWithValues(this.view.getOnlySensorsWithValueBox());
		this.sendSensorRequestAndShow(requestBuilder.getRequest());
	}

	private void sendSensorRequestAndShow(final Request request) {
		History.newItem(DataPanelPage.SEARCH.name(), false);
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && request.getRequestType().equals(result.getResultType()) && (result.getSensors() != null)) {
				if(request.getParameters() != null) {
					this.eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, request.getParameters(), false));
				}else if(request.getIds() != null) {
					this.eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, false, request.getIds()));
				}
				this.view.showSensorData(result.getSensors());
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
				//TODO: show error
				this.view.showLoadSensorError();
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
			this.view.showLoadSensorError();
		}, false));
	}

	@Override
	public void getSensorValuePreviewAndShow(List<Integer> ids) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.VALUE_PREVIEW, false);
		ids.forEach(requestBuilder::addId);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getValuePreviews() != null)) {
				this.view.showSensorValuePreview(result.getValuePreviews());
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
