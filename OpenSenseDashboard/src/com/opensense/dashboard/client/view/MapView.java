package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.shared.Sensor;

public interface MapView extends IDataPanelPageView{
	public interface Presenter{
		HandlerManager getEventBus();
		JavaScriptObject getMarkerSpiderfier();
	}
	
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void showSensorData(final List<Sensor> sensors);
	public void showMarkers(List<Sensor> sensorlist);
	public void resize(double lg,double bg);
	public void resetMarkerAndCluster();
	public void calcBounds(List<Sensor> sensorlist);
	public MapWidget getMapWidget();
	public Map<Integer, Marker> getMarkers();
}
