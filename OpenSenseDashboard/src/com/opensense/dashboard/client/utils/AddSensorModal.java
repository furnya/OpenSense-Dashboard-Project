package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.controls.MapTypeStyle;
import com.google.gwt.maps.client.maptypes.MapTypeStyleElementType;
import com.google.gwt.maps.client.maptypes.MapTypeStyleFeatureType;
import com.google.gwt.maps.client.maptypes.MapTypeStyler;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.ListsView.Presenter;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.License;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Unit;

import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTextBox;

public class AddSensorModal extends Composite {

	@UiTemplate("AddSensorModal.ui.xml")
	interface AddSensorModalUiBinder extends UiBinder<Widget, AddSensorModal> {
	}

	private static AddSensorModalUiBinder uiBinder = GWT.create(AddSensorModalUiBinder.class);

	private Map<Integer, Unit> unitMap;
	private Map<MaterialTextBox, Boolean> validMap = new HashMap<>();

	private MapOptions mapOptions;
	private MapWidget mapWidget;
	private Marker marker;

	private Presenter presenter;

	@UiField
	Div map;

	@UiField
	Input placeBox;

	@UiField
	MaterialModal modal;

	@UiField
	MaterialListBox measurandList;

	@UiField
	MaterialListBox unitList;

	@UiField
	MaterialListBox licenseList;

	@UiField
	MaterialTextBox latitudeBox;

	@UiField
	MaterialTextBox longitudeBox;

	@UiField
	MaterialTextBox altitudeBox;

	@UiField
	MaterialTextBox directionVerticalBox;

	@UiField
	MaterialTextBox directionHorizontalBox;

	@UiField
	MaterialTextBox sensorModelBox;

	@UiField
	MaterialTextBox accuracyBox;

	@UiField
	MaterialTextBox attributionTextBox;

	@UiField
	MaterialTextBox attributionUrlBox;

	@UiField
	MaterialButton closeButton;

	@UiField
	MaterialButton confirmButton;

	@UiField
	MaterialModalContent content;

	@UiField
	FormPanel uploadForm;

	@UiField
	FileUpload fileUpload;

	@UiField
	MaterialImage infoIcon;

	public AddSensorModal(Presenter presenter) {
		this.presenter = presenter;
		this.initWidget(uiBinder.createAndBindUi(this));
		this.measurandList.addValueChangeHandler(event -> {
			this.filterUnits(Integer.valueOf(event.getValue()));
		});
		this.modal.setId("dashboard-modal");
		this.initMap();
		this.initAutoComplete();
		this.initMarker();
		this.addValidators();
		this.initValidMap();
		this.initFileUpload();
		//Remove the modal from DOM to prevent multiple modal which stays forever in the DOM
		this.modal.addCloseHandler(event -> RootPanel.get().remove(RootPanel.get("dashboard-modal")));
	}

	@UiHandler("closeButton")
	public void onCloseButtonClicked(ClickEvent e) {
		this.modal.close();
	}

	@UiHandler("confirmButton")
	public void onConfirmButtonClicked(ClickEvent e) {
		this.uploadForm.submit();
	}

	private void initFileUpload() {
		this.uploadForm.setAction(GWT.getHostPageBaseURL() + "fileupload");
		this.uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		this.uploadForm.setMethod(FormPanel.METHOD_POST);
		this.uploadForm.addSubmitHandler(event -> {
			String filename = this.fileUpload.getFilename();
			if(filename.length()==0) {
				event.cancel();
				this.sendCreateSensorRequest(false);
				return;
			}
			if (!filename.endsWith(".csv")) {
				event.cancel();
				AppController.showInfo("Es können nur Datein im csv Format hochgeladen werden");
			}
		});
		this.uploadForm.addSubmitCompleteHandler(event -> {
			if(event.getResults().contains(Languages.successful())) {
				this.sendCreateSensorRequest(true);
			}else {
				AppController.showError(event.getResults());
			}
		});
	}
	
