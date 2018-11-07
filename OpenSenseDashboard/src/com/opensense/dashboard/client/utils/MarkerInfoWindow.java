package com.opensense.dashboard.client.utils;

import java.util.List;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MarkerInfoWindow extends Composite {
	
	@UiField
	Div infoBox;
	@UiField
	Heading header;
	
	@UiField
	Div data;
	
	
	//binder der xml in die datei bindet
	@UiTemplate("MarkerInfoWindow.ui.xml")
	interface MarkerInfoWindowUiBinder extends UiBinder<Widget, MarkerInfoWindow> {
	}
	
	
	private static MarkerInfoWindowUiBinder uiBinder = GWT.create(MarkerInfoWindowUiBinder.class);
	
	public MarkerInfoWindow() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget getInfoBox() {
		return this.infoBox;
	}
	
	public void setHeader(String header) {
		this.header.setText(header);
	}
	
	public void setData(List<String> sensorData) {
		sensorData.forEach(item->this.data.add(new Span(item)));
	}
	
}
