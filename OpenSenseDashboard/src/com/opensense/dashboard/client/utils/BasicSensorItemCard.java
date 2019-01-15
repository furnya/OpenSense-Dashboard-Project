package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
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
import com.opensense.dashboard.client.view.ListManagerView;
import com.opensense.dashboard.shared.Sensor;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPreLoader;

public class BasicSensorItemCard extends Composite{

	@UiTemplate("BasicSensorItemCard.ui.xml")
	interface BasicSensorItemCardUiBinder extends UiBinder<Widget, BasicSensorItemCard> {
	}

	private static BasicSensorItemCardUiBinder uiBinder = GWT.create(BasicSensorItemCardUiBinder.class);

	private int sensorId;
	private ListManagerView view;

	@UiField
	Div layout;

	@UiField
	MaterialCollapsibleBody collapsibleBody;

	@UiField
	MaterialCollapsible collapsible;

	@UiField
	MaterialCollapsibleItem collapsibleItem;

	@UiField
	MaterialLabel header;

	@UiField
	Image icon;

	@UiField
	MaterialCheckBox checkbox;

	@UiField
	MaterialImage expandButton;

	@UiField
	MaterialImage favButton;

	@UiField
	MaterialImage trashButton;

	@UiField
	MaterialPreLoader cardSpinner;

	@UiField
	Rating rating;

	@UiField
	Div content;

	@UiField
	Div previewContainer;

	@UiField
	Span firstValue;

	@UiField
	Span lastValue;

	@UiField
	MaterialCollapsibleHeader collapsibleHeader;

	public BasicSensorItemCard(Integer sensorId, ListManagerView view) {
		this.view = view;
		this.sensorId = sensorId;
		this.initWidget(uiBinder.createAndBindUi(this));
		this.addClickHandler();
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

	public void setHeader(String text) {
		this.header.setText(text);
	}

	public void setIcon(String url) {
		this.icon.setUrl(url);
	}

	public void setIconTitle(String title) {
		this.icon.setTitle(title);
	}

	private void addClickHandler() {
		this.collapsibleItem.addDomHandler(event -> this.checkbox.setValue(!this.checkbox.getValue(), true), ClickEvent.getType());
		this.addClickListener(this.checkbox.getElement());
		this.addClickListener(this.collapsibleItem.getElement());
		this.addClickListener(this.collapsibleBody.getElement());
		this.expandButton.addDomHandler(event -> {
			if(this.collapsibleItem.isActive()) {
				this.collapsibleItem.collapse();
				this.collapsibleHeader.removeStyleName("colHeader-active");
			}else {
				this.loadAllSensorInfo();
			}
		}, ClickEvent.getType());
		this.addClickListener(this.expandButton.getElement());
	}

	private void loadAllSensorInfo() {
		this.view.loadAllSensorInfo(this.sensorId, this);
	}

	public void hideBody() {
		this.collapsibleBody.setDisplay(Display.NONE);
	}

	private void addInfoPair(String key, String value) {
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
		this.collapsibleItem.expand();
		this.collapsibleHeader.addStyleName("colHeader-active");
		this.rating.setRating(sensor.getAccuracy()*10.0);
		this.addInfoPair(Languages.userId(), sensor.getUserId()+"");
		this.addInfoPair(Languages.unit(), sensor.getUnit().getDisplayName()+"");
		this.addInfoPair(Languages.altitudeAboveGround(), sensor.getAltitudeAboveGround()+"m");
		this.addInfoPair(Languages.directionVertical(), sensor.getDirectionVertical()+"�");
		this.addInfoPair(Languages.directionHorizontal(), sensor.getDirectionHorizontal()+"�");
		this.addInfoPair(Languages.sensorModel(), sensor.getSensorModel());
		this.addInfoPair(Languages.attributionText(), sensor.getAttributionText());
		this.addInfoPair(Languages.attributionURL(), sensor.getAttributionURLString());
		if(sensor.getValuePreview()!=null) {
			this.firstValue.setText(Languages.getDate(sensor.getValuePreview().getMinValue().getTimestamp()) + " - " + sensor.getValuePreview().getMinValue().getNumberValue());
			this.lastValue.setText(Languages.getDate(sensor.getValuePreview().getMaxValue().getTimestamp()) + " - " + sensor.getValuePreview().getMaxValue().getNumberValue());
		}else {
			this.firstValue.setText(Languages.noValuePreviewData());
			this.lastValue.setText(Languages.noValuePreviewData());
		}
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
			this.layout.addStyleName("card-active");
		}else {
			this.layout.removeStyleName("card-active");
			this.layout.getElement().removeAttribute("style");
		}
	}

	private native void addClickListener(Element elem) /*-{
		elem.addEventListener("click", function(event){
			event.stopPropagation();
		});
	}-*/;

	public void showLoadingIndicator(boolean show) {
		//TODO:
	}

}
