package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterIconInfo;
import com.google.gwt.maps.utility.markerclustererplus.client.ClusterIconStyle;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClusterer;
import com.google.gwt.maps.utility.markerclustererplus.client.MarkerClustererOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.MapSensorItemCard;
import com.opensense.dashboard.client.utils.MarkerInfoWindow;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.ValuePreview;

public class MapViewImpl extends DataPanelPageView implements MapView {

	@UiTemplate("MapView.ui.xml")
	interface MapViewUiBinder extends UiBinder<Widget, MapViewImpl> {
	}

	@UiField
	Div map;

	@UiField
	Div sensorContainer;

	@UiField
	Button recenterBtn;

	@UiField
	Button clearBtn;

	@UiField
	Button visuBtn;

	@UiField
	Button searchBtn;

	private static MapViewUiBinder uiBinder = GWT.create(MapViewUiBinder.class);

	protected Presenter presenter;
	private MapWidget mapWidget;

	// ########################################################################
	/*
	 * variables for Map & Markers
	 */
	// ########################################################################
	private Map<Integer, Marker> markers = new HashMap<>();
	private List<Integer> sensIds = new ArrayList<>();
	private List<Marker> mList = new ArrayList<>();
	private List<List<Sensor>> listOfSensors = new ArrayList<>();

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
		showThisMap();
		recenterBtn.add(new Image(GUIImageBundle.INSTANCE.recenter().getSafeUri().asString()));
		clearBtn.add(new Image(GUIImageBundle.INSTANCE.favorite().getSafeUri().asString()));
		visuBtn.add(new Image(GUIImageBundle.INSTANCE.diagramIconSvg().getSafeUri().asString()));
		searchBtn.add(new Image(GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString()));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	// Important do not delete
	@Override
	public void initView() {
		// init UI Elements if needed
	}

	@UiHandler("searchBtn")
	public void goToSearchPage(ClickEvent e) {
		goToSearchPage();
	}

	@UiHandler("visuBtn")
	public void onVisuBtnClicked(ClickEvent e) {
		goToVisuPage();
	}

	@UiHandler("recenterBtn")
	public void onRecenterBtnClicked(ClickEvent e) {
		recenterMap();
	}

	@UiHandler("clearBtn")
	public void onClearButtonClicked(ClickEvent e) {
		resetMarkerAndCluster();
	}

