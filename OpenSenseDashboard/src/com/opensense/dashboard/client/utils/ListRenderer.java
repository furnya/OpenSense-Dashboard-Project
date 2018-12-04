package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;

public class ListRenderer extends Composite{

	@UiTemplate("ListRenderer.ui.xml")
	interface ListRendererUiBinder extends UiBinder<Widget, ListRenderer> {
	}

	private static ListRendererUiBinder uiBinder = GWT.create(ListRendererUiBinder.class);

	private ListController controller;

	@UiField
	MaterialCollapsible collapsible;

	public ListRenderer(ListController listController) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.controller = listController;
	}

	public void addNewListItem() {
		ListCollapsibleItem item = new ListCollapsibleItem();
		this.collapsible.add(item);
	}

}
