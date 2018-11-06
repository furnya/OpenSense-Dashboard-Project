package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.MapEventType;
import com.google.gwt.maps.client.events.MapHandlerRegistration;
import com.google.gwt.maps.client.events.MouseEvent;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;

public class MapViewImpl extends DataPanelPageView implements MapView {
	
	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	@UiField
	Div map;

	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);
	
	protected Presenter presenter;
	private MapWidget mapWidget;
	private MapOptions mapOptions;
	
	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		showThisMap();
		setMarkers(52.521918,13.513215);
		resize();
		setMarkers(52.12341,13.34235);
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
		mapOptions = MapOptions.newInstance();
		mapOptions.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapOptions.setZoom(7);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setScrollWheel(true);
		
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
	}
	
	
	 protected void drawInfoWindow(Marker marker, MouseEvent mouseEvent) {
		    if (marker == null || mouseEvent == null) {
		      return;
		    }

		    HTML html = new HTML("You clicked on: " + mouseEvent.getLatLng().getToString());

		    InfoWindowOptions options = InfoWindowOptions.newInstance();
		    options.setContent(html);
		    InfoWindow iw = InfoWindow.newInstance(options);
		    iw.open(mapWidget, marker);
		  }
	
	 public void resize() {
		    LatLng center = LatLng.newInstance(52.521918,13.413215);
		    MapHandlerRegistration.trigger(mapWidget, MapEventType.RESIZE);        
		    mapOptions.setCenter(center);
		}
	 
	 
	 
	 
	private void setMarkers(double bg, double lg) {
			LatLng position = LatLng.newInstance(bg, lg);
			MarkerOptions markerOpt = MarkerOptions.newInstance();
			markerOpt.setPosition(position);
			markerOpt.setTitle("Berlin Alexanderplatz");
			final Marker markerBasic = Marker.newInstance(markerOpt);
			markerBasic.setMap(mapWidget); 
			markerBasic.setIcon(GUIImageBundle.INSTANCE.tempIconSvg().getSafeUri().asString());
			markerBasic.addClickHandler(new ClickMapHandler() {
			      @Override
			      public void onEvent(ClickMapEvent event) {
			        drawInfoWindow(markerBasic, event.getMouseEvent());
			      }
			    });
	}
}