package com.opensense.dashboard.client.utils;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
		//TODO: validate
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
