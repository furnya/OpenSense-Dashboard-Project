package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;

public class SensorItemCard extends Composite{
	
	@UiTemplate("SensorItemCard.ui.xml")
	interface SensorItemCardUiBinder extends UiBinder<Widget, SensorItemCard> {
	}
	
	private static SensorItemCardUiBinder uiBinder = GWT.create(SensorItemCardUiBinder.class);
	
	@UiField
	Div layout;
	
	@UiField
	MaterialLabel header;
	
	@UiField
	Image icon;
	
	@UiField
	Div midContainer;
	
	@UiField
	MaterialCheckBox checkbox;
	
	public SensorItemCard() {
		initWidget(uiBinder.createAndBindUi(this));
		addClickHandler();
	}
	
	public void setHeader(String text) {
		this.header.setText(text);
	}
	
	public void setIcon(String url) {
		this.icon.setUrl(url);
	}
	
	public void setIconTitle(String title) {
		this.icon.setTitle(title);
	}
	
	public Div getMiddleHeader() {
		return this.midContainer;
	}
	
	private void addClickHandler() {
		layout.addDomHandler(event -> checkbox.setValue(!checkbox.getValue(), true), ClickEvent.getType());
	}
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return checkbox.addValueChangeHandler(event -> {
			if(event.getValue()) {
				layout.addStyleName("card-active");
				layout.removeStyleName("card-deactive");
			}else {
				layout.addStyleName("card-deactive");
				layout.removeStyleName("card-active");
			}
			handler.onValueChange(event);
		});
	}
}
