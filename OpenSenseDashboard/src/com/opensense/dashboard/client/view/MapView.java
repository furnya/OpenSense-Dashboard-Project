package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface MapView extends IDataPanelPageView{
	public interface Presenter{
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
}
