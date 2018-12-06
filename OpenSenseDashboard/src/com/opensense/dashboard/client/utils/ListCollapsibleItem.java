package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;

public class ListCollapsibleItem extends Composite{

	@UiTemplate("ListCollapsibleItem.ui.xml")
	interface ListCollapsibleItemUiBinder extends UiBinder<Widget, ListCollapsibleItem> {
	}

	@UiField
	MaterialImage deleteButton;

	@UiField
	MaterialLink listItemId;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void setName(String name) {
		this.listItemId.setText(name);
	}

	public void addDeleteButtonClickHandler(ClickHandler handler) {
		this.deleteButton.getElement().getStyle().clearDisplay();
		this.deleteButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}
}
