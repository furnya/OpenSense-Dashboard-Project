package com.opensense.dashboard.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class HomeViewImpl extends DataPanelPageView implements HomeView {
	
	@UiTemplate("HomeView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
	
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	protected Presenter presenter;
	
	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void printTest() {
		GWT.log("Hier");
	}
}
