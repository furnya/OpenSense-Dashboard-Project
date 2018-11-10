package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.SensorItemCard;
import com.opensense.dashboard.shared.Sensor;

import gwt.material.design.client.base.validator.RegExValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialTextBox;

public class SearchViewImpl extends DataPanelPageView implements SearchView {
	
	@UiTemplate("SearchView.ui.xml")
	interface SearchViewUiBinder extends UiBinder<Widget, SearchViewImpl> {
	}
	
	@UiField
	Div container;
	
	@UiField
	Div sensorContainer;
	
	@UiField
	MaterialNavBar navBarSearch;
	
	@UiField
	Input searchInput;
	
	@UiField
	MaterialButton searchButton;
	
	@UiField
	MaterialTextBox minAccuracy;
	
	@UiField
	MaterialTextBox maxAccuracy;
	
	@UiField
	MaterialListBox measurandList;
	
	@UiField
	MaterialTextBox maxSensors;
	
	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;
	
	private Autocomplete autoComplete;
	
	private final static String AUTOCOMPLETE = "autocomplete";
	
	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoOptions.setTypes(AutocompleteType.GEOCODE);
		autoComplete = Autocomplete.newInstance(searchInput.getElement(), autoOptions);
		buildValidators();
	}
	
	@UiHandler("searchButton")
	public void onSearchButtonCLicked(ClickEvent e) {
		presenter.buildSensorRequestAndSend();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView() {
		// init UI Elements if needed
	}
	
	@Override
	public void showSensorData(List<Sensor> sensors) {
		sensors.forEach(sensor -> {
			final SensorItemCard card = new SensorItemCard();
			card.setHeader("Sensor: " + sensor.getId());
			card.setIcon(GUIImageBundle.INSTANCE.tempIconSvg().getSafeUri().asString());
			card.getContent().add(new Span("Genauigkeit " + sensor.getAccuracy()));
			card.addClickHandler(event -> {
				event.stopPropagation();
				GWT.log(card.isActive()+"");
				card.setActive(!card.isActive());
			});
			sensorContainer.add(card);
		});
	}
	
	private void buildValidators() {
		final RegExValidator rgx = new RegExValidator("^(?:[0-9]|0[0-9]|10|)$");
		minAccuracy.addValidator(rgx);
		minAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTOCOMPLETE, "off");
		minAccuracy.addValueChangeHandler(event -> {
			if(!minAccuracy.validate(true)) {
				searchButton.setEnabled(false);
			}else if(maxAccuracy.validate(true) && maxSensors.validate(true)) {
				searchButton.setEnabled(true);
			}
		});
		maxAccuracy.addValidator(rgx);
		maxAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTOCOMPLETE, "off");
		maxAccuracy.addValueChangeHandler(event -> {
			if(!maxAccuracy.validate(true)) {
				searchButton.setEnabled(false);
			}else if(minAccuracy.validate(true) && maxSensors.validate(true)) {
				searchButton.setEnabled(true);
			}
		});
		final RegExValidator digitrgx = new RegExValidator("^1?\\d{0,4}$");
		maxSensors.addValidator(digitrgx);
		maxSensors.getChildrenList().get(0).getElement().setAttribute(AUTOCOMPLETE, "off");
		maxSensors.addValueChangeHandler(event -> {
			if(!maxSensors.validate(true)) {
				searchButton.setEnabled(false);
			}else if(minAccuracy.validate(true) && maxAccuracy.validate(true)) {
				searchButton.setEnabled(true);
			}
		});
	}
	
	@Override
	public LatLngBounds getBounds() {
		if(autoComplete.getPlace() != null && autoComplete.getPlace().getGeometry() != null) {
			return autoComplete.getPlace().getGeometry().getViewPort();
		}
		return null;
	}
	
	@Override
	public String getMinAccuracy() {
		return minAccuracy.getText();
	}
	
	@Override
	public String getMaxAccuracy() {
		return maxAccuracy.getText();
	}
	
	@Override
	public void setMeasurandsList(Map<Integer, String> measurands) {
		measurandList.clear();
		measurandList.addItem("", Languages.all());
		measurandList.setSelectedIndex(0);
		measurands.entrySet().forEach(entry -> measurandList.addItem(entry.getKey().toString(), entry.getValue()));
	}
	
	@Override
	public String getMeasurandId() {
		return measurandList.getSelectedValue();
	}
	
	@Override
	public String getMaxSensors() {
		return maxSensors.getText();
	}
	
	@Override
	public void setMaxSensors(String maxSensors) {
		this.maxSensors.setValue(maxSensors, true);
	}

	@Override
	public void setMinAccuracy(String minAccuracy) {
		this.minAccuracy.setValue(minAccuracy, true);
	}
	
	@Override
	public void setMaxAccuracy(String maxAccuracy) {
		this.maxAccuracy.setValue(maxAccuracy, true);
	}

	@Override
	public boolean isSearchButtonEnabled() {
		return this.searchButton.isEnabled();
	}

	@Override
	public void showLoadSensorError() {
		//TODO:
	}
	
}
