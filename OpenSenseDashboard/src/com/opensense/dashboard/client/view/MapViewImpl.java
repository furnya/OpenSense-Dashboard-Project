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
import com.google.gwt.user.client.ui.Widget;

public class MapViewImpl extends DataPanelPageView implements MapView {
	
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
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView() {
		// init UI Elements if needed
	}
	
	private void showThisMap() {
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapOptions.setZoom(10);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setScrollWheel(true);
		
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		MapWidget mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
	}


}