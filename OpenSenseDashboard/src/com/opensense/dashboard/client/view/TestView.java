package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.presenter.TestPresenter;

public interface TestView {
	public interface Presenter {

		String getData();
	}
	
	public void setPresenter(TestPresenter testPresenter);
	public Widget asWidget();
}
