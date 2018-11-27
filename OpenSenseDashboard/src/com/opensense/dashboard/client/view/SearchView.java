package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.ValuePreview;

public interface SearchView extends IDataPanelPageView{
	public interface Presenter{

		void buildSensorRequestAndSend();
		HandlerManager getEventBus();
		void getSensorValuePreviewAndShow(List<Integer> idsOnPage);
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void showSensorData(List<Sensor> sensors);
	String getMinAccuracy();
	String getMaxAccuracy();
	void setMeasurandsList(Map<Integer, String> measurands);
	String getMeasurandId();
	String getMaxSensors();
	public void showLoadSensorError();
	void setMaxSensors(String maxSensors);
	void setMaxAccuracy(String maxAccuracy);
	void setMinAccuracy(String minAccuracy);
	public boolean isSearchButtonEnabled();
	public void selectMeasurandId(String id);
	public void showLoadingIndicator();
	public void setPlaceString(String value);
	public void pagination();
	public LatLngBounds getBounds();
	public List<Integer> getShownSensorIds();
	public double getMaxSensorsOnPage();
	public int getSensorPage();
	public void setSensorPage(int i);
	public void clearSensorData();
	public void showDataContainer(boolean show);
	public void showSensorValuePreview(Map<Integer, ValuePreview> result);
}
