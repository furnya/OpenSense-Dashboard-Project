package com.opensense.dashboard.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NavigationPanelViewImpl extends Composite implements NavigationPanelView {

	@UiTemplate("NavigationPanelView.ui.xml")
	interface NavigationPanelViewUiBinder extends UiBinder<Widget, NavigationPanelViewImpl> {
	}
	
	private static NavigationPanelViewUiBinder uiBinder = GWT.create(NavigationPanelViewUiBinder.class);

	protected Presenter presenter;
	
	public NavigationPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
