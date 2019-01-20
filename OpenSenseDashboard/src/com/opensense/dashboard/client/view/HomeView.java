package com.opensense.dashboard.client.view;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;

public interface HomeView extends IDataPanelPageView {
	public interface Presenter {

		boolean isUserLoggedIn();
		HandlerManager getEventBus();
	}

	public void setPresenter(Presenter presenter);

	public Widget asWidget();

	public void initView();

	public void setUserInfo(String result);
}
