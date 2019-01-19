package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface UserView extends IDataPanelPageView{
	public interface Presenter{
		void sendLoginRequest(String username, String password);
		boolean isUserLoggedIn();
		void sendRegisterRequest(String username, String password, String email);
		void sendForgotPasswordRequest(String email);
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
	public void initView();
	public void resetViewElements();
	public void showLoginNotValid();
}
