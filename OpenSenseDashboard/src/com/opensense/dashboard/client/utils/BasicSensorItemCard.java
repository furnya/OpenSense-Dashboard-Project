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
import com.opensense.dashboard.shared.Sensor;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;

public class BasicSensorItemCard extends Composite{

	@UiTemplate("BasicSensorItemCard.ui.xml")
	interface BasicSensorItemCardUiBinder extends UiBinder<Widget, BasicSensorItemCard> {
	}

	private static BasicSensorItemCardUiBinder uiBinder = GWT.create(BasicSensorItemCardUiBinder.class);

	private boolean active = false;
	private boolean infoLoaded = false;

	@UiField
	Div layout;

	@UiField
	MaterialLabel header;

	@UiField
	Image icon;

	@UiField
	MaterialButton showInfoButton;

	@UiField
	MaterialButton favButton;

	@UiField
	MaterialButton trashButton;

	@UiField
	Spinner cardSpinner;

	@UiField
	Rating rating;

	@UiField
	Div content;

	@UiField
	MaterialImage check;

	public BasicSensorItemCard() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void addFavButtonClickHandler(ClickHandler handler) {
		this.favButton.getElement().getStyle().clearDisplay();
		this.favButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}

	public void addTrashButtonClickHandler(ClickHandler handler) {
		this.trashButton.getElement().getStyle().clearDisplay();
		this.trashButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}

	public void addShowInfoButtonClickHandler(ClickHandler handler) {
		this.showInfoButton.getElement().getStyle().clearDisplay();
		this.showInfoButton.addClickHandler(event -> {
			event.stopPropagation();
			if(this.layout.getElement().getClassName().contains("collapsed") && this.infoLoaded) {
				this.layout.getElement().removeClassName("collapsed");
			}else {
				if(this.infoLoaded) {
					this.layout.getElement().addClassName("collapsed");
				}else {
					handler.onClick(event);
				}
			}
		});
	}

	public void addActiveChangeHandler(ActiveChangeEventHandler handler) {
		this.layout.addDomHandler(event -> {
			this.setActive(!this.active);
			handler.onActiveChangeEvent(new ActiveChangeEvent(this.active));
		}, ClickEvent.getType());
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

	private void addContentValue(String key, String value) {
		Div container = new Div();
		container.addStyleName("flex");
		Span keySpan = new Span();
		keySpan.setText(key+": ");
		keySpan.addStyleName("title-sensor");
		Span valueSpan = new Span();
		valueSpan.setText(value);
		valueSpan.addStyleName("value-sensor");
		container.add(keySpan);
		container.add(valueSpan);
		this.content.add(container);
	}

	public void showSensorInfo(Sensor sensor) {
		this.content.clear();
		this.rating.setRating(sensor.getAccuracy()*10.0);
		this.addContentValue(Languages.userId(), sensor.getUserId()+"");
		this.addContentValue(Languages.unit(), sensor.getUnit().getDisplayName()+"");
		this.addContentValue(Languages.altitudeAboveGround(), sensor.getAltitudeAboveGround()+"m");
		this.addContentValue(Languages.directionVertical(), sensor.getDirectionVertical()+"�");
		this.addContentValue(Languages.directionHorizontal(), sensor.getDirectionHorizontal()+"�");
		this.addContentValue(Languages.sensorModel(), sensor.getSensorModel());
		this.addContentValue(Languages.attributionText(), sensor.getAttributionText());
		this.addContentValue(Languages.attributionURL(), sensor.getAttributionURLString());
		if((sensor.getValuePreview() != null)) {
			this.addContentValue(Languages.firstValue(), Languages.getDate(sensor.getValuePreview().getMinValue().getTimestamp()) + " - " + sensor.getValuePreview().getMinValue().getNumberValue() + " " + sensor.getUnit().getDisplayName());
			this.addContentValue(Languages.lastValue(),	Languages.getDate(sensor.getValuePreview().getMaxValue().getTimestamp()) + " - " + sensor.getValuePreview().getMaxValue().getNumberValue() + " " + sensor.getUnit().getDisplayName());
		}else {
			this.addContentValue(Languages.values(), Languages.noValuePreviewData());
		}
		this.infoLoaded = true;
		this.layout.getElement().removeClassName("collapsed");
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

	public void showLoadingIndicator(boolean show) {
		//TODO:
	}

}
