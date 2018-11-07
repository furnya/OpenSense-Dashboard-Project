package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.user.client.ui.Widget;

public interface SearchView extends IDataPanelPageView{
	public interface Presenter{

		void buildSensorRequestAndSend();
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void showSensorData(List<String> s);
	LatLngBounds getBounds();
	String getMinAccuracy();
	String getMaxAccuracy();
	void setUnitList(Map<Integer, String> units);
	List<String> getUnits();
	String getMaxSensors();
	void setMaxSensors(Integer maxSensors);
}
