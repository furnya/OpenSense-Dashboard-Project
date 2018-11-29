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
	
	public void setValuePreviewConent(String firstValue, String lastValue) {
		previewContainer.clear();
		Div firstDiv = new Div();
		Span titleSpanFirst = new Span(Languages.firstValue());
		titleSpanFirst.addStyleName("title-sensor");
		Span spanFirst = new Span(firstValue);
		spanFirst.setStyleName("value-sensor");
		firstDiv.add(titleSpanFirst);
		firstDiv.add(spanFirst);
		Div lastDiv = new Div();
		Span titleSpanLast = new Span(Languages.lastValue());
		titleSpanLast.addStyleName("title-sensor");
		Span spanLirst = new Span(lastValue);
		spanLirst.setStyleName("value-sensor");
		lastDiv.add(titleSpanLast);
		lastDiv.add(spanLirst);
		previewContainer.add(firstDiv);
		previewContainer.add(lastDiv);
	}
	
	private native void addClickListener(Element elem) /*-{
		elem.addEventListener("click", function(event){
			event.stopPropagation();
		});
	}-*/;
}
