package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface UserView extends IDataPanelPageView{
	public interface Presenter{
		void sendLoginRequest(String username, String password);
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
}
