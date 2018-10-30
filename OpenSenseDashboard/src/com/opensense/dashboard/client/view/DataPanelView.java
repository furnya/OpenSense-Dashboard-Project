package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public interface DataPanelView {
	public interface Presenter {
	}

	public void setPresenter(Presenter dataPanelPresenter);
	public Widget asWidget();
	HasWidgets getContentContainer();
	void setHeading(String heading);
}
