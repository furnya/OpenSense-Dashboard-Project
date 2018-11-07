package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.MapEventType;
import com.google.gwt.maps.client.events.MapHandlerRegistration;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.utils.MarkerInfoWindow;

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
	private Map<Integer,Marker> markers = new HashMap<>();
	//This should be a HashMap
	/*NOTE: 
	 * 
	 * Changing the List to a HashMap will cause problems with the Handlers-> should be considered while changing
	 * 
	 * */
	private List<InfoWindow> infoWindows = new ArrayList<>();
	
	
	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		showThisMap();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	//Important do not delete
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
		
		mapWidget.addDragStartHandler(event-> {
			infoWindows.forEach(InfoWindow::close);
		});
		
		mapWidget.addZoomChangeHandler(event-> {
			infoWindows.forEach(InfoWindow::close);
		});
		
		mapWidget.addClickHandler(event-> {
			infoWindows.forEach(InfoWindow::close);
		});
	}
	
	//this function will add a basic InfoWindow to the Markers on the Map
	//Sensor Data:
	/*
	 * license: boolean ? true/false -> if true shows licenseId: Int - false-> ---
	 * id: int
	 * accuracy: int
	 * altitudeAboveGround: int
	 * sensorModel: String
	 * attributionText: String
	 * 
	 * 
	 * */
	
	
	 protected void drawInfoWindow(Marker marker,String sensor) {
		    if (marker == null) {
		      return;
		    }
		    InfoWindowOptions options = InfoWindowOptions.newInstance();
		    MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		    infoWindow.setHeader("DemoSensor X");
		    ArrayList<String> testData = new ArrayList<String>();
		    testData.add("Temperatur: "+ "20");
		    testData.add("Ort:  Berlin");
		    testData.add("Einheit:  Grad Celcius");
		    infoWindow.setData(testData);
		    options.setContent(infoWindow);
		    InfoWindow iw = InfoWindow.newInstance(options);
		    iw.open(mapWidget, marker);
		    infoWindows.add(iw);
		  }
	
	 public void resize() {
		    LatLng center = LatLng.newInstance(52.521918,13.413215);
		    MapHandlerRegistration.trigger(mapWidget, MapEventType.RESIZE);        
		    mapOptions.setCenter(center);
		}
	 
	 
	 
	 
	private void setMarkers(double bg, double lg, String sensor) {
			LatLng position = LatLng.newInstance(bg, lg);
			MarkerOptions markerOpt = MarkerOptions.newInstance();
			markerOpt.setPosition(position);
			final Marker markerBasic = Marker.newInstance(markerOpt);
			markerBasic.setMap(mapWidget);
			markerBasic.setIcon(GUIImageBundle.INSTANCE.testtempIconSvg().getSafeUri().asString());
			markerBasic.addClickHandler(event-> drawInfoWindow(markerBasic, sensor));
			int i = 0;
			markers.put(i++,markerBasic);
	}
	
	private void markerClusterer(Map<Integer,Marker> markers) {
		
	}
	
	public void showMarkers(List<String> stringlist) {
		stringlist.forEach(item-> {
			LatLng postion = LatLng.newInstance(Double.valueOf(item.split(",")[0]),Double.valueOf(item.split(",")[1]));
			GWT.log(postion.getLatitude()+" "+ postion.getLongitude());
			
			setMarkers(postion.getLatitude(),postion.getLongitude(),item);
			resize();
		});
		
	}

}