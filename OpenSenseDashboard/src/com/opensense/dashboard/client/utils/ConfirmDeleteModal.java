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
import com.google.gwt.event.dom.client.ClickHandler;
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

public class ConfirmDeleteModal extends Composite{

	@UiTemplate("ConfirmDeleteModal.ui.xml")
	interface ConfirmDeleteModalUiBinder extends UiBinder<Widget, ConfirmDeleteModal> {
	}

	private static ConfirmDeleteModalUiBinder uiBinder = GWT.create(ConfirmDeleteModalUiBinder.class);
	
	private static final Logger LOGGER = Logger.getLogger(ConfirmDeleteModal.class.getName());
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialButton cancelButton;
	
	@UiField
	MaterialButton confirmButton;
	
	ClickHandler handler;
	
	public ConfirmDeleteModal(ClickHandler handler) {
		this.handler = handler;
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	public void open() {
		modal.open();
	}
	
	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent e) {
		modal.close();
	}
	
	@UiHandler("confirmButton")
	public void onConfirmButtonClicked(ClickEvent e) {
		modal.close();
		this.handler.onClick(e);
	}
}
