package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MapViewImpl extends Composite implements MapView {
	
	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	@UiField
	Div map;

	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);
	
	protected Presenter presenter;
	
	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		showThisMap();
		
	}

	private void showThisMap() {
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setZoom(20);
		mapOptions.setCenter(LatLng.newInstance(52.0, 52.0));
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		MapWidget mapWidget = MapWidget.newInstance(mapImpl);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
}