package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface NavigationPanelView {
	public interface Presenter{
	}

	void setPresenter(Presenter presenter);
	Widget asWidget();
}
