package com.opensense.dashboard.client.view;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.shared.CreateSensorRequest;

public interface ListsView extends IDataPanelPageView{
	public interface Presenter{
		AppController getAppController();
		HandlerManager getEventBus();
		void createSensorRequest(CreateSensorRequest request);
		void requestDataAndShowCreateSensorModal();
	}

	public void setPresenter(Presenter presenter);
	@Override
	public Widget asWidget();
	public void initView(Runnable runnable, boolean b);
	public ListManager getListManager();
	public void setCreateListButtonEnabled(boolean b);
	void setCreateSensorButtonEnabled(boolean enabled);
}
