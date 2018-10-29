package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface TestView {
	public interface Presenter {

		String getData();
	}
	
	public void setPresenter(Presenter presenter);
	public Widget asWidget();
}
