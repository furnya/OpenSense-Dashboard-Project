package com.opensense.dashboard.client.presenter;

import java.util.Date;
import java.util.LinkedList;
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
import com.opensense.dashboard.client.view.VisualisationsView;
import com.opensense.dashboard.client.view.VisualisationsViewImpl;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;
import com.opensense.dashboard.shared.Sensor;

public class VisualisationsPresenter extends DataPanelPagePresenter implements IPresenter, VisualisationsView.Presenter{

	private final VisualisationsView view;
	
	private static final Logger LOGGER = Logger.getLogger(SearchPresenter.class.getName());
	
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
		view.initView();
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
		view.setSensors(new LinkedList<>());
		if(ids!=null && !ids.isEmpty()) {
			for(Integer id : ids) {
				buildValueRequestAndSend(id, view.getDefaultRange(), null, null);
			}
			eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, false, ids));
		}
	}


	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		runnable.run();
	}
	
	public void valueRequestForSensorList(List<Integer> sensorIds, DateRange dateRange, Date minDate, Date maxDate) {
		sensorIds.forEach(sensorId -> buildValueRequestAndSend(sensorId, dateRange, minDate, maxDate));
	}
	
	public void buildValueRequestAndSend(Integer id, DateRange dateRange, Date minDate, Date maxDate) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.VALUE, true);
		requestBuilder.setIds(new LinkedList<Integer>());
		requestBuilder.addId(id);
		requestBuilder.setDateRange(dateRange);
		if(minDate != null && dateRange==DateRange.CUSTOM) requestBuilder.addParameter(ParamType.MIN_TIMESTAMP, minDate.toGMTString().replace(" ", "%20"));
		if(maxDate != null && dateRange==DateRange.CUSTOM) requestBuilder.addParameter(ParamType.MAX_TIMESTAMP, maxDate.toGMTString().replace(" ", "%20"));
		requestBuilder.getRequest().getParameters().forEach(param -> GWT.log("RequestParam: " + param.getKey() + " " + param.getValue()));
		sendRequest(requestBuilder.getRequest());
	}
	
	private void sendRequest(final Request request) {
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && request.getRequestType().equals(result.getResultType()) && result.getValues() != null) {
				view.addSensorValues(result.getSensors().get(0), result.getValues());
				view.showChart();
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the values.");
		}, false));
	}
	
}
