package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.controls.ControlPosition;
import com.google.gwt.maps.client.events.MapEventType;
import com.google.gwt.maps.client.events.MapHandlerRegistration;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterCalculator;
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterIconStyle;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClusterer;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClustererOptions;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.utils.MarkerInfoWindow;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;

import gwt.material.design.client.ui.MaterialButton;

public class MapViewImpl extends DataPanelPageView implements MapView {
	
	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	@UiField
	Div map;
	
	@UiField
	MaterialButton recenterBtn;
	
	@UiField
	MaterialButton clearBtn;
	
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
	private List<Marker> mList = new ArrayList<>();
	private List<Marker> allMarkers = new ArrayList<>();
	private List<MarkerClusterer> allClusters = new ArrayList<>();
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
	
	private String getIconUrlFromType(MeasurandType measurandType) {
		switch(measurandType) {
		case AIR_PRESSURE:
			return GUIImageBundle.INSTANCE.pressureIconSvg().getSafeUri().asString();
		case BRIGHTNESS:
			return GUIImageBundle.INSTANCE.sunnyIconSvg().getSafeUri().asString();
		case CLOUDINESS:
			return GUIImageBundle.INSTANCE.cloudsIconSvg().getSafeUri().asString();
		case HUMIDITY:
			return GUIImageBundle.INSTANCE.humidityIconSvg().getSafeUri().asString();
		case NOISE:
			return GUIImageBundle.INSTANCE.noiseIconSvg().getSafeUri().asString();
		case PM10:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PM2_5:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PRECIPITATION_AMOUNT:
			return GUIImageBundle.INSTANCE.precipitaionIconSvg().getSafeUri().asString();
		case PRECIPITATION_TYPE:
			return GUIImageBundle.INSTANCE.precipitationTypeIconSvg().getSafeUri().asString();
		case TEMPERATURE:
			return GUIImageBundle.INSTANCE.tempIconSvg().getSafeUri().asString();
		case WIND_DIRECTION:
			return GUIImageBundle.INSTANCE.windDirectionIconSvg().getSafeUri().asString();
		case WIND_SPEED:
			return GUIImageBundle.INSTANCE.windSpeedIconSvg().getSafeUri().asString();
		default:
			return GUIImageBundle.INSTANCE.questionIconSvg().getSafeUri().asString();
		}
	}
	
	private void showThisMap() {
		mapOptions = MapOptions.newInstance();
		//DefaultCenter is Berlin Alexanderplatz 
		//52.521918,13.413215
		mapOptions.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapOptions.setMinZoom(2);
		mapOptions.setMaxZoom(18);
		mapOptions.setZoom(2);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setStreetViewControl(false);
		mapOptions.setMapTypeControl(false);
		mapOptions.setScrollWheel(true);
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.BOTTOM_CENTER, clearBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.LEFT_TOP, recenterBtn);
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
	protected void drawInfoWindow(Marker marker,Integer id, String smodel, double accuracy, double aGround) {
		    if (marker == null || id < 0) {
		      return;
		    }  
		    InfoWindowOptions options = InfoWindowOptions.newInstance();
		    MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		    infoWindow.setHeader(smodel + " " + id);
		    ArrayList<String> sensorData = new ArrayList<>();
		    sensorData.add("Sensortyp: "+ smodel);
		    sensorData.add("Genauigkeit: "+ accuracy);
		    sensorData.add("SensorID: "+ id);
		    sensorData.add("Hoehe ueber Grund: "+ aGround);
		    infoWindow.setData(sensorData);
		    options.setContent(infoWindow);
		    sensorData.clear();
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
		    mapWidget.setZoom(15);
		    mapWidget.setCenter(reCenter);
		}
	 
	public void closeLastInfoWindow(InfoWindow iw) {
		lastOpened.get(0).close();
		lastOpened.remove(0);
	}
	
	private void setMarkers(double bg, double lg, int sensorID, String smodel, double accuracy, double aGround, MeasurandType measurandType) {
			LatLng position = LatLng.newInstance(bg, lg);
			MarkerOptions markerOpt = MarkerOptions.newInstance();
			markerOpt.setPosition(position);
			markerOpt.setTitle("Sensor postion is: "+ bg + ", " + lg + " Sensormodel: "+ smodel);
			final Marker markerBasic = Marker.newInstance(markerOpt);
			MarkerImage icon = MarkerImage.newInstance(getIconUrlFromType(measurandType), Size.newInstance(20, 20));
			icon.setScaledSize(Size.newInstance(20, 20));
			markerBasic.setIcon(icon);
			markerBasic.setDraggable(false);
			markers.put(sensorID,markerBasic);
			mList.add(markerBasic);
			allMarkers.add(markerBasic);
			String idToString = Integer.toString(sensorID);
			markerBasic.addClickHandler(event->{
						GWT.log("Current id: "+idToString);
						resize(bg, lg);
						drawInfoWindow(markerBasic,sensorID,smodel,accuracy,aGround);
						
			});
	}
	
	public void showMarkers(List<Sensor> sensorList) {
		sensorList.forEach(item-> {
			LatLng postion = LatLng.newInstance(item.getLocation().getLat(),item.getLocation().getLon());
			/*variables for Sensors*/
			int id = item.getId();
			String sensormodel = item.getSensorModel();
			double sensorAccuracy = item.getAccuracy();
			double aboveGround = item.getAltitudeAboveGround();
			
			GWT.log(postion.getLatitude()+" "+ postion.getLongitude()+" SensorID: "+ id);
			
			setMarkers(postion.getLatitude(),postion.getLongitude(),id,sensormodel,sensorAccuracy, aboveGround, item.getMeasurand().getMeasurandType());
		});
		MarkerClustererOptions mCO = MarkerClustererOptions.newInstance();
		mCO.setGridSize(80);
		mCO.setAverageCenter(true);
		mCO.setMinimumClusterSize(2);
		mCO.setMaxZoom(14);
		mCO.setZoomOnClick(true);
		cluster = MarkerClusterer.newInstance(mapWidget,mCO);
		cluster.addMarkers(mList);
		allClusters.add(cluster);
		cluster.repaint();
	}	 
	
	
	@UiHandler("clearBtn")
	public void onClearButtonClicked(ClickEvent e) {
			resetMarkerAndCluster();
	}
	public void resetMarkerAndCluster() {
		mapWidget.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapWidget.setZoom(2);
		allClusters.forEach(c->c.clearMarkers());
		mList.clear();
		allMarkers.clear();
		markers.clear();
	}
	
	
	
	@UiHandler("recenterBtn")
	public void onRecenterBtnClicked(ClickEvent e) {
			recenterMap();
	}
	public void recenterMap() {
		mapWidget.setCenter(LatLng.newInstance(52.521918,13.413215));
		mapWidget.setZoom(2);
	}
	
	
//	public native void initSpiderfier(MapWidget mapWidget) /*-{
//		var oms = new OverlappingMarkerSpiderfier(mapWidget,{
//		markersWontMove: true,
//		markersWontHide: true,
//		basicFormatEvents: true
//		});
//	}-)*/;
}