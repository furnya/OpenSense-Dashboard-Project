package com.opensense.dashboard.client.view;

import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.AbstractChart;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Value;

public interface VisualisationsView extends IDataPanelPageView {
	
	public interface Presenter{
		void valueRequestForSensorList(List<Integer> sensorIds, DateRange dateRange, Date minDate, Date maxDate);
		void buildValueRequestAndSend(Integer id, DateRange dateRange, Date minDate, Date maxDate);
		HandlerManager getEventBus();
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	void hideLoadingIndicator();
	void showLoadingIndicator();
	public void addSensorValues(Sensor sensor, List<Value> values);
	public DateRange getDefaultRange();
	public boolean showChart();
	public void createChart();
	public void setSensors(List<Sensor> sensors);
	public List<Sensor> getSensors();
	public void showNoDataIndicator(boolean show);
	public void addEmptySensorItemCard(Integer sensorId);
}
