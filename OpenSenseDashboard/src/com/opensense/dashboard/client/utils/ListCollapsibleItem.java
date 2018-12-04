package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ListCollapsibleItem extends Composite{

	@UiTemplate("ListCollapsibleItem.ui.xml")
	interface ListCollapsibleItemUiBinder extends UiBinder<Widget, ListCollapsibleItem> {
	}

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}
}
