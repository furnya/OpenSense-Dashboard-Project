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
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.controls.ControlPosition;
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
	private List<Integer> sensIds = new ArrayList<>();
	private List<Marker> mList = new ArrayList<>();
	private List<List<Sensor>> listOfSensors = new ArrayList<>();
	private MapOptions mapOptions;

	private List<Marker> plusClusterIcons = new ArrayList<>();

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
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	// Important do not delete
	@Override
	public void initView(Runnable runnable) {
		// init UI Elements if needed
		this.initMap();
		this.initMapHandler();
		this.initMapButtons();
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(),
				this.sensorContainer);
		listManagerOptions.setEditingActive(false);
		listManagerOptions.setPagerSize(com.opensense.dashboard.client.model.Size.SMALL);
		listManagerOptions.setSpinnerSize(com.opensense.dashboard.client.model.Size.SMALL);
		listManagerOptions.setShowMapButton(false);
		listManagerOptions.setShowSearchButton(false);
		listManagerOptions.setShowVisualizationButton(false);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> {
			if(!event.getSelectedIds().isEmpty()) {
				this.presenter.buildSensorRequestFromIdsAndShowMarkers(event.getSelectedIds());
			}else {
				this.resetMarkerAndCluster();
			}
		});

	}

	private void initMap() {
		this.mapOptions = MapOptions.newInstance();
		this.mapOptions.setMinZoom(2);
		this.mapOptions.setDraggable(true);
		this.mapOptions.setScaleControl(true);
		this.mapOptions.setStreetViewControl(false);
		this.mapOptions.setMapTypeControl(false);
		this.mapOptions.setScrollWheel(true);
		this.mapOptions.setPanControl(false);
		this.mapOptions.setZoomControl(true);
		this.setMapStyles();
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
		this.mapOptions.setMapTypeStyles(mapStyleArray);
		MapImpl mapImpl = MapImpl.newInstance(this.map.getElement(), this.mapOptions);
		this.mapWidget = MapWidget.newInstance(mapImpl);
		this.mapWidget.setVisible(true);
	}

	private void initMapButtons() {
		if (this.mList.isEmpty()) {
			this.recenterBtn.setEnabled(false);
			this.searchBtn.setEnabled(false);
			this.visuBtn.setEnabled(false);
		}
		this.recenterBtn.add(new Image(GUIImageBundle.INSTANCE.recenter().getSafeUri().asString()));
		this.recenterBtn.addStyleName("m-btn recenter-icon");
		this.recenterBtn.setTitle("recenters map to fit bounds");
		this.recenterBtn.addClickHandler(event -> this.recenterMap());
		this.searchBtn.add(new Image(GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString()));
		this.searchBtn.addStyleName("m-btn search-icon");
		this.searchBtn.setTitle("gets all sensors and sends them to searchPage");
		this.searchBtn.addClickHandler(event -> this.goToSearchPage());
		this.visuBtn.add(new Image(GUIImageBundle.INSTANCE.diagramIconSvg().getSafeUri().asString()));
		this.visuBtn.addStyleName("m-btn visual-icon");
		this.visuBtn.setTitle("gets all sensors and sends them to visuPage");
		this.visuBtn.addClickHandler(event -> this.goToVisuPage());
		this.mapWidget.setControls(ControlPosition.RIGHT_BOTTOM, this.recenterBtn);
		this.mapWidget.setControls(ControlPosition.TOP_LEFT, this.searchBtn);
		this.mapWidget.setControls(ControlPosition.LEFT_TOP, this.visuBtn);
	}

	private void initMapHandler() {
		Window.addResizeHandler(event -> {
			if (this.lastOpened != null) {
				this.lastOpened.close();
				this.lastOpened = null;
			}
		});

		this.mapWidget.addDragStartHandler(event -> {
			if (this.lastOpened != null) {
				this.lastOpened.close();
				this.lastOpened = null;
			}
		});

		this.mapWidget.addZoomChangeHandler(event -> {
			if (this.lastOpened != null) {
				this.lastOpened.close();
				this.lastOpened = null;
			}
			if ((this.mapWidget.getZoom() > 15) && (this.cluster != null)) {
				this.cluster.repaint();
				this.checkForSpiderfierMarkers();
			}
			if ((this.mapWidget.getZoom() <= 15) && (this.cluster != null)) {
				this.plusClusterIcons.forEach(Marker::clear);
				this.plusClusterIcons.clear();
				this.cluster.repaint();
			}
		});

		this.mapWidget.addClickHandler(event -> {
			if (this.lastOpened != null) {
				this.lastOpened.close();
				this.lastOpened = null;
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
		if ((marker == null) || (si.getSensorId() < 0)) {
			return;
		}
		InfoWindowOptions iwOptions = InfoWindowOptions.newInstance();
		MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		infoWindow.setHeader(si.getSensorModel() + " " + si.getSensorId());
		infoWindow.setInfoWindowRating(si.getAccuracy());
		ArrayList<String> sensorData = new ArrayList<>();
		ArrayList<String> dataDesriptors = new ArrayList<>();
		dataDesriptors.add(Languages.measurand()+ ": ");
		dataDesriptors.add(Languages.sensorTyp()+ ": ");
		dataDesriptors.add(Languages.accuracy()+ ": ");
		dataDesriptors.add(Languages.altitudeAboveGround()+ " ");
		dataDesriptors.add(Languages.origin()+ " ");
		infoWindow.setDataDescriptor(dataDesriptors);
		sensorData.add(si.getMeasurand().getMeasurandType().toString());
		sensorData.add(si.getSensorModel());
		sensorData.add(""+si.getAccuracy());
		sensorData.add(""+si.getAltitudeAboveGround());
		sensorData.add(si.getAttributionText());
		infoWindow.setData(sensorData);
		iwOptions.setContent(infoWindow);
		iwOptions.setDisableAutoPan(true);
		iwOptions.setDisableAutoPan(false);
		sensorData.clear();
		InfoWindow iw = InfoWindow.newInstance(iwOptions);
		if (this.lastOpened != null) {
			this.lastOpened.close();
			this.lastOpened = null;
		}
		this.lastOpened = iw;
		this.iwsFromMarkers.put(si.getSensorId(), infoWindow);
		iw.open(this.mapWidget, marker);
	}

	private void setMarkers(Sensor sensor) {
		LatLng position = LatLng.newInstance(sensor.getLocation().getLat(), sensor.getLocation().getLon());
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		final Marker markerBasic = Marker.newInstance(markerOpt);
		MarkerImage icon = MarkerImage
				.newInstance(MeasurandIconHelper.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
		icon.setScaledSize(Size.newInstance(30, 30));
		markerBasic.setIcon(icon);
		markerBasic.setDraggable(false);
		this.mList.add(markerBasic);
		markerBasic.addClickHandler(event -> {
			if (!this.markerHasNearMarkers(this.presenter.getMarkerSpiderfier(), markerBasic)) {
				this.recenterToCenter(markerBasic);
				this.drawInfoWindow(markerBasic, sensor);
			}
		});

		markerBasic.addMouseOverHandler(event -> {
		});
		this.markersOnMapButtonEnabler();
		this.addMarkerToSpiderfier(this.presenter.getMarkerSpiderfier(), markerBasic);
	}

	@Override
	public void checkForSpiderfierMarkers() { // TODO: bug this should also be called from mapmove event so the event gets updated
		this.mList.forEach(marker -> {
			if (this.mapWidget.getBounds().contains(marker.getPosition()) && this.markerHasNearMarkers(this.presenter.getMarkerSpiderfier(), marker) &&
					this.plusClusterIcons.stream().noneMatch(pC -> pC.getPosition().equals(marker.getPosition()))) {
				this.addPlusCluster(marker);
			}
		});
	}

	@Override
	public void addPlusCluster(Marker marker) {
		MarkerOptions plusOpt = MarkerOptions.newInstance();
		plusOpt.setPosition(marker.getPosition());
		final Marker plusMarker = Marker.newInstance(plusOpt);
		plusMarker.setMap(this.mapWidget);
		MarkerImage plusIcon = MarkerImage.newInstance(GUIImageBundle.INSTANCE.m1Plus().getSafeUri().asString());
		plusIcon.setScaledSize(Size.newInstance(70, 60));
		plusIcon.setAnchor(Point.newInstance(45, 45));
		plusMarker.setZindex(10000);
		plusMarker.setIcon(plusIcon);
		plusMarker.setDraggable(false);
		this.plusClusterIcons.add(plusMarker);
		plusMarker.addClickHandler(event -> {
			plusMarker.clear();
			this.plusClusterIcons.remove(plusMarker);
			this.triggerClick(marker, event.getMouseEvent().getLatLng());
		});
	}

	protected native void triggerClick(JavaScriptObject marker, LatLng position)/*-{
		$wnd.google.maps.event.trigger(marker, 'click', position);
	}-*/;

	private void markersOnMapButtonEnabler() {
		if (!this.mList.isEmpty()) {
			this.recenterBtn.setEnabled(true);
			this.searchBtn.setEnabled(true);
			this.visuBtn.setEnabled(true);
		}
	}

	private native void addMarkerToSpiderfier(JavaScriptObject spiderfier, Marker marker) /*-{
		spiderfier.addMarker(marker);
	}-*/;

	private native boolean markerHasNearMarkers(JavaScriptObject spiderfier, Marker marker)/*-{
		return spiderfier.markersNearMarker(marker, true).length == 1;
	}-*/;

	@Override
	public void showMarkers(List<Sensor> sensorList) {
		if (!this.mList.isEmpty()) {
			this.resetMarkerAndCluster();
		}
		if (!sensorList.isEmpty()) {
			sensorList.forEach(item -> {
				GWT.log(item.getLocation().getLat() + " " + item.getLocation().getLon() + " SensorID: "
						+ item.getSensorId());
				this.setMarkers(item);
				this.sensIds.add(item.getSensorId());
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
			this.cluster = MarkerClusterer.newInstance(this.mapWidget, mCo);
			this.cluster.addMarkers(this.mList);
			this.cluster.repaint();
		}
		if (sensorList.isEmpty()) {
			GWT.log("ERROR: Sensorlist is empty");
		}else {
			recenterMap();
		}
		
	}

	@Override
	public void resetMarkerAndCluster() {
		if (!this.mList.isEmpty()) {
			this.mList.clear();
			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, false, new ArrayList<>()));
			this.cluster.clearMarkers();
			this.sensIds.clear();
			GWT.log("clearing All clusters & markers");
		}
	}

	public void recenterMap() {
		if (this.mList.isEmpty()) {
			return;
		}
		this.cluster.fitMapToMarkers();
	}

	public void recenterToCenter(Marker m) {
		if (this.mList.isEmpty()) {
			return;
		}
		this.mapWidget.setCenter(m.getPosition());
	}

	public void goToVisuPage() {
		if (this.mList.isEmpty()) {
			GWT.log("User tried to visualize a empty Markerlist");
		}
		this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, this.sensIds));
	}

	public void goToSearchPage() {
		if (this.mList.isEmpty()) {
			GWT.log("User tried to go to Search Page while sending a empty Markerlist");
		}
		this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, this.sensIds));
	}

	@Override
	public MapWidget getMapWidget() {
		return this.mapWidget;
	}

	@Override
	public ListManager getListManager() {
		return this.listManager;
	}
}
