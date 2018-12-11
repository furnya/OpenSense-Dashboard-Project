package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.controls.MapTypeStyle;
import com.google.gwt.maps.client.maptypes.MapTypeStyleElementType;
import com.google.gwt.maps.client.maptypes.MapTypeStyleFeatureType;
import com.google.gwt.maps.client.maptypes.MapTypeStyler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterIconStyle;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClusterer;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClustererOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.ListManagerOptions;
import com.opensense.dashboard.client.utils.MarkerInfoWindow;
import com.opensense.dashboard.client.utils.MeasurandIconHelper;
import com.opensense.dashboard.client.utils.PagerSize;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;

public class MapViewImpl extends DataPanelPageView implements MapView {

	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}

	private ListManager listManager;

	@UiField
	Div map;

	@UiField
	Div sensorContainer;

	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);

	protected Presenter presenter;
	private MapWidget mapWidget;

	private final LatLng defaultCenter = LatLng.newInstance(52.521918, 13.413215);

	// ########################################################################
	/*
	 * variables for Map & Markers
	 */
	// ########################################################################
	private Map<Integer, Marker> markers = new HashMap<>();
	private List<Integer> sensIds = new ArrayList<>();
	private List<Marker> mList = new ArrayList<>();
	private List<List<Sensor>> listOfSensors = new ArrayList<>();
	MapOptions mapOptions;

	private Button recenterBtn = new Button();

	private Button searchBtn = new Button();

	private Button visuBtn = new Button();

	private MarkerClusterer cluster;
	// This should be a HashMap
	// ########################################################################
	/*
	 * variables for InfoWindow
	 */
	// ########################################################################
	// last opened infoWindow
	private InfoWindow lastOpened;
	private Map<Marker, InfoWindow> infoWindows = new HashMap<>();
	private Map<Integer, MarkerInfoWindow> iwsFromMarkers = new HashMap<>();

	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	// Important do not delete
	@Override
	public void initView(Runnable runnable) {
		// init UI Elements if needed
		initMap();
		initMapHandler();
		initMapButtons();
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(),
				this.sensorContainer);
		listManagerOptions.setEditingActive(true);
		listManagerOptions.setPagerSize(PagerSize.SMALL);
		listManagerOptions.setShowMapButton(false);
		listManagerOptions.setShowSearchButton(false);
		listManagerOptions.setShowVisualizationButton(false);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> event.getSelectedIds().forEach(id -> GWT.log(id + "")));
	}

	private void initMap() {
		mapOptions = MapOptions.newInstance();
		mapOptions.setMinZoom(2);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setStreetViewControl(false);
		mapOptions.setMapTypeControl(false);
		mapOptions.setScrollWheel(true);
		mapOptions.setPanControl(false);
		mapOptions.setZoomControl(true);
		setMapStyles();
	}

	private void setMapStyles() {
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
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
	}

	private void initMapButtons() {
		if (markers.isEmpty()) {
			recenterBtn.setEnabled(false);
			searchBtn.setEnabled(false);
			visuBtn.setEnabled(false);
		}
		recenterBtn.add(new Image(GUIImageBundle.INSTANCE.recenter().getSafeUri().asString()));
		recenterBtn.addStyleName("m-btn recenter-icon");
		recenterBtn.setTitle("recenters map to fit bounds");
		recenterBtn.addClickHandler(event -> recenterMap());
		searchBtn.add(new Image(GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString()));
		searchBtn.addStyleName("m-btn search-icon");
		searchBtn.setTitle("gets all sensors and sends them to searchPage");
		searchBtn.addClickHandler(event -> goToSearchPage());
		visuBtn.add(new Image(GUIImageBundle.INSTANCE.diagramIconSvg().getSafeUri().asString()));
		visuBtn.addStyleName("m-btn visual-icon");
		visuBtn.setTitle("gets all sensors and sends them to visuPage");
		visuBtn.addClickHandler(event -> goToVisuPage());
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.RIGHT_BOTTOM, recenterBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.TOP_LEFT, searchBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.LEFT_TOP, visuBtn);
	}

	private void initMapHandler() {
		Window.addResizeHandler(event -> {
			GWT.log("resizeHandler 1");
			if (lastOpened != null) {
				lastOpened.close();
				lastOpened = null;
			}
		});

		mapWidget.addDragStartHandler(event -> {
			if (lastOpened != null) {
				GWT.log("closed 1");
				lastOpened.close();
				lastOpened = null;
			}
		});

		mapWidget.addZoomChangeHandler(event -> {
			if (lastOpened != null) {
				GWT.log("closed 2");
				lastOpened.close();
				lastOpened = null;
			}
		});

		mapWidget.addClickHandler(event -> {
			if (lastOpened != null) {
				GWT.log("closed 3");
				lastOpened.close();
				lastOpened = null;
			}
		});

	}

	// this function will add a basic InfoWindow to the Markers on the Map
	// Sensor Data:
	/*
	 * license: boolean ? true/false -> if true shows licenseId: Int - false-> ---
	 * id: int accuracy: int altitudeAboveGround: int sensorModel: String
	 * attributionText: String
	 * 
	 * 
	 */
	protected void drawInfoWindow(Marker marker, Sensor si) {
		if (marker == null || si.getSensorId() < 0) {
			return;
		}
		GWT.log("NEU");
		InfoWindowOptions iwOptions = InfoWindowOptions.newInstance();
		MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		infoWindow.setHeader(si.getSensorModel() + " " + si.getSensorId());
		infoWindow.setInfoWindowRating(si.getAccuracy());
		ArrayList<String> sensorData = new ArrayList<>();
		sensorData.add(Languages.measurand() + ": " + si.getMeasurand().getMeasurandType().toString());
		sensorData.add(Languages.sensorTyp() + ": " + si.getSensorModel());
		sensorData.add(Languages.accuracy() + ": " + si.getAccuracy());
		sensorData.add(Languages.altitudeAboveGround() + " " + si.getAltitudeAboveGround());
		sensorData.add(Languages.origin() + " " + si.getAttributionText());
		infoWindow.setData(sensorData);
		iwOptions.setContent(infoWindow);
		iwOptions.setDisableAutoPan(true);
		iwOptions.setDisableAutoPan(false);
		sensorData.clear();
		InfoWindow iw = InfoWindow.newInstance(iwOptions);
		if (lastOpened != null) {
			GWT.log("closed 0");
			lastOpened.close();
			lastOpened = null;
		}
		lastOpened = iw;
		iwsFromMarkers.put(si.getSensorId(), infoWindow);
		iw.open(mapWidget, marker);
	}

	private void setMarkers(Sensor s) {
		LatLng position = LatLng.newInstance(s.getLocation().getLat(), s.getLocation().getLon());
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		final Marker markerBasic = Marker.newInstance(markerOpt);
		MarkerImage icon = MarkerImage.newInstance(MeasurandIconHelper.getIconUrlFromType(s.getMeasurand().getMeasurandType()),
				Size.newInstance(30, 30));
		icon.setScaledSize(Size.newInstance(30, 30));
		markerBasic.setIcon(icon);
		markerBasic.setDraggable(false);
		markers.put(s.getSensorId(), markerBasic);
		mList.add(markerBasic);
		String idToString = Integer.toString(s.getSensorId());

		markerBasic.addClickHandler(event -> {
			if (!markerHasNearMarkers(presenter.getMarkerSpiderfier(), markerBasic)) {
				recenterToCenter(markerBasic);
				drawInfoWindow(markerBasic, s);
			} else {
				unspiderfy(presenter.getMarkerSpiderfier());
			}
			GWT.log("Current id: " + idToString);
		});

		markerBasic.addMouseOverHandler(event -> {
		});
		markersOnMapButtonEnabler();
		addMarkerToSpiderfier(presenter.getMarkerSpiderfier(), mapWidget.getJso(), markerBasic);
	}

	private void markersOnMapButtonEnabler() {
		if (!markers.isEmpty()) {
			recenterBtn.setEnabled(true);
			searchBtn.setEnabled(true);
			visuBtn.setEnabled(true);
		}
	}

	private native void unspiderfy(JavaScriptObject spiderfier) /*-{
		spiderfier.unspiderfy();
	}-*/;

	private native void addMarkerToSpiderfier(JavaScriptObject spiderfier, JavaScriptObject mapWidget,
			Marker marker) /*-{
		spiderfier.addMarker(marker);
	}-*/;

	private native boolean markerHasNearMarkers(JavaScriptObject spiderfier, Marker marker)/*-{
		return spiderfier.markersNearMarker(marker, true).length == 1;
	}-*/;

	public void showMarkers(List<Sensor> sensorList) {
		if (!mList.isEmpty()) {
			resetMarkerAndCluster();
		}
		if (!sensorList.isEmpty()) {
			sensorList.forEach(item -> {
				GWT.log(item.getLocation().getLat() + " " + item.getLocation().getLon() + " SensorID: "
						+ item.getSensorId());
				setMarkers(item);
				sensIds.add(item.getSensorId());
			});
			MarkerClustererOptions mCo = MarkerClustererOptions.newInstance();
			mCo.setGridSize(80);
			mCo.setMinimumClusterSize(2);
			mCo.setMaxZoom(15);
			mCo.setZoomOnClick(true);
			mCo.setAverageCenter(true);
			List<ClusterIconStyle> cises = new ArrayList<>();
			ClusterIconStyle cis1 = ClusterIconStyle.newInstance();
			ClusterIconStyle cis2 = ClusterIconStyle.newInstance();
			ClusterIconStyle cis3 = ClusterIconStyle.newInstance();
			ClusterIconStyle cis4 = ClusterIconStyle.newInstance();
			ClusterIconStyle cis5 = ClusterIconStyle.newInstance();
			cises.add(cis1);
			cises.add(cis2);
			cises.add(cis3);
			cises.add(cis4);
			cises.add(cis5);
			cis1.setUrl(GUIImageBundle.INSTANCE.m1().getSafeUri().asString());
			cis2.setUrl(GUIImageBundle.INSTANCE.m2().getSafeUri().asString());
			cis3.setUrl(GUIImageBundle.INSTANCE.m3().getSafeUri().asString());
			cis4.setUrl(GUIImageBundle.INSTANCE.m4().getSafeUri().asString());
			cis5.setUrl(GUIImageBundle.INSTANCE.m5().getSafeUri().asString());
			cises.forEach(cis -> {
				cis.setTextColor("WHITE");
				cis.setHeight(90);
				cis.setWidth(90);
			});
			// Markers inside Cluster: blue < 10 <= orange < 100 <= red < 1000 <= purple
			cis1.setAnchor(27, 29);
			cis2.setAnchor(25, 25);
			cis3.setAnchor(25, 23);
			cis4.setAnchor(24, 24);
			cis5.setAnchor(23, 22);
			mCo.setStyles(cises);
			cluster = MarkerClusterer.newInstance(mapWidget, mCo);
			cluster.addMarkers(mList);
			cluster.repaint();
		}
		if (sensorList.isEmpty()) {
			GWT.log("ERROR: Sensorlist is empty");
		}
	}

	public void resetMarkerAndCluster() {
		if (!mList.isEmpty() || !markers.isEmpty()) {
			mList.clear();
			markers.clear();
			cluster.clearMarkers();
			sensIds.clear();
			GWT.log("clearing All clusters & markers");
		}
	}

	public void recenterMap() {
		if (markers.isEmpty()) {
			return;
		}
		cluster.fitMapToMarkers();
	}

	public void recenterToCenter(Marker m) {
		if (markers.isEmpty()) {
			return;
		}
		mapWidget.setCenter(m.getPosition());
	}

	public void goToVisuPage() {
		if (mList.isEmpty()) {
			GWT.log("User tried to visualize a empty Markerlist");
		}
		presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, sensIds));
	}

	public void goToSearchPage() {
		if (mList.isEmpty()) {
			GWT.log("User tried to go to Search Page while sending a empty Markerlist");
		}
		presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, sensIds));
	}

	@Override
	public MapWidget getMapWidget() {
		return mapWidget;
	}

	@Override
	public Map<Integer, Marker> getMarkers() {
		return markers;
	}

	protected native void triggerClick(JavaScriptObject marker, LatLng position)/*-{
		$wnd.google.maps.event.trigger(marker, 'spider_click', position);
	}-*/;
}
