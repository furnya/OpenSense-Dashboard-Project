package com.opensense.dashboard.client.view;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.presenter.ListManagerPresenter;

public interface ListsView extends IDataPanelPageView{
	public interface Presenter{
		AppController getAppController();
		HandlerManager getEventBus();
	}

	public void setPresenter(Presenter presenter);
	@Override
	public Widget asWidget();
	public void initView();
	public ListManagerPresenter getListManager();
}
