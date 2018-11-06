package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface SearchView extends IDataPanelPageView{
	public interface Presenter{

		void buildRequestAndSend();
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void showSensorData(List<String> s);
}