	private String getIconUrlFromType(MeasurandType measurandType) {
		switch (measurandType) {
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

	public void showSensorData(final List<Sensor> sensors) {
		sensorContainer.clear();
		if (!listOfSensors.contains(sensors)) {
			listOfSensors.add(sensors);
		}
		listOfSensors.forEach(list -> {
			final MapSensorItemCard card = new MapSensorItemCard();
			card.setHeader("Sensortyp: " + list.get(0).getMeasurand().getMeasurandType());
			card.setIcon(getIconUrlFromType(list.get(0).getMeasurand().getMeasurandType()));
			card.setIconTitle(list.get(0).getMeasurand().getDisplayName());
			card.getMiddleHeader().add(new Span(list.get(0).getAttributionText()));
			card.getMiddleHeader().add(new Span(list.get(0).getSensorModel()));
			card.getMiddleHeader().add(new Span("Sensoren: " + Integer.toString(list.size())));
			card.addClickHandler(event -> {
				event.stopPropagation();
				showMarkers(list);
				GWT.log(card.isActive() + "");
//				card.setActive(!card.isActive());
			});
			sensorContainer.add(card);
		});
	}

	private void showThisMap() {
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setMinZoom(2);
		mapOptions.setMaxZoom(18);
		mapOptions.setDraggable(true);
		mapOptions.setScaleControl(true);
		mapOptions.setStreetViewControl(false);
		mapOptions.setMapTypeControl(false);
		mapOptions.setScrollWheel(true);
		mapOptions.setPanControl(false);
		mapOptions.setZoomControl(false);
		MapImpl mapImpl = MapImpl.newInstance(map.getElement(), mapOptions);
		mapWidget = MapWidget.newInstance(mapImpl);
		mapWidget.setVisible(true);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.LEFT_BOTTOM, clearBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.RIGHT_BOTTOM, recenterBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.TOP_LEFT, searchBtn);
		mapWidget.setControls(com.google.gwt.maps.client.controls.ControlPosition.LEFT_TOP, visuBtn);
		mapWidget.addDragStartHandler(event -> {
			for (InfoWindow infoWindow : infoWindows.values()) {
				infoWindow.close();
				if (lastOpened != null) {
					lastOpened.close();
				}
				cluster.repaint();

			}
		});

		mapWidget.addZoomChangeHandler(event -> {
			if (mapWidget.getZoom() == 14) {
				checkForNearMarkers();
			}
			for (InfoWindow infoWindow : infoWindows.values()) {
				infoWindow.close();
				if (lastOpened != null) {
					lastOpened.close();
				}
				cluster.repaint();
			}
		});

		mapWidget.addClickHandler(event -> {
			for (InfoWindow infoWindow : infoWindows.values()) {
				infoWindow.close();
				if (lastOpened != null) {
					lastOpened.close();
				}
			}
		});
	}

	private void checkForNearMarkers() {
		mList.forEach(marker -> {
			if (mapWidget.getBounds().contains(marker.getPosition()) && !markerHasNearMarkers(presenter.getMarkerSpiderfier(), marker)) {
				MarkerOptions plusOptions = MarkerOptions.newInstance();
				plusOptions.setMap(mapWidget);
				plusOptions.setZindex(10000);
				plusOptions.setIcon(GUIImageBundle.INSTANCE.diagramIcon().getSafeUri().asString());
				final Marker plus = Marker.newInstance(plusOptions);
				plus.setPosition(marker.getPosition());
				//addMarkerToSpiderfier
				plus.addClickHandler(event-> {
					triggerClick(marker, marker.getPosition());
					plus.clear();
				});
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
		InfoWindowOptions iwOptions = InfoWindowOptions.newInstance();
		MarkerInfoWindow infoWindow = new MarkerInfoWindow();
		infoWindow.setHeader(si.getSensorModel() + " " + si.getSensorId());
		ArrayList<String> sensorData = new ArrayList<>();
		sensorData.add("Messeinheit: " + si.getMeasurand().getMeasurandType().toString());
		sensorData.add("Sensortyp: " + si.getSensorModel());
		sensorData.add("Genauigkeit: " + si.getAccuracy());
		sensorData.add("Hoehe ueber Grund: " + si.getAltitudeAboveGround());
		sensorData.add("Attribution: " + si.getAttributionText());
		infoWindow.setData(sensorData);
		iwOptions.setContent(infoWindow);
		sensorData.clear();
		InfoWindow iw = InfoWindow.newInstance(iwOptions);
		if (lastOpened != null) {
			lastOpened.close();
			lastOpened = null;
		}
		lastOpened = iw;
		infoWindows.put(marker, iw);
		iwsFromMarkers.put(si.getSensorId(), infoWindow);
		iw.open(mapWidget, marker);
	}

	private void setMarkers(Sensor s) {
		LatLng position = LatLng.newInstance(s.getLocation().getLat(), s.getLocation().getLon());
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		markerOpt.setTitle("Sensor postion is: " + s.getLocation().getLat() + ", " + s.getLocation().getLon()
				+ " Sensormodel: " + s.getSensorModel());
		final Marker markerBasic = Marker.newInstance(markerOpt);
		MarkerImage icon = MarkerImage.newInstance(getIconUrlFromType(s.getMeasurand().getMeasurandType()),
				Size.newInstance(30, 30));
		icon.setScaledSize(Size.newInstance(30, 30));
		markerBasic.setIcon(icon);
		markerBasic.setDraggable(false);
		markers.put(s.getSensorId(), markerBasic);
		mList.add(markerBasic);
		String idToString = Integer.toString(s.getSensorId());

		markerBasic.addClickHandler(event -> {
			if (!markerHasNearMarkers(presenter.getMarkerSpiderfier(), markerBasic)) {
				drawInfoWindow(markerBasic, s);
			} else {
				unspiderfy(presenter.getMarkerSpiderfier());
			}
			GWT.log("Current id: " + idToString);
		});

		markerBasic.addMouseOverHandler(event -> {
		});

		addMarkerToSpiderfier(presenter.getMarkerSpiderfier(), mapWidget.getJso(), markerBasic);
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
			mCo.setGridSize(40);
			mCo.setMinimumClusterSize(2);
			mCo.setMaxZoom(13);
			mCo.setZoomOnClick(true);
			mCo.setAverageCenter(true);
			ClusterIconStyle cis = ClusterIconStyle.newInstance();
			cis.setUrl(GUIImageBundle.INSTANCE.homeIconSvg().getSafeUri().asString());
			cis.setAnchorIcon(27,27);
			cis.setAnchor(52,53);
			mCo.setStyle(cis);
			cluster = MarkerClusterer.newInstance(mapWidget, mCo);
			cluster.addMarkers(mList);
			cluster.repaint();
		}
		if (sensorList.isEmpty()) {
			GWT.log("ERROR: Sensorlist is empty");
		}
		showSensorData(sensorList);
	}

	public void resetMarkerAndCluster() {
		if (!mList.isEmpty() || !markers.isEmpty()) {
			// DefaultCenter is Berlin Alexanderplatz
			// 52.521918,13.413215
			mapWidget.setCenter(LatLng.newInstance(52.521918, 13.413215));
			mapWidget.setZoom(4);
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

	public void showValuePreviewOnInfoWindow(Map<Integer, ValuePreview> preview) {
		if (!sensIds.isEmpty()) {
			preview.entrySet().forEach(entry -> {
				if (sensIds.contains(entry.getKey())) {
					if (iwsFromMarkers.containsKey(entry.getKey()) && entry.getValue() != null) {
						ArrayList<String> valueData = new ArrayList<>();
						valueData.add("Min Wert am " + Languages.getDate(entry.getValue().getMinValue().getTimestamp())
								+ ": " + entry.getValue().getMinValue().getNumberValue());
						valueData.add("Max Wert am " + Languages.getDate(entry.getValue().getMaxValue().getTimestamp())
								+ ": " + entry.getValue().getMaxValue().getNumberValue());
						iwsFromMarkers.get(entry.getKey()).setData(valueData);
					} else {
						iwsFromMarkers.get(entry.getKey()).getData().add(new Span("Keine Werte vorhanden"));
					}
				}
			});
		}
	}
}
