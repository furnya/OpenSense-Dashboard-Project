package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.Image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.view.ListManagerViewImpl;

import gwt.material.design.client.ui.MaterialLink;

public class ListCollapsibleItem extends Composite{

	@UiTemplate("ListCollapsibleItem.ui.xml")
	interface ListCollapsibleItemUiBinder extends UiBinder<Widget, ListCollapsibleItem> {
	}

	private ListManagerViewImpl view;

	private Integer listId;

	@UiField
	Image deleteButton;

	@UiField
	MaterialLink listItemId;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	public ListCollapsibleItem(ListManagerViewImpl view, int id) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.view = view;
		this.listId = id;
		this.listItemId.setText("Neue Liste " + id);
	}

	@UiHandler("deleteButton")
	public void onDeleteButtonClicked(ClickEvent e) {
		e.stopPropagation();
		this.view.deleteList(this.listId);
	}
}
