package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface LoginView {
	public interface Presenter{
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
}
