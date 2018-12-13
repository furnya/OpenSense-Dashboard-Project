package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.shared.Sensor;

public interface MapView extends IDataPanelPageView{
	public interface Presenter{
		HandlerManager getEventBus();
		AppController getAppController();
		JavaScriptObject getMarkerSpiderfier();
		void buildSensorRequestFromIdsAndShowMarkers(List<Integer> selectedIds);
	}


	public void setPresenter(Presenter presenter);
	@Override
	public Widget asWidget();
	public void initView(Runnable runnable);
	public void showMarkers(List<Sensor> sensorlist);
	public void resetMarkerAndCluster();
	public MapWidget getMapWidget();
//	public Map<Integer, Marker> getMarkers();
	public ListManager getListManager();
	public void addPlusCluster(Marker marker);
	public void checkForSpiderfierMarkers();
}
