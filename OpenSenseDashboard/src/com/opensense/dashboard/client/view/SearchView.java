package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.server.logic.Sensor;

public interface SearchView extends IDataPanelPageView{
	public interface Presenter{
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void showSensorData(List<Sensor> result);
}