	private void sendCreateSensorRequest(boolean valuesAttached) {
		CreateSensorRequest request = new CreateSensorRequest();
		request.setValuesAttached(valuesAttached);
		request.setAccuracy(Double.valueOf(this.accuracyBox.getValue()));
		request.setAltitudeAboveGround(Double.valueOf(this.altitudeBox.getValue()));
		request.setAttributionText(this.attributionTextBox.getValue());
		request.setAttributionURL(this.attributionUrlBox.getValue());
		request.setDirectionHorizontal(Double.valueOf(this.directionHorizontalBox.getValue()));
		request.setDirectionVertical(Double.valueOf(this.directionVerticalBox.getValue()));
		request.setLatitude(Double.valueOf(this.latitudeBox.getValue()));
		request.setLongitude(Double.valueOf(this.longitudeBox.getValue()));
		request.setLicenseId(Integer.valueOf(this.licenseList.getValue()));
		request.setMeasurandId(Integer.valueOf(this.measurandList.getValue()));
		request.setSensorModel(this.sensorModelBox.getValue());
		request.setUnitId(Integer.valueOf(this.unitList.getValue()));
		this.presenter.createSensorRequest(request);
		this.modal.close();
	}

	private void initValidMap() {
		this.validMap.put(this.latitudeBox, true);
		this.validMap.put(this.longitudeBox, true);
		this.validMap.put(this.altitudeBox, false);
		this.validMap.put(this.directionVerticalBox, false);
		this.validMap.put(this.directionHorizontalBox, false);
		this.validMap.put(this.sensorModelBox, false);
		this.validMap.put(this.accuracyBox, false);
		this.validMap.put(this.attributionTextBox, false);
		this.validMap.put(this.attributionUrlBox, false);
	}

	private void addValidators() {
		this.latitudeBox.addValidator(this.createDoubleValidator(-90, 90));
		this.latitudeBox.addValueChangeHandler(this.createLatLngValueChangeHandler(this.latitudeBox));
		this.longitudeBox.addValidator(this.createDoubleValidator(-180, 180));
		this.longitudeBox.addValueChangeHandler(this.createLatLngValueChangeHandler(this.longitudeBox));
		BlankValidator<String> vString = this.createStringValidator();
		this.sensorModelBox.addValidator(vString);
		this.sensorModelBox.addValueChangeHandler(this.createStringValueChangeHandler(this.sensorModelBox));
		this.attributionTextBox.addValidator(vString);
		this.attributionTextBox.addValueChangeHandler(this.createStringValueChangeHandler(this.attributionTextBox));
		this.attributionUrlBox.addValidator(vString);
		this.attributionUrlBox.addValueChangeHandler(this.createStringValueChangeHandler(this.attributionUrlBox));
		this.altitudeBox.addValidator(this.createDoubleValidator(0, Double.POSITIVE_INFINITY));
		this.altitudeBox.addValueChangeHandler(this.createStringValueChangeHandler(this.altitudeBox));
		this.directionVerticalBox.addValidator(this.createDoubleValidator(-360, 360));
		this.directionVerticalBox.addValueChangeHandler(this.createStringValueChangeHandler(this.directionVerticalBox));
		this.directionHorizontalBox.addValidator(this.createDoubleValidator(-360, 360));
		this.directionHorizontalBox.addValueChangeHandler(this.createStringValueChangeHandler(this.directionHorizontalBox));
		this.accuracyBox.addValidator(this.createDoubleValidator(0, 10));
		this.accuracyBox.addValueChangeHandler(this.createStringValueChangeHandler(this.accuracyBox));
	}

	private BlankValidator<String> createDoubleValidator(double min, double max) {
		return new BlankValidator<String>("Invalid value") {
			@Override
			public boolean isValid(String value) {
				if (value == null) {
					return false;
				}
				Double doubleValue = null;
				try {
					doubleValue = Double.valueOf(value);
				} catch (NumberFormatException e) {
					return false;
				}
				if (doubleValue == null) {
					return false;
				}
				return ((min <= doubleValue) && (doubleValue <= max));
			}
		};
	}

	private BlankValidator<String> createStringValidator() {
		return new BlankValidator<String>("Invalid value") {
			@Override
			public boolean isValid(String value) {
				return ((value != null) && (value != ""));
			}
		};
	}

