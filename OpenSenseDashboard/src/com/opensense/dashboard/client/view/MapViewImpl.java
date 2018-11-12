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
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterIconStyle;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClusterer;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClustererOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.utils.MarkerInfoWindow;
import com.opensense.dashboard.shared.Sensor;

public class MapViewImpl extends DataPanelPageView implements MapView {
	
	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	@UiField
	Div map;

	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);
	
	protected Presenter presenter;
	private MapWidget mapWidget;
	
	//########################################################################
	/*
	 * variables for Map & Markers
	 * */
	//########################################################################
	private MapOptions mapOptions;
	private Map<Integer,Marker> markers = new HashMap<>();
	private List<Marker> mList = new ArrayList<Marker>();
	private MarkerClusterer cluster;
	//This should be a HashMap
	//########################################################################
	/*
	 * variables for InfoWindow
	 * */
	//########################################################################
	//last opened infoWindow
	private ArrayList<InfoWindow> lastOpened = new ArrayList<>();
	private Map<Marker,InfoWindow> infoWindows = new HashMap<>();
	int id = 0;
	
	
	
	
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
		//DefaultCenter is Berlin Alexanderplatz 
		//52.521918,13.413215
		mapOptions.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapOptions.setMinZoom(3);
		mapOptions.setMaxZoom(15);
		mapOptions.setZoom(5);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setScrollWheel(true);
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
		
		mapWidget.addDragStartHandler(event-> {
			for(InfoWindow infoWindow:infoWindows.values()){
				infoWindow.close();
				lastOpened.clear();
				cluster.repaint();
			}
		});
		
		mapWidget.addZoomChangeHandler(event-> {
			for(InfoWindow infoWindow:infoWindows.values()){
				infoWindow.close();
				lastOpened.clear();
				//Mabye not doing anyting ?!
				cluster.repaint();
			}
		});
		
		mapWidget.addClickHandler(event-> {
			for(InfoWindow infoWindow:infoWindows.values()){
				infoWindow.close();
				lastOpened.clear();
			}
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
	protected void drawInfoWindow(Marker marker,Integer id) {
		    if (marker == null || id < 0) {
		      return;
		    }
		    
		    InfoWindowOptions options = InfoWindowOptions.newInstance();
		    MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		    infoWindow.setHeader("DemoSensor X");
		    ArrayList<String> testData = new ArrayList<>();
			testData.add("Temperatur: "+ "20");
		    testData.add("Ort:  Berlin");
		    testData.add("Einheit:  Grad Celcius");
		    infoWindow.setData(testData);
		    options.setContent(infoWindow);
		    InfoWindow iw = InfoWindow.newInstance(options);
		    lastOpened.add(iw);
		    infoWindows.put(marker, iw);
		    String size = Integer.toString(lastOpened.size());
		    GWT.log("This is lastOpened size: " + size);
		    if(lastOpened.size()>1) {
		    	GWT.log("There is a opened InfoWindow");
		    	closeLastInfoWindow(lastOpened.get(0));
		    	iw.open(mapWidget,marker);
		    }else {
		    	iw.open(mapWidget,marker);
		    }
		  }
	
	 
	 //Still not working -> should zoom and center to markers position on click
	 public void resize(double bg, double lg) {
		    LatLng reCenter = LatLng.newInstance(bg,lg);
		    MapHandlerRegistration.trigger(mapWidget, MapEventType.RESIZE);        
		    mapOptions.setCenter(reCenter);
			mapOptions.setZoom(16);
		}
	 
	 
	public void closeLastInfoWindow(InfoWindow iw) {
		lastOpened.get(0).close();
		lastOpened.remove(0);
	}
	 
	 
	 
	private void setMarkers(double bg, double lg) {
			LatLng position = LatLng.newInstance(bg, lg);
			MarkerOptions markerOpt = MarkerOptions.newInstance();
			markerOpt.setPosition(position);
			markerOpt.setTitle("Current postion is: "+ bg + ", " + lg);
			Marker markerBasic = Marker.newInstance(markerOpt);
			markerBasic.setMap(mapWidget);
			markerBasic.setIcon(GUIImageBundle.INSTANCE.testtempIconSvg().getSafeUri().asString());
			markerBasic.setDraggable(false);
			markers.put(id++,markerBasic);
			mList.add(markerBasic);
			String idToString = Integer.toString(id);
			markerBasic.addClickHandler(event->{
						GWT.log("Current id: "+idToString);
						drawInfoWindow(markerBasic,id);
//						resize(bg, lg);
			});
	}
	
	public void showMarkers(List<Sensor> sensorList) {
		LatLng postion = LatLng.newInstance(52.521918,13.413215);
		GWT.log(postion.getLatitude()+" "+ postion.getLongitude());
		
		setMarkers(postion.getLatitude(),postion.getLongitude());
		setMarkers(postion.getLatitude()+0.0211,postion.getLongitude()+0.0112);
		MarkerClustererOptions mCO = MarkerClustererOptions.newInstance();
		mCO.setMaxZoom(11);
		mCO.setGridSize(30);
		ClusterIconStyle clusterStyle = ClusterIconStyle.newInstance();
		clusterStyle.setUrl(GUIImageBundle.INSTANCE.m1Icon().getSafeUri().asString());
		mCO.setStyle(clusterStyle);
		cluster = MarkerClusterer.newInstance(mapWidget,mCO);
		cluster.addMarkers(mList);
//		sensorList.forEach(item-> {
//			LatLng postion = LatLng.newInstance(52.521918,13.413215);
//			GWT.log(postion.getLatitude()+" "+ postion.getLongitude());
//			
//			setMarkers(postion.getLatitude(),postion.getLongitude());
//			MarkerClusterer cluster = MarkerClusterer.newInstance(mapWidget);
//			cluster.repaint();
//			cluster.addMarkers(mList);
//		});
		
	}	
}