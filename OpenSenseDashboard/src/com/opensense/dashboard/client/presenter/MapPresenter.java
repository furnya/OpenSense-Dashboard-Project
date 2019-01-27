package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.MapView;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class MapPresenter extends DataPanelPagePresenter implements IPresenter, MapView.Presenter {

	private static final Logger LOGGER = Logger.getLogger(MapPresenter.class.getName());

	private final MapView view;

	private JavaScriptObject markerSpiderfier;

	private boolean spiderStatus = false;

	public MapPresenter(HandlerManager eventBus, AppController appController, MapView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	public MapView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
		this.initMarkerSpiderfier();
	}

	@Override
	public void onPageReturn() {
		this.view.resetMarkerAndCluster();
		this.view.getListManager().setUserLoggedInAndUpdate(!this.appController.isGuest());
	}

	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		GWT.log("sorry you can't do that");
	}

	@Override
	public void handleIds(List<Integer> ids) {
		this.view.getListManager().updateSelectedSensorsList(ids);
	}

	@Override
	public void waitUntilViewInit(Runnable runnable) {
		this.view.initView(runnable);
		runnable.run();
	}

	public void updateFavoriteList() {
		this.view.getListManager().updateFavoriteList();
	}

	public void onUserLoggedIn() {
		this.view.getListManager().onUserLoggedIn();
	}

	public void onUserLoggedOut() {
		this.view.getListManager().onUserLoggedOut();
	}

	// get Sensor Data from Server
	@Override
	public void buildSensorRequestFromIdsAndShowMarkers(List<Integer> markerIds) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.SENSOR, false);
		markerIds.forEach(requestBuilder::addId);
		this.sendRequest(requestBuilder.getRequest());
	}

	private void sendRequest(final Request request) {
		
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if ((result != null) && (result.getResultType() != null)
					&& request.getRequestType().equals(result.getResultType()) && (result.getSensors() != null)) {
				if (request.getParameters() != null) {
					this.eventBus
					.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, request.getParameters(), false));
				} else if (request.getIds() != null) {
					this.eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP,false));
				}
				this.view.showMarkers(result.getSensors());
			} else {
				AppController.showError(Languages.connectionError());
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
			}
		}, caught -> {
			AppController.showError(Languages.connectionError());
			LOGGER.log(Level.WARNING, "Failure requesting the sensors.");
		}, false));
	}

	private void initMarkerSpiderfier() {
		this.markerSpiderfier = this.initMarkerSpiderfierJSNI(this.view.getMapWidget().getJso());
	}

	private native JavaScriptObject initMarkerSpiderfierJSNI(JavaScriptObject mapWidget) /*-{
		var oms = new $wnd.OverlappingMarkerSpiderfier(mapWidget, {
			nearbyDistance : 1,
			markersWontMove : true,
			keepSpiderfied : true,
			spiralFootSeparation : 26,
			spiralLengthStart : 11,
			spiralLengthFactor : 4,
			legWeight : 2.5,
			circleSpiralSwitchover : 30,
			basicFormatEvents : true
		});
		var that = this;

		//destroy MarkerPopup whenever the spiderfier does some action:
		oms
				.addListener(
						'spiderfy',
						function(marker) {
							console.log("spiderfy");

						});

		oms
				.addListener(
						'unspiderfy',
						function(marker) {
							console.log("unspiderfy");
							that.@com.opensense.dashboard.client.presenter.MapPresenter::addPlusCluster(*)(marker);
						});

		return oms;
	}-*/;

	@Override
	public JavaScriptObject getMarkerSpiderfier() {
		return this.markerSpiderfier;
	}

	public void addPlusCluster(Marker marker) {
		this.view.checkForSpiderfierMarkers();
		//		this.view.addPlusCluster(marker);//TODO: this should work but the marker is
		// not correct
	}

}
