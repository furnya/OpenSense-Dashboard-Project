package com.opensense.dashboard.client.view;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.shared.Value;

public interface VisualisationsView extends IDataPanelPageView {
	
	public interface Presenter{
		void buildValueRequestAndSend();
		HandlerManager getEventBus();
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	void hideLoadingIndicator();
	void showLoadingIndicator();
	public void showValuesInChart(List<Value> values);
}
