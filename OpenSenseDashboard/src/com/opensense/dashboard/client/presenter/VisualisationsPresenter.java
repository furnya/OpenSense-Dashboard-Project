package com.opensense.dashboard.client.presenter;

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
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

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
		// TODO Auto-generated method stub
	}


	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		view.initView();
		runnable.run();
	}
	
	@Override
	public void buildValueRequestAndSend() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.VALUE, true);
		requestBuilder.setIds(new LinkedList<Integer>());
		requestBuilder.addId(1);
		requestBuilder.getRequest().getParameters().forEach(param -> GWT.log("RequestParam: " + param.getKey() + " " + param.getValue()));
		sendRequest(requestBuilder.getRequest());
	}
	
	private void sendRequest(final Request request) {
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && request.getRequestType().equals(result.getResultType()) && result.getValues() != null) {
				if(request.getParameters() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, request.getParameters(), false));
				}else if(request.getIds() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, false, request.getIds()));
				}
				view.showValuesInChart(result.getValues().subList(0, 100));
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the values.");
		}, false));
	}
	
}
