package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.presenter.DataPanelPresenter;

public interface DataPanelView {
	public interface Presenter {
	}

	public void setPresenter(DataPanelPresenter dataPanelPresenter);
	public Widget asWidget();
}
