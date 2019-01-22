package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface FooterView {
	public interface Presenter{
		void switchLanguage(String lang);
	}
	public void setPresenter(Presenter footerPresenter);
	public Widget asWidget();
}
