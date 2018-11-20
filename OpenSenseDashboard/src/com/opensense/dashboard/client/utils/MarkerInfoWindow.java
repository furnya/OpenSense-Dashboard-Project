package com.opensense.dashboard.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.view.MapView.Presenter;

import gwt.material.design.client.ui.MaterialButton;

public class MarkerInfoWindow extends Composite {

	@UiField
	Div infoBox;
	@UiField
	Heading header;

	@UiField
	Div data;

	@UiField
	MaterialButton iwVisuBtn;

	@UiField
	MaterialButton iwSearchBtn;

	private List<Integer> currentID = new ArrayList<>();
	
	// binder der xml in die datei bindet
	@UiTemplate("MarkerInfoWindow.ui.xml")
	interface MarkerInfoWindowUiBinder extends UiBinder<Widget, MarkerInfoWindow> {
	}

	private static MarkerInfoWindowUiBinder uiBinder = GWT.create(MarkerInfoWindowUiBinder.class);

	protected Presenter presenter;

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
		sensorData.forEach(item -> {
			this.data.add(new Span(item));
			});
	}

	public void passID(List<Integer> id) {
		if(!currentID.isEmpty()) {
			currentID.clear();
		}
		if(id.isEmpty()) {
			GWT.log("passing ID failed");
		}
		currentID.add(id.get(0));
	}

	@UiHandler("iwVisuBtn")
	public void oniwVisuBtnClicked(ClickEvent e) {
		getSensorAndShowVisu();
	}

	public void getSensorAndShowVisu() {
		GWT.log("VisuBtn pressed");
		if (currentID.isEmpty()) {
			GWT.log("ERROR: ID must be set");
			GWT.log(" THIS IS " + currentID.size());
		}
		presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, currentID));
	}

	@UiHandler("iwSearchBtn")
	public void oniwSearchBtnClicked(ClickEvent e) {
		getSensorAndShowInSearch();

	}

	public void getSensorAndShowInSearch() {
		GWT.log("SearchBtn pressed");
		if (currentID.isEmpty()) {
			GWT.log("ERROR: ID must be set");
			GWT.log(" THIS IS " + currentID.size());
		}
		presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, currentID));
	}
}
