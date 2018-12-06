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
import gwt.material.design.client.ui.MaterialLink;
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

	@UiField
	Pager pagerTop;

	@UiField
	Pager pagerBottom;

	@UiField
	MaterialLink favoriteButton;

	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;

	private Autocomplete autoComplete;

	private static final String AUTO_COMPLETE = "autocomplete";

	private List<Integer> unselectedSensors = new ArrayList<>();
	private List<Integer> selectedSensors = new ArrayList<>();

	private Map<Integer, Sensor> sensors = new HashMap<>();

	private LinkedList<Integer> shownSensorIds = new LinkedList<>();
	private Map<Integer, SensorItemCard> sensorViews = new HashMap<>();

	public SearchViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.initPager();
		this.showNoDataIndicator(false);
		this.showDataContainer(false);
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoOptions.setTypes(AutocompleteType.GEOCODE);
		this.autoComplete = Autocomplete.newInstance(this.searchInput.getElement(), autoOptions);
		this.buildValidators();
		this.addToListButton.add(new Image(GUIImageBundle.INSTANCE.listIconSvg().getSafeUri().asString()));
	}

	@UiHandler("searchButton")
	public void onSearchButtonClicked(ClickEvent e) {
		if(this.minAccuracy.validate() && this.maxAccuracy.validate() && this.maxSensors.validate() && this.searchButton.isEnabled()) {
			this.clearSensorData();
			this.showDataContainer(true);
			this.showLoadingIndicator();
			this.presenter.buildSensorRequestAndSend();
		}
	}

	@UiHandler("showOnMapButton")
	public void onShowOnMapButtonClicked(ClickEvent e) {
		if(!this.selectedSensors.isEmpty()) {
			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, this.selectedSensors));
		}
	}

	@UiHandler("showVisualizationsButton")
	public void onShowVisualizationsButtonClicked(ClickEvent e) {
		if(!this.selectedSensors.isEmpty()) {
			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, this.selectedSensors));
		}
	}

	@UiHandler("selectAllButton")
	public void onSelectAllButtonClicked(ClickEvent e) {
		if(Languages.selectAllSensors().equals(this.selectAllButton.getText())) {
			for (int i = this.unselectedSensors.size() - 1; i >= 0; i --) {
				this.sensorViews.get(this.unselectedSensors.get(i)).setActive(true);
				this.selectedSensors.add(this.unselectedSensors.get(i));
				this.unselectedSensors.remove(i);
			}
			this.selectAllButton.setText(Languages.deselectAllSensors());
		}else {
			for (int i = this.selectedSensors.size() - 1; i >= 0; i --) {
				this.sensorViews.get(this.selectedSensors.get(i)).setActive(false);
				this.unselectedSensors.add(this.selectedSensors.get(i));
				this.selectedSensors.remove(i);
			}
			this.selectAllButton.setText(Languages.selectAllSensors());
		}
		this.onSelectedSensorsChanged();
	}

	@UiHandler("favoriteButton")
	public void onFavoriteButtonClicked(ClickEvent e) {
		this.selectedSensors.forEach(id -> this.presenter.addSensorToFavoriteList(id));
	}

	private void onShownSensorsChanged() {
		this.selectAllButton.setEnabled(!this.shownSensorIds.isEmpty());
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
		this.clearSensorData();
		sensors.forEach(sensor -> {
			final SensorItemCard card = new SensorItemCard();
			final Integer sensorId = sensor.getSensorId();
			this.sensors.put(sensorId, sensor);
			card.setHeader(sensor.getMeasurand().getDisplayName() + "   -   " + Languages.sensorId() + sensorId);
			this.unselectedSensors.add(sensorId);
			card.setIcon(this.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
			card.setRating(sensor.getAccuracy()); //TODO:
			card.addContentValue(Languages.altitudeAboveGround(), sensor.getAltitudeAboveGround()+"m");
			card.addContentValue(Languages.origin(), sensor.getAttributionText());
			card.addValueChangeHandler(event -> {
				if(event.getValue()) {
					this.unselectedSensors.remove(sensorId);
					this.selectedSensors.add(sensorId);
				}else {
					this.unselectedSensors.add(sensorId);
					this.selectedSensors.remove(sensorId);
				}
				this.onSelectedSensorsChanged();
			});
			card.addFavButtonClickHandler(event -> this.presenter.addSensorToFavoriteList(sensorId));
			this.shownSensorIds.add(sensorId); //can order the list before adding
			this.sensorViews.put(sensorId, card);
		});
		if(this.sensorViews.isEmpty()) {
			this.noDataIndicator.getElement().getStyle().clearDisplay();
		}
		this.pagerTop.update(this.shownSensorIds.size(), true);
		this.onShownSensorsChanged();
		this.hideLoadingIndicator();
	}

	private void onSelectedSensorsChanged() {
		if(!this.selectedSensors.isEmpty()) {
			this.showOnMapButton.setEnabled(true);
			this.showVisualizationsButton.setEnabled(true);
			this.addToListButton.setEnabled(true);
		}else {
			this.showOnMapButton.setEnabled(false);
			this.showVisualizationsButton.setEnabled(false);
			this.addToListButton.setEnabled(false);
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
		this.minAccuracy.addValidator(rgx);
		this.minAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
		this.minAccuracy.addValueChangeHandler(event -> {
			if(!this.minAccuracy.validate(true)) {
				this.searchButton.setEnabled(false);
			}else if(this.maxAccuracy.validate(true) && this.maxSensors.validate(true)) {
				this.searchButton.setEnabled(true);
			}
		});
		this.maxAccuracy.addValidator(rgx);
		this.maxAccuracy.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
		this.maxAccuracy.addValueChangeHandler(event -> {
			if(!this.maxAccuracy.validate(true)) {
				this.searchButton.setEnabled(false);
			}else if(this.minAccuracy.validate(true) && this.maxSensors.validate(true)) {
				this.searchButton.setEnabled(true);
			}
		});
		final RegExValidator digitrgx = new RegExValidator("^1?\\d{0,4}$");
		this.maxSensors.addValidator(digitrgx);
		this.maxSensors.getChildrenList().get(0).getElement().setAttribute(AUTO_COMPLETE, "off");
		this.maxSensors.addValueChangeHandler(event -> {
			if(!this.maxSensors.validate(true)) {
				this.searchButton.setEnabled(false);
			}else if(this.minAccuracy.validate(true) && this.maxAccuracy.validate(true)) {
				this.searchButton.setEnabled(true);
			}
		});
	}

	@Override
	public LatLngBounds getBounds() {
		if((this.autoComplete.getPlace() != null) && (this.autoComplete.getPlace().getGeometry() != null)) {
			return this.autoComplete.getPlace().getGeometry().getViewPort();
		}
		return null;
	}

	@Override
	public String getMinAccuracy() {
		return this.minAccuracy.getText();
	}

	@Override
	public String getMaxAccuracy() {
		return this.maxAccuracy.getText();
	}

	@Override
	public void setMeasurandsList(Map<Integer, String> measurands) {
		this.measurandList.clear();
		this.measurandList.addItem("", Languages.all());
		this.measurandList.setSelectedIndex(0);
		measurands.entrySet().forEach(entry -> this.measurandList.addItem(entry.getKey().toString(), entry.getValue()));
	}

	@Override
	public String getMeasurandId() {
		return this.measurandList.getSelectedValue();
	}

	@Override
	public String getMaxSensors() {
		return this.maxSensors.getText();
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
		this.hideLoadingIndicator();
		this.noDataIndicator.getElement().getStyle().clearDisplay();
	}

	@Override
	public void selectMeasurandId(String value) {
		this.measurandList.setSelectedIndex(this.measurandList.getIndex(value));
	}

	@Override
	public void showLoadingIndicator() {
		this.spinner.getElement().getStyle().clearDisplay();
	}

	public void hideLoadingIndicator() {
		this.spinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	@Override
	public void setPlaceString(String value) {
		this.searchInput.setValue(value);
	}

	@Override
	public List<Integer> getShownSensorIds() {
		return this.shownSensorIds;
	}

	@Override
	public void clearSensorData() {
		this.showNoDataIndicator(false);
		this.sensors.clear();
		this.sensorContainer.clear();
		this.unselectedSensors.clear();
		this.selectedSensors.clear();
		this.sensorViews.clear();
		this.shownSensorIds.clear();
		this.pagerTop.clearPager();
		this.pagerBottom.clearPager();
		this.onShownSensorsChanged();
		this.onSelectedSensorsChanged();
	}

	@Override
	public void showDataContainer(boolean show) {
		if(show) {
			this.dataContent.getElement().getStyle().clearDisplay();
		}else {
			this.dataContent.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void showNoDataIndicator(boolean show) {
		if(show) {
			this.noDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			this.noDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@Override
	public void showSensorValuePreview(Map<Integer, ValuePreview> preview) {
		if(!this.shownSensorIds.isEmpty()) {
			preview.entrySet().forEach(entry -> {
				if(this.shownSensorIds.contains(entry.getKey())){
					if(this.sensorViews.containsKey(entry.getKey()) && (entry.getValue()!=null)) {
						this.sensorViews.get(entry.getKey()).setValuePreviewConent(
								Languages.getDate(entry.getValue().getMinValue().getTimestamp()) + " - " + entry.getValue().getMinValue().getNumberValue() + " " + this.sensors.get(entry.getKey()).getUnit().getDisplayName(),
								Languages.getDate(entry.getValue().getMaxValue().getTimestamp()) + " - " + entry.getValue().getMaxValue().getNumberValue() + " " + this.sensors.get(entry.getKey()).getUnit().getDisplayName());
					}else {
						this.sensorViews.get(entry.getKey()).setValuePreviewConent(Languages.noValuePreviewData(), Languages.noValuePreviewData());
					}
				}
			});
		}
	}

	private void initPager() {
		this.pagerTop.addBackwardsButtonClickHandler(event -> this.pagerTop.onBackwardsButtonClicked(this.shownSensorIds.size()));
		this.pagerTop.addBackwardsStepByStepClickHandler(event -> this.pagerTop.onBackwardsStepByStepButtonClicked(this.shownSensorIds.size()));
		this.pagerTop.addForwardsStepByStepClickHandler(event -> this.pagerTop.onForwardsStepByStepButtonClicked(this.shownSensorIds.size()));
		this.pagerTop.addForwardsButtonClickHandler(event -> this.pagerTop.onForwardsButtonClicked(this.shownSensorIds.size()));

		this.pagerTop.addPaginationEventHandler(event -> this.pagination(event.getPage(), event.getMaxObjectsOnPage()));

		this.pagerBottom.addBackwardsButtonClickHandler(event -> this.pagerBottom.onBackwardsButtonClicked(this.shownSensorIds.size()));
		this.pagerBottom.addBackwardsStepByStepClickHandler(event -> this.pagerBottom.onBackwardsStepByStepButtonClicked(this.shownSensorIds.size()));
		this.pagerBottom.addForwardsStepByStepClickHandler(event -> this.pagerBottom.onForwardsStepByStepButtonClicked(this.shownSensorIds.size()));
		this.pagerBottom.addForwardsButtonClickHandler(event -> this.pagerBottom.onForwardsButtonClicked(this.shownSensorIds.size()));

		this.pagerBottom.addPaginationEventHandler(event -> this.pagination(event.getPage(), event.getMaxObjectsOnPage()));

		this.pagerTop.setDependentPager(this.pagerBottom);
		this.pagerBottom.setDependentPager(this.pagerTop);
	}

	private void pagination(int page, int maxObjectsOnPage) {
		this.sensorContainer.clear();
		List<Integer> idsOnPage = new ArrayList<>();
		for(int i = page * maxObjectsOnPage; (i < this.shownSensorIds.size()) && (i < ((page + 1) * maxObjectsOnPage)); i++){
			SensorItemCard card = this.sensorViews.get(this.shownSensorIds.get(i));
			card.showPreviewContentSpinner(true);
			this.sensorContainer.add(card);
		}
		this.presenter.getSensorValuePreviewAndShow(idsOnPage);
	}
}
