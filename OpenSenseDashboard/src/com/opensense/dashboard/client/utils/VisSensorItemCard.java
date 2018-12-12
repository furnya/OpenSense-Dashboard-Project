package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import gwt.material.design.client.ui.MaterialPreLoader;

public class VisSensorItemCard extends Composite{
	
	@UiTemplate("VisSensorItemCard.ui.xml")
	interface VisSensorItemCardUiBinder extends UiBinder<Widget, VisSensorItemCard> {
	}
	
	private static VisSensorItemCardUiBinder uiBinder = GWT.create(VisSensorItemCardUiBinder.class);
	
	@UiField
	Div layout;

	@UiField
	Div content;

	@UiField
	MaterialLabel header;

	@UiField
	Image icon;

	@UiField
	Div midContainer;

	@UiField
	MaterialCheckBox checkbox;

	@UiField
	Image favButton;

	@UiField
	Rating rating;

	@UiField
	MaterialPreLoader cardSpinner;
	
	public VisSensorItemCard() {
		initWidget(uiBinder.createAndBindUi(this));
		addClickHandler();
		this.setActive(true);
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

	private void addClickHandler() {
		this.layout.addDomHandler(event -> this.checkbox.setValue(!this.checkbox.getValue(), true), ClickEvent.getType());
		this.addClickListener(this.checkbox.getElement());
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return this.checkbox.addValueChangeHandler(event -> {
			this.setActive(event.getValue());
			handler.onValueChange(event);
		});
	}

	public void setActive(boolean active) {
		this.checkbox.setValue(active);
		if(active) {
//			this.layout.addStyleName("card-active");
			this.getElement().getStyle().setBackgroundColor("#cccccc");
		}else {
//			this.layout.removeStyleName("card-active");
			this.getElement().getStyle().setBackgroundColor("white");
		}
	}	

	public void setRating(Double accuracy) {
		if(accuracy != null) {
			this.rating.setRating(accuracy * 10);
		}
	}

	private native void addClickListener(Element elem) /*-{
		elem.addEventListener("click", function(event){
			event.stopPropagation();
		});
	}-*/;

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
	
	public void showLoadingIndicator() {
		if(checkbox.getValue()) {
			cardSpinner.getElement().addClassName("card-spinner-active");
		}else {
			cardSpinner.getElement().removeClassName("card-spinner-active");
		}
		cardSpinner.getElement().getStyle().clearDisplay();
	}
	
	public void hideLoadingIndicator() {
		cardSpinner.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	public void clearContent() {
		this.content.clear();
	}
}
