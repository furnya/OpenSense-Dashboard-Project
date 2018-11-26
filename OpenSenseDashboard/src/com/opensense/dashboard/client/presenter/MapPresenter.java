package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.MapView;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class MapPresenter extends DataPanelPagePresenter implements IPresenter, MapView.Presenter{
	
	private static final Logger LOGGER = Logger.getLogger(MapPresenter.class.getName());
	
	private final MapView view;
	
	private JavaScriptObject markerSpiderfier;
	
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
		initMarkerSpiderfier();
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
		buildSensorRequestAndShowMarkers(parameters);
	}
	
	@Override
	public void handleIds(List<Integer> ids) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, false);
		ids.forEach(requestBuilder::addId);
		sendRequest(requestBuilder.getRequest());
	}


	@Override
	public void waitUntilViewInit(Runnable runnable) {
		view.initView();
		runnable.run();
	}
	
	//get Sensor Data from Server
	public void buildSensorRequestAndShowMarkers(Map<ParamType, String> parameters) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, true);
		parameters.entrySet().forEach(entry -> requestBuilder.addParameter(entry.getKey(),entry.getValue()));
		sendRequest(requestBuilder.getRequest());
	}
	
	private void sendRequest(final Request request) {
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && request.getRequestType().equals(result.getResultType()) && result.getSensors() != null) {
				if(request.getParameters() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, request.getParameters(), false));
				}else if(request.getIds() != null) {
					eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, false, request.getIds()));
				}
				view.showSensorData(result.getSensors());
				view.showMarkers(result.getSensors());
				view.calcBounds(result.getSensors());
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
				//TODO: show error
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
			//TODO: show error
		}, false));
	}

	private void initMarkerSpiderfier() {
		markerSpiderfier = initMarkerSpiderfierJSNI(view.getMapWidget().getJso());
	}
	
	private native JavaScriptObject initMarkerSpiderfierJSNI(JavaScriptObject mapWidget) /*-{
		var oms = new $wnd.OverlappingMarkerSpiderfier(mapWidget, {
			nearbyDistance : 10,
			markersWontMove : true,
			keepSpiderfied : true,
			circleSpiralSwitchover : 20,
			basicFormatEvents : true
		});
		var that = this;
		
		//destroy MarkerPopup whenever the spiderfier does some action:
		oms.addListener('spiderfy',	function(marker) {
			that.@com.opensense.dashboard.client.presenter.MapPresenter::destroyMarkerPopup();
		});
		
		oms.addListener('unspiderfy', function(marker) {
			that.@com.opensense.dashboard.client.presenter.MapPresenter::destroyMarkerPopup();
		});
	
		return oms;
	}-*/;
	
	
	@Override
	public JavaScriptObject getMarkerSpiderfier() {
		return markerSpiderfier;
	}

	/**
	 * destroys the marker popup
	 */
	private void destroyMarkerPopup() {
		GWT.log("Destroyed");
	}
}
