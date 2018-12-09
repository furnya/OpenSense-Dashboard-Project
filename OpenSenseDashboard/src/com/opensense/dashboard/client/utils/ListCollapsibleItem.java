package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPreLoader;

public class ListCollapsibleItem extends Composite{

	@UiTemplate("ListCollapsibleItem.ui.xml")
	interface ListCollapsibleItemUiBinder extends UiBinder<Widget, ListCollapsibleItem> {
	}

	@UiField
	MaterialCollapsibleItem sensorItem;

	@UiField
	MaterialImage deleteButton;

	@UiField
	MaterialImage listIcon;

	@UiField
	MaterialLink listItemName;

	@UiField
	Pager pagerTop;

	@UiField
	Pager pagerBottom;

	@UiField
	MaterialPreLoader listItemSpinner;

	@UiField
	Div noDataIndicator;

	@UiField
	Div sensorContainer;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void setName(String name) {
		this.listItemName.setText(name);
	}

	public void addDeleteButtonClickHandler(ClickHandler handler) {
		this.deleteButton.getElement().getStyle().clearDisplay();
		this.deleteButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}

	public Div getSensorContainer() {
		return this.sensorContainer;
	}

	public Pager getTopPager() {
		return this.pagerTop;
	}

	public Pager getBottomPager() {
		return this.pagerBottom;
	}

	public Div getNoDataIndicator() {
		return this.noDataIndicator;
	}

	public void setListIcon(String url) {
		this.listIcon.setUrl(url);
		this.listIcon.getElement().getStyle().clearDisplay();
	}

	public MaterialCollapsibleItem getCollapsibleItem() {
		return this.sensorItem;
	}
}
