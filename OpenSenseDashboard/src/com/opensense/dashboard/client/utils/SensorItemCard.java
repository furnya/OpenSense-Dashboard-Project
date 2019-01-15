package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.ActiveChangeEvent;
import com.opensense.dashboard.client.event.ActiveChangeEventHandler;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;

public class SensorItemCard extends Composite{

	@UiTemplate("SensorItemCard.ui.xml")
	interface SensorItemCardUiBinder extends UiBinder<Widget, SensorItemCard> {
	}

	private static SensorItemCardUiBinder uiBinder = GWT.create(SensorItemCardUiBinder.class);

	private boolean active = false;

	@UiField
	Div layout;

	@UiField
	Div content;

	@UiField
	MaterialLabel header;

	@UiField
	Image icon;

	@UiField
	Div headerContainer;

	@UiField
	MaterialButton favButton;

	@UiField
	Rating rating;

	@UiField
	MaterialImage check;

	public SensorItemCard() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void addFavButtonClickHandler(ClickHandler handler) {
		this.favButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
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

	public Div getContent() {
		return this.content;
	}

	public void addActiveChangeHandler(ActiveChangeEventHandler handler) {
		this.layout.addDomHandler(event -> {
			this.setActive(!this.active);
			handler.onActiveChangeEvent(new ActiveChangeEvent(this.active));
		}, ClickEvent.getType());
	}

	public void setActive(boolean active) {
		if(active) {
			this.layout.addStyleName("card-active");
			this.check.getElement().getStyle().clearDisplay();
		}else {
			this.layout.removeStyleName("card-active");
			this.check.getElement().getStyle().setDisplay(Display.NONE);
		}
		this.active = active;
	}

	public void setRating(Double accuracy) {
		if(accuracy != null) {
			this.rating.setRating(accuracy * 10);
		}
	}

	public void addContentValue(String titleText, String valueText) {
		Div valueCon = new Div();
		valueCon.addStyleName("flex");
		Span title = new Span(titleText);
		title.addStyleName("title-sensor");
		valueCon.add(title);
		Span value = new Span(valueText);
		value.addStyleName("value-sensor");
		valueCon.add(value);
		this.content.add(valueCon);
	}
}
