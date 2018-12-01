package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
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
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.Pager;
import com.opensense.dashboard.client.utils.SensorItemCard;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.ValuePreview;

import gwt.material.design.client.base.validator.RegExValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialPreLoader;
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
	Div noDataIndicator;
	
	@UiField
	Div dataContent;
	
	@UiField
	MaterialNavBar navBarSearch;
	
	@UiField
	Input searchInput;
	
	@UiField
	MaterialButton searchButton;
	
	@UiField
	MaterialButton showOnMapButton;
	
	@UiField
	MaterialButton showVisualizationsButton;
	
	@UiField
	MaterialButton addToListButton;
	
	@UiField
	MaterialDropDown listDropDown;
	
	@UiField
	MaterialTextBox minAccuracy;
	
	@UiField
	MaterialTextBox maxAccuracy;
	
	@UiField
	MaterialListBox measurandList;
	
	@UiField
	MaterialTextBox maxSensors;
	
	@UiField
	MaterialPreLoader spinner;
	
	@UiField
	MaterialButton selectAllButton;
	
	@UiField(provided = true)
	Pager pagerTop = new Pager(this);
	
    @UiField(provided = true)
    Pager pagerBottom = new Pager(this);
	
	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;
	
	private Autocomplete autoComplete;
	
	private static final String AUTO_COMPLETE = "autocomplete";
	
	private List<Integer> unselectedSensors = new ArrayList<>();
	private List<Integer> selectedSensors = new ArrayList<>();
	
	private Map<Integer, Sensor> sensors = new HashMap<>();
	
	private LinkedList<Integer> shownSensorIds = new LinkedList<>();
	private Map<Integer, SensorItemCard> sensorViews = new HashMap<>();
	private int maxSensorsOnPage = 20;
	private int sensorPage = 0;
	
	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		showNoDataIndicator(false);
		showDataContainer(false);
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoOptions.setTypes(AutocompleteType.GEOCODE);
		autoComplete = Autocomplete.newInstance(searchInput.getElement(), autoOptions);
		buildValidators();
		addToListButton.add(new Image(GUIImageBundle.INSTANCE.listIconSvg().getSafeUri().asString()));
	}
	
	@UiHandler("searchButton")
	public void onSearchButtonClicked(ClickEvent e) {
		if(minAccuracy.validate() && maxAccuracy.validate() && maxSensors.validate() && searchButton.isEnabled()) {
			clearSensorData();
			showDataContainer(true);
			showLoadingIndicator();
			presenter.buildSensorRequestAndSend();
		}
	}
	
	@UiHandler("showOnMapButton")
	public void onShowOnMapButtonClicked(ClickEvent e) {
		if(!selectedSensors.isEmpty()) {
			presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, selectedSensors));
		}
	}
	
	@UiHandler("showVisualizationsButton")
	public void onShowVisualizationsButtonClicked(ClickEvent e) {
		if(!selectedSensors.isEmpty()) {
			presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, selectedSensors));
		}
	}
	
	@UiHandler("selectAllButton")
	public void onSelectAllButtonClicked(ClickEvent e) {
		if(Languages.selectAllSensors().equals(selectAllButton.getText())) {
			for (int i = unselectedSensors.size() - 1; i >= 0; i --) {
				sensorViews.get(unselectedSensors.get(i)).setActive(true);
				selectedSensors.add(unselectedSensors.get(i));
				unselectedSensors.remove(i);
			}
			selectAllButton.setText(Languages.deselectAllSensors());
		}else {
			for (int i = selectedSensors.size() - 1; i >= 0; i --) {
				sensorViews.get(selectedSensors.get(i)).setActive(false);
				unselectedSensors.add(selectedSensors.get(i));
				selectedSensors.remove(i);
			}
			selectAllButton.setText(Languages.selectAllSensors());
		}
		onSelectedSensorsChanged();
	}
	
	private void onShownSensorsChanged() {
		selectAllButton.setEnabled(!shownSensorIds.isEmpty());
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
	public void showSensorData(final List<Sensor> sensors) {
		clearSensorData();
		sensors.forEach(sensor -> {
			final SensorItemCard card = new SensorItemCard();
			final Integer sensorId = sensor.getSensorId();
			this.sensors.put(sensorId, sensor);
			card.setHeader(sensor.getMeasurand().getDisplayName() + "   -   " + Languages.sensorId() + sensorId);
			unselectedSensors.add(sensorId);
			card.setIcon(getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
			card.setRating(sensor.getAccuracy()); //TODO:
			card.addContentValue("Höhe über Grund", sensor.getAltitudeAboveGround()+"m");
			card.addContentValue("Herkunft", sensor.getAttributionText());
			card.addValueChangeHandler(event -> {
				if(event.getValue()) {
					unselectedSensors.remove(sensorId);
					selectedSensors.add(sensorId);
				}else {
					unselectedSensors.add(sensorId);
					selectedSensors.remove(sensorId);
				}
				onSelectedSensorsChanged();
			});
			shownSensorIds.add(sensorId); //can order the list before adding
			sensorViews.put(sensorId, card);
		});
		if(sensorViews.isEmpty()) {
			noDataIndicator.getElement().getStyle().clearDisplay();
		}
		pagination();
		onShownSensorsChanged();
		hideLoadingIndicator();
	}
	
	private void onSelectedSensorsChanged() {
		if(!selectedSensors.isEmpty()) {
			showOnMapButton.setEnabled(true);
			showVisualizationsButton.setEnabled(true);
			addToListButton.setEnabled(true);
		}else {
			showOnMapButton.setEnabled(false);
			showVisualizationsButton.setEnabled(false);
			addToListButton.setEnabled(false);
		}
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

	private void buildValidators() {
		final RegExValidator rgx = new RegExValidator("^(?:[0-9]|0[0-9]|10|)$");
		minAccuracy.addValidator(rgx);
		minAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
		minAccuracy.addValueChangeHandler(event -> {
			if(!minAccuracy.validate(true)) {
				searchButton.setEnabled(false);
			}else if(maxAccuracy.validate(true) && maxSensors.validate(true)) {
				searchButton.setEnabled(true);
			}
		});
		maxAccuracy.addValidator(rgx);
		maxAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
		maxAccuracy.addValueChangeHandler(event -> {
			if(!maxAccuracy.validate(true)) {
				searchButton.setEnabled(false);
			}else if(minAccuracy.validate(true) && maxSensors.validate(true)) {
				searchButton.setEnabled(true);
			}
		});
		final RegExValidator digitrgx = new RegExValidator("^1?\\d{0,4}$");
		maxSensors.addValidator(digitrgx);
		maxSensors.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
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
		hideLoadingIndicator();
		noDataIndicator.getElement().getStyle().clearDisplay();
	}

	@Override
	public void selectMeasurandId(String value) {
		this.measurandList.setSelectedIndex(this.measurandList.getIndex(value));
	}
	
	public void showLoadingIndicator() {
		spinner.getElement().getStyle().setDisplay(Display.BLOCK);
	}
	
	public void hideLoadingIndicator() {
		spinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	@Override
	public void setPlaceString(String value) {
		searchInput.setValue(value);
	}

	@Override
	public void pagination() {
		sensorContainer.clear();
		
		List<Integer> idsOnPage = new ArrayList<>();
		for(int i = sensorPage * maxSensorsOnPage; i < shownSensorIds.size() && i < (sensorPage + 1) * maxSensorsOnPage; i++){
			sensorContainer.add(sensorViews.get(shownSensorIds.get(i)));
			idsOnPage.add(shownSensorIds.get(i));
		}
		idsOnPage.forEach(id -> sensorViews.get(id).showPreviewContentSpinner(true));
		presenter.getSensorValuePreviewAndShow(idsOnPage);
		
		pagerTop.setPage(Languages.setPageNumber(sensorPage, maxSensorsOnPage, shownSensorIds.size()));
		pagerTop.setForwardsEnabled(sensorPage + 1 < ((int) Math.ceil((double) shownSensorIds.size() / (double) maxSensorsOnPage)));
		pagerTop.setBackwardsEnabled(sensorPage > 0);
		
		pagerBottom.setPage(Languages.setPageNumber(sensorPage, maxSensorsOnPage, shownSensorIds.size()));
		pagerBottom.setForwardsEnabled(sensorPage + 1 < ((int) Math.ceil((double) shownSensorIds.size() / (double) maxSensorsOnPage)));
		pagerBottom.setBackwardsEnabled(sensorPage > 0);
	}
	
	public void clearPager() {
		pagerTop.setPage(Languages.setPageNumber(0, 0, 0));
		pagerTop.setForwardsEnabled(false);
		pagerTop.setBackwardsEnabled(false);
		pagerBottom.setPage(Languages.setPageNumber(0, 0, 0));
		pagerBottom.setForwardsEnabled(false);
		pagerBottom.setBackwardsEnabled(false);
	}

	@Override
	public List<Integer> getShownSensorIds() {
		return shownSensorIds;
	}

	@Override
	public double getMaxSensorsOnPage() {
		return maxSensorsOnPage;
	}

	@Override
	public int getSensorPage() {
		return sensorPage;
	}

	@Override
	public void setSensorPage(int page) {
		this.sensorPage = page;
	}
	
	@Override
	public void clearSensorData() {
		showNoDataIndicator(false);
		this.sensors.clear();
		sensorContainer.clear();
		unselectedSensors.clear();
		selectedSensors.clear();
		sensorViews.clear();
		shownSensorIds.clear();
		clearPager();
		sensorPage = 0;
		onShownSensorsChanged();
		onSelectedSensorsChanged();
	}

	@Override
	public void showDataContainer(boolean show) {
		if(show) {
			dataContent.getElement().getStyle().clearDisplay();
		}else {
			dataContent.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	public void showNoDataIndicator(boolean show) {
		if(show) {
			noDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			noDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	public void showSensorValuePreview(Map<Integer, ValuePreview> preview) {
		if(!shownSensorIds.isEmpty()) {
			preview.entrySet().forEach(entry -> {
				if(shownSensorIds.contains(entry.getKey())){
					if(sensorViews.containsKey(entry.getKey()) && entry.getValue()!=null) {
						sensorViews.get(entry.getKey()).setValuePreviewConent(
						Languages.getDate(entry.getValue().getMinValue().getTimestamp()) + " - " + entry.getValue().getMinValue().getNumberValue() + " " + sensors.get(entry.getKey()).getUnit().getDisplayName(),
						Languages.getDate(entry.getValue().getMaxValue().getTimestamp()) + " - " + entry.getValue().getMaxValue().getNumberValue() + " " + sensors.get(entry.getKey()).getUnit().getDisplayName());
					}else {
						sensorViews.get(entry.getKey()).setValuePreviewConent(Languages.noValuePreviewData(), Languages.noValuePreviewData());
					}
				}
			});
		}
	}
}
