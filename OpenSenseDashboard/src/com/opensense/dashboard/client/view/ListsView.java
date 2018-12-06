package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;

public interface ListsView extends IDataPanelPageView{
	public interface Presenter{
		AppController getAppController();
	}

	public void setPresenter(Presenter presenter);
	@Override
	public Widget asWidget();
	public void initView();
	public ListManagerPresenter getListManager();
}