	private ValueChangeHandler<String> createLatLngValueChangeHandler(MaterialTextBox box) {
		return event -> {
			if (box.validate(true)) {
				this.validMap.replace(box, true);
				this.tryEnableConfirm();
				if (this.latitudeBox.validate(true) && this.longitudeBox.validate(true)) {
					double lat = Double.parseDouble(this.latitudeBox.getValue());
					double lng = Double.parseDouble(this.longitudeBox.getValue());
					this.mapWidget.setCenter(LatLng.newInstance(lat, lng));
					this.marker.setPosition(LatLng.newInstance(lat, lng));
				}
			} else {
				this.validMap.replace(box, false);
				this.confirmButton.setEnabled(false);
			}
		};
	}

	private ValueChangeHandler<String> createStringValueChangeHandler(MaterialTextBox box) {
		return event -> {
			if (box.validate(true)) {
				this.validMap.replace(box, true);
				this.tryEnableConfirm();
			} else {
				this.validMap.replace(box, false);
				this.confirmButton.setEnabled(false);
			}
		};
	}

	private void tryEnableConfirm() {
		if (!this.validMap.containsValue(false)) {
			this.confirmButton.setEnabled(true);
		}
	}

	private void initMarker() {
		LatLng position = LatLng.newInstance(52.0, 13.0);
		this.latitudeBox.setValue("52.0");
		this.longitudeBox.setValue("13.0");
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		markerOpt.setMap(this.mapWidget);
		this.marker = Marker.newInstance(markerOpt);
		this.marker.setDraggable(true);
		this.marker.addDragEndHandler(event -> {
			this.latitudeBox.setValue(this.marker.getPosition().getLatitude() + "");
			this.longitudeBox.setValue(this.marker.getPosition().getLongitude() + "");
		});
		this.mapWidget.addDblClickHandler(event -> {
			this.latitudeBox.setValue(event.getMouseEvent().getLatLng().getLatitude() + "");
			this.longitudeBox.setValue(event.getMouseEvent().getLatLng().getLongitude() + "");
			this.marker.setPosition(LatLng.newInstance(event.getMouseEvent().getLatLng().getLatitude(),
					event.getMouseEvent().getLatLng().getLongitude()));
		});
	}

	private void initAutoComplete() {
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoOptions.setTypes(AutocompleteType.GEOCODE);

		Autocomplete autoComplete = Autocomplete.newInstance(this.placeBox.getElement(), autoOptions);
		autoComplete.addPlaceChangeHandler(event -> {
			if ((autoComplete.getPlace() != null) && (autoComplete.getPlace().getGeometry() != null)) {
				LatLngBounds bounds = autoComplete.getPlace().getGeometry().getViewPort();
				this.mapWidget.fitBounds(bounds);
			}
		});
	}

	private void initMap() {
		this.mapOptions = MapOptions.newInstance();
		this.mapOptions.setMinZoom(2);
		this.mapOptions.setMaxZoom(18);
		this.mapOptions.setDraggable(true);
		this.mapOptions.setScaleControl(true);
		this.mapOptions.setStreetViewControl(false);
		this.mapOptions.setMapTypeControl(false);
		this.mapOptions.setScrollWheel(true);
		this.mapOptions.setPanControl(false);
		this.mapOptions.setZoomControl(true);
		this.mapOptions.setDisableDoubleClickZoom(true);
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

	public void filterUnits(Integer measurandId) {
		this.unitList.clear();
		this.unitList.setSelectedIndex(0);
		this.unitMap.entrySet().stream().filter(entry -> entry.getValue().getMeasurandId() == measurandId)
		.forEach(entry -> this.unitList.addItem(entry.getKey().toString(), entry.getValue().getDisplayName()));
	}

	public void open() {
		this.modal.open();
	}

	public void setMeasurands(Map<Integer, Measurand> measurands) {
		this.measurandList.clear();
		this.measurandList.setSelectedIndex(0);
		measurands.entrySet().forEach(entry -> this.measurandList
				.addItem(entry.getKey().toString(), entry.getValue().getDisplayName()));
	}

	public void setUnits(Map<Integer, Unit> units) {
		this.unitMap = units;
		this.filterUnits(Integer.valueOf(this.measurandList.getValue()));
	}

	public void setLicences(Map<Integer, License> licenses) {
		this.licenseList.clear();
		this.licenseList.setSelectedIndex(0);
		licenses.entrySet().forEach(entry -> this.licenseList.addItem(entry.getKey().toString(),
				entry.getValue().getFullName()));
	}

}
