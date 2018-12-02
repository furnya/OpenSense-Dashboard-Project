package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPreLoader;

public class SensorItemCard extends Composite{
	
	@UiTemplate("SensorItemCard.ui.xml")
	interface SensorItemCardUiBinder extends UiBinder<Widget, SensorItemCard> {
	}
	
	private static SensorItemCardUiBinder uiBinder = GWT.create(SensorItemCardUiBinder.class);
	
	@UiField
	Div layout;
	
	@UiField
	Div content;
	
	@UiField
	Div previewContainer;
	
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
	Span firstValue;
	
	@UiField
	Span lastValue;
	
	@UiField
	MaterialPreLoader firstSpinner;
	
	@UiField
	MaterialPreLoader lastSpinner;
	
	public SensorItemCard() {
		initWidget(uiBinder.createAndBindUi(this));
		addClickHandler();
	}
	
	@UiHandler("favButton")
	public void onFavButtonClicked(ClickEvent e) {
		e.stopPropagation();
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
		layout.addDomHandler(event -> checkbox.setValue(!checkbox.getValue(), true), ClickEvent.getType());
		addClickListener(checkbox.getElement());
	}
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return checkbox.addValueChangeHandler(event -> {
			setActive(event.getValue());
			handler.onValueChange(event);
		});
	}

	public void setActive(boolean active) {
		checkbox.setValue(active);
		if(active) {
			layout.addStyleName("card-active");
		}else {
			layout.removeStyleName("card-active");
		}
	}
	
	public void setValuePreviewConent(String firstText, String lastText) {
		showPreviewContentSpinner(false);
		firstValue.setText(firstText);
		lastValue.setText(lastText);
	}
	
	/**
	 * shows the preview spinner which indicates that the content will be shown
	 * clears the content before showing
	 * @param show
	 */
	public void showPreviewContentSpinner(boolean show) {
		if(show) {
			firstValue.setText("");
			lastValue.setText("");
			firstSpinner.setDisplay(Display.BLOCK);
			lastSpinner.setDisplay(Display.BLOCK);
		}else {
			firstSpinner.setDisplay(Display.NONE);
			lastSpinner.setDisplay(Display.NONE);
		}
	}
	
	public void setRating(Double accuracy) {
		if(accuracy != null) {
			rating.setRating(accuracy * 10);
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
		content.add(valueCon);
	}
}
