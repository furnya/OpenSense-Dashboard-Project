package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
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
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.presenter.ListManagerPresenter;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.License;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.ResultType;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.Unit;

import gwt.material.design.client.base.validator.AbstractValidator;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.base.validator.DecimalMaxValidator;
import gwt.material.design.client.base.validator.DecimalMinValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class AddSensorModal extends Composite{

	@UiTemplate("AddSensorModal.ui.xml")
	interface AddSensorModalUiBinder extends UiBinder<Widget, AddSensorModal> {
	}

	private static AddSensorModalUiBinder uiBinder = GWT.create(AddSensorModalUiBinder.class);
	
	private static final Logger LOGGER = Logger.getLogger(AddSensorModal.class.getName());
	
	private Map<Integer, Measurand> measurandMap;
	private Map<Integer, Unit> unitMap;
	private Map<Integer, License> licenseMap;
	private Map<MaterialTextBox, Boolean> validMap = new HashMap<>();
	
	private MapOptions mapOptions;
	private MapWidget mapWidget;
	private Autocomplete autoComplete;
	private Marker marker;
	
	@UiField
	Div map;
	
	@UiField
	Input placeBox;
	
	private ListManager listManager;
	
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
	
	public AddSensorModal(ListManager listManager) {
		this.listManager = listManager;
		initWidget(uiBinder.createAndBindUi(this));
		requestMeasurands();
		this.measurandList.addValueChangeHandler(event -> {
			this.filterUnits(Integer.valueOf(event.getValue()));
		});
		requestLicenses();
		this.initMap();
		this.initAutoComplete();
		this.initMarker();
		this.addValidators();
		this.initValidMap();
	}
	
	private void initValidMap() {
		this.validMap.put(this.latitudeBox,true);
		this.validMap.put(this.longitudeBox,true);
		this.validMap.put(this.altitudeBox,false);
		this.validMap.put(this.directionVerticalBox,false);
		this.validMap.put(this.directionHorizontalBox,false);
		this.validMap.put(this.sensorModelBox,false);
		this.validMap.put(this.accuracyBox,false);
		this.validMap.put(this.attributionTextBox,false);
		this.validMap.put(this.attributionUrlBox,false);
	}
	
	private void addValidators() {
		this.latitudeBox.addValidator(createDoubleValidator(-90, 90));
		this.latitudeBox.addValueChangeHandler(createLatLngValueChangeHandler(this.latitudeBox));
		this.longitudeBox.addValidator(createDoubleValidator(-180, 180));
		this.longitudeBox.addValueChangeHandler(createLatLngValueChangeHandler(this.longitudeBox));
		BlankValidator<String> vString = createStringValidator();
		this.sensorModelBox.addValidator(vString);
		this.sensorModelBox.addValueChangeHandler(createStringValueChangeHandler(this.sensorModelBox));
		this.attributionTextBox.addValidator(vString);
		this.attributionTextBox.addValueChangeHandler(createStringValueChangeHandler(this.attributionTextBox));
		this.attributionUrlBox.addValidator(vString);
		this.attributionUrlBox.addValueChangeHandler(createStringValueChangeHandler(this.attributionUrlBox));
		this.altitudeBox.addValidator(createDoubleValidator(0, Double.POSITIVE_INFINITY));
		this.altitudeBox.addValueChangeHandler(createStringValueChangeHandler(this.altitudeBox));
		this.directionVerticalBox.addValidator(createDoubleValidator(-360, 360));
		this.directionVerticalBox.addValueChangeHandler(createStringValueChangeHandler(this.directionVerticalBox));
		this.directionHorizontalBox.addValidator(createDoubleValidator(-360, 360));
		this.directionHorizontalBox.addValueChangeHandler(createStringValueChangeHandler(this.directionHorizontalBox));
		this.accuracyBox.addValidator(createDoubleValidator(0, 10));
		this.accuracyBox.addValueChangeHandler(createStringValueChangeHandler(this.accuracyBox));
	}
	
	private BlankValidator<String> createDoubleValidator(double min, double max){
		return new BlankValidator<String>("Invalid value") {
			@Override
			public boolean isValid(String value) {
				if(value==null) return false;
				Double doubleValue = null;
				try {
					doubleValue = Double.valueOf(value);
				}catch(NumberFormatException e) {
					return false;
				}
				if(doubleValue==null) return false;
				return (min<=doubleValue && doubleValue<=max);
			}
		};
	}
	
	private BlankValidator<String> createStringValidator(){
		return new BlankValidator<String>("Invalid value") {
			@Override
			public boolean isValid(String value) {
				return (value!=null && value!="");
			}
		};
	}
	
	private ValueChangeHandler<String> createLatLngValueChangeHandler(MaterialTextBox box){
		return event -> {
			if(box.validate(true)) {
				this.validMap.replace(box,true);
				this.tryEnableConfirm();
				if(this.latitudeBox.validate(true) && this.longitudeBox.validate(true)) {
					double lat = Double.parseDouble(this.latitudeBox.getValue());
					double lng = Double.parseDouble(this.longitudeBox.getValue());
					this.mapWidget.setCenter(LatLng.newInstance(lat, lng));
					this.marker.setPosition(LatLng.newInstance(lat, lng));
				}
			}else {
				this.validMap.replace(box,false);
				this.confirmButton.setEnabled(false);
			}
		};
	}
	
	private ValueChangeHandler<String> createStringValueChangeHandler(MaterialTextBox box){
		return event -> {
			if(box.validate(true)) {
				this.validMap.replace(box,true);
				this.tryEnableConfirm();
			}else {
				this.validMap.replace(box,false);
				this.confirmButton.setEnabled(false);
			}
		};
	}

	private void tryEnableConfirm() {
		if(!this.validMap.containsValue(false)) {
			this.confirmButton.setEnabled(true);
		}
	}

	private void initMarker() {
		LatLng position = LatLng.newInstance(52.0,13.0);
		this.latitudeBox.setValue("52.0");
		this.longitudeBox.setValue("13.0");
		MarkerOptions markerOpt = MarkerOptions.newInstance();
		markerOpt.setPosition(position);
		markerOpt.setMap(this.mapWidget);
		this.marker = Marker.newInstance(markerOpt);
		this.marker.setDraggable(true);
		this.marker.addDragEndHandler(event -> {
			this.latitudeBox.setValue(this.marker.getPosition().getLatitude()+"");
			this.longitudeBox.setValue(this.marker.getPosition().getLongitude()+"");
		});
		this.mapWidget.addDblClickHandler(event -> {
			this.latitudeBox.setValue(event.getMouseEvent().getLatLng().getLatitude()+"");
			this.longitudeBox.setValue(event.getMouseEvent().getLatLng().getLongitude()+"");
			this.marker.setPosition(LatLng.newInstance(event.getMouseEvent().getLatLng().getLatitude(), event.getMouseEvent().getLatLng().getLongitude()));
		});
	}
	
	private void initAutoComplete() {
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoOptions.setTypes(AutocompleteType.GEOCODE);
		this.autoComplete = Autocomplete.newInstance(this.placeBox.getElement(), autoOptions);
		this.autoComplete.addPlaceChangeHandler(event -> {
			if((this.autoComplete.getPlace() != null) && (this.autoComplete.getPlace().getGeometry() != null)) {
				LatLngBounds bounds = this.autoComplete.getPlace().getGeometry().getViewPort();
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
	
	private void requestMeasurands() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.MEASURAND, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getMeasurands() != null)) {
				this.measurandMap = result.getMeasurands();
				this.measurandList.clear();
				this.measurandList.setSelectedIndex(0);
				measurandMap.entrySet().forEach(entry -> this.measurandList.addItem(entry.getKey().toString(), entry.getValue().getDisplayName()));
				requestUnits();
			}else {
				LOGGER.log(Level.WARNING, "Failure requesting the measurands.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the measurands.");
		}, false));
	}
	
	private void requestUnits() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.UNIT, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getUnits() != null)) {
				this.unitMap = result.getUnits();
				this.filterUnits(Integer.valueOf(measurandList.getValue()));
			}else {
				LOGGER.log(Level.WARNING, "Failure requesting the units.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the units.");
		}, false));
	}
	
	private void requestLicenses() {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.LICENSE, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getLicenses() != null)) {
				this.licenseMap = result.getLicenses();
				this.licenseList.clear();
				this.licenseList.setSelectedIndex(0);
				licenseMap.entrySet().forEach(entry -> this.licenseList.addItem(entry.getKey().toString(), entry.getValue().getFullName()));
			}else {
				LOGGER.log(Level.WARNING, "Failure requesting the units.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the units.");
		}, false));
	}
	
	public void filterUnits(Integer measurandId) {
		this.unitList.clear();
		this.unitList.setSelectedIndex(0);
		unitMap.entrySet().stream().filter(entry -> entry.getValue().getMeasurandId()==measurandId).forEach(entry -> this.unitList.addItem(entry.getKey().toString(), entry.getValue().getDisplayName()));
	}
	
	public void open() {
		modal.open();
	}
	
	@UiHandler("closeButton")
	public void onCloseButtonClicked(ClickEvent e) {
		modal.close();
	}
	
	@UiHandler("confirmButton")
	public void onConfirmButtonClicked(ClickEvent e) {
		CreateSensorRequest request = new CreateSensorRequest();
		request.setAccuracy(Double.valueOf(accuracyBox.getValue()));
		request.setAltitudeAboveGround(Double.valueOf(altitudeBox.getValue()));
		request.setAttributionText(attributionTextBox.getValue());
		request.setAttributionURL(attributionUrlBox.getValue());
		request.setDirectionHorizontal(Double.valueOf(directionHorizontalBox.getValue()));
		request.setDirectionVertical(Double.valueOf(directionVerticalBox.getValue()));
		request.setLatitude(Double.valueOf(latitudeBox.getValue()));
		request.setLongitude(Double.valueOf(longitudeBox.getValue()));
		request.setLicenseId(Integer.valueOf(licenseList.getValue()));
		request.setMeasurandId(Integer.valueOf(measurandList.getValue()));
		request.setSensorModel(sensorModelBox.getValue());
		request.setUnitId(Integer.valueOf(unitList.getValue()));
		GeneralService.Util.getInstance().createSensor(request, new DefaultAsyncCallback<ActionResult>(result -> {
			MaterialToast.fireToast(result.getErrorMessage());
			if(result.getActionResultType()==ActionResultType.SUCCESSFUL) {
				modal.close();
				this.listManager.updateLists();
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure creating the sensor.");
		}, false));
	}
}
