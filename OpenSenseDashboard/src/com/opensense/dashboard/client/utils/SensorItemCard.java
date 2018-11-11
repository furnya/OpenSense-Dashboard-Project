package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;

public class SensorItemCard extends Composite{
	
	@UiTemplate("SensorItemCard.ui.xml")
	interface SensorItemCardUiBinder extends UiBinder<Widget, SensorItemCard> {
	}
	
	private static SensorItemCardUiBinder uiBinder = GWT.create(SensorItemCardUiBinder.class);
	
	@UiField
	Div layout;
	
	@UiField
	MaterialCollapsible collapsible;
	
	@UiField
	MaterialCollapsibleItem item;
	
	@UiField
	MaterialLabel header;
	
	@UiField
	MaterialCollapsibleHeader headerContainer;
	
	@UiField
	Image icon;
	
	@UiField
	Div content;
	
	
	public SensorItemCard() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setHeader(String text) {
		this.header.setText(text);
	}
	
	public void setIcon(String url) {
		this.icon.setUrl(url);
	}
	
	public Div getContent() {
		return this.content;
	}
	
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return headerContainer.addClickHandler(handler);
	}
	
	public boolean isActive() {
		return item.getStyleName().contains("active");
	}
	
	public void setActive(boolean active) {
		if(item.getStyleName().contains("active")) {
			collapsible.close(1);
		}else {
			collapsible.open(1);
		}
	}
}
