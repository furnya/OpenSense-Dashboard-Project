package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.controls.MapTypeStyle;
import com.google.gwt.maps.client.maptypes.MapTypeStyleElementType;
import com.google.gwt.maps.client.maptypes.MapTypeStyleFeatureType;
import com.google.gwt.maps.client.maptypes.MapTypeStyler;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.ActiveChangeEvent;
import com.opensense.dashboard.client.event.ActiveChangeEventHandler;
import com.opensense.dashboard.client.model.Size;
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

	@UiField
	Div headerContainer;

	@UiField
	Div map;

	@UiField
	Div cardActionBtnContainer;

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
				this.showInfoButton.setTooltip(Languages.hideInformation());
			}else {
				this.showInfoButton.setTooltip(Languages.information());
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
		this.addContentValue(Languages.directionVertical(), sensor.getDirectionVertical()+"°");
		this.addContentValue(Languages.directionHorizontal(), sensor.getDirectionHorizontal()+"°");
		this.addContentValue(Languages.sensorModel(), sensor.getSensorModel());
		this.addContentValue(Languages.attributionText(), sensor.getAttributionText());
		this.addContentValue(Languages.attributionURL(), sensor.getAttributionURLString());
		if((sensor.getValuePreview() != null)) {
			this.addContentValue(Languages.firstValue(), Languages.getDate(sensor.getValuePreview().getMinValue().getTimestamp()) + " - " + String.valueOf(sensor.getValuePreview().getMinValue().getNumberValue()).substring(0, 10) + " " + sensor.getUnit().getDisplayName());
			this.addContentValue(Languages.lastValue(),	Languages.getDate(sensor.getValuePreview().getMaxValue().getTimestamp()) + " - " + String.valueOf(sensor.getValuePreview().getMaxValue().getNumberValue()).substring(0, 10) + " " + sensor.getUnit().getDisplayName());
		}else {
			this.addContentValue(Languages.values(), Languages.noValuePreviewData());
		}
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setCenter(LatLng.newInstance(sensor.getLocation().getLat(),sensor.getLocation().getLon()));
		mapOptions.setZoom(10);
		mapOptions.setMinZoom(2);
		mapOptions.setMaxZoom(18);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setStreetViewControl(false);
		mapOptions.setMapTypeControl(false);
		mapOptions.setScrollWheel(true);
		mapOptions.setPanControl(false);
		mapOptions.setZoomControl(true);
		mapOptions.setDisableDoubleClickZoom(true);
		MapTypeStyle mapStyle = MapTypeStyle.newInstance();
		MapTypeStyle mapStyle2 = MapTypeStyle.newInstance();
		mapStyle.setFeatureType(MapTypeStyleFeatureType.POI);
		mapStyle2.setFeatureType(MapTypeStyleFeatureType.TRANSIT);
		mapStyle.setElementType(MapTypeStyleElementType.LABELS);
		mapStyle2.setElementType(MapTypeStyleElementType.LABELS);

		String visibility = "off";
		MapTypeStyler styler = MapTypeStyler.newVisibilityStyler(visibility);
		MapTypeStyler[] stylers = new MapTypeStyler[1];
		stylers[0] = styler;
		mapStyle.setStylers(stylers);
		mapStyle2.setStylers(stylers);
		MapTypeStyle[] mapStyleArray = new MapTypeStyle[2];
		mapStyleArray[0] = mapStyle;
		mapStyleArray[1] = mapStyle2;
		mapOptions.setMapTypeStyles(mapStyleArray);
		MapImpl mapImpl = MapImpl.newInstance(this.map.getElement(), mapOptions);
		MapWidget mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
		LatLng position = LatLng.newInstance(sensor.getLocation().getLat(),sensor.getLocation().getLon());
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		markerOpt.setMap(mapWidget);
		MarkerImage iconMap = MarkerImage.newInstance(MeasurandIconHelper.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
		iconMap.setScaledSize(com.google.gwt.maps.client.base.Size.newInstance(30, 30));
		markerOpt.setIcon(iconMap);
		Marker marker = Marker.newInstance(markerOpt);
		marker.setDraggable(false);
		this.infoLoaded = true;
		this.layout.getElement().removeClassName("collapsed");
		this.map.addDomHandler(event -> event.stopPropagation(), ClickEvent.getType());
	}

	public void setActive(boolean active) {
		if(active) {
			this.layout.addStyleName("card-active");
			this.check.getElement().getStyle().clearDisplay();
		}else {
			this.headerContainer.getElement().getStyle().clearBackgroundColor();
			this.layout.removeStyleName("card-active");
			this.check.getElement().getStyle().setDisplay(Display.NONE);
		}
		this.active = active;
	}

	public void setColor(String color) {
		this.layout.removeStyleName("card-active");
		this.headerContainer.getElement().setAttribute("style",  "background-color: " + color);
	}

	public void showLoadingIndicator(boolean show) {
		//TODO:
	}

	public Element getInfoButtonElement() {
		return this.showInfoButton.getElement();
	}

	/**
	 * sets the card size only small
	 * @param size
	 */
	public void setSize(Size size) {
		if(Size.SMALL.equals(size)) {
			this.layout.getElement().setAttribute("size", size.name());
			this.rating.setSize(size);
			this.cardActionBtnContainer.getElement().setAttribute("size", size.name());
		}
	}

	public void removeMap() {
		this.map.removeFromParent();
	}

}
