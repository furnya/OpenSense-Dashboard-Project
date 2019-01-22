package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface UserView extends IDataPanelPageView{
	public interface Presenter{
		void sendLoginRequest(String username, String password);
		boolean isUserLoggedIn();
		void sendRegisterRequest(String username, String password, String email);
		void sendForgotPasswordRequest(String email);
		void logout();
		void sendChangePasswordRequest(String oldPassword, String newPassword);
	}

	public void setPresenter(Presenter presenter);
	@Override
	public Widget asWidget();
	public void initView();
	public void resetViewElements();
	public void showLoginNotValid();
	public void showLoginPopup(boolean b);
	public void hideChangePasswordContainer(boolean b);
	public void showSaveButtonSpinner(boolean b);
	void reEnableButtons();
}
