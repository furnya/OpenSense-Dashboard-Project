package com.opensense.dashboard.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class ListsViewImpl extends DataPanelPageView implements ListsView {
	
	@UiTemplate("ListsView.ui.xml")
	interface ListsViewUiBinder extends UiBinder<Widget, ListsViewImpl> {
	}
	
	private static ListsViewUiBinder uiBinder = GWT.create(ListsViewUiBinder.class);

	protected Presenter presenter;
	
	public ListsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
