package com.opensense.dashboard.client.view;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.model.DataPanelPage;

public interface NavigationPanelView {
	public interface Presenter{
		HandlerManager getEventBus();
		boolean isGuest();
		void onLogoutButtonClicked();
	}

	void setPresenter(Presenter presenter);
	Widget asWidget();
	void setActiveDataPanelPage(DataPanelPage page);
}
