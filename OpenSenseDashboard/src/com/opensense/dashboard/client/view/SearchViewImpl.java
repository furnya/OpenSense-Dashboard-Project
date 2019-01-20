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
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.maps.client.placeslib.AutocompleteType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.AddSensorsToFavoriteListEvent;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.MeasurandIconHelper;
import com.opensense.dashboard.client.utils.Pager;
import com.opensense.dashboard.client.utils.SensorItemCard;
import com.opensense.dashboard.client.utils.Spinner;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.UserList;

import gwt.material.design.client.base.validator.RegExValidator;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTooltip;

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
	Spinner spinner;

	@UiField
	MaterialButton selectAllButton;

	@UiField
	Pager pagerTop;

	@UiField
	Pager pagerBottom;

	@UiField
	MaterialLink favoriteButton;

	@UiField
	MaterialCheckBox onlySensorsWithValueBox;

	@UiField
	Spinner listDropDownSpinner;
	
	@UiField
	MaterialTooltip selectAllTooltip;

	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;

	private Autocomplete autoComplete;

	private static final String AUTO_COMPLETE = "autocomplete";

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

		this.container.addDomHandler(event -> this.hideListDropDown(), MouseWheelEvent.getType());

		Window.addResizeHandler(event -> this.hideListDropDown());

		this.listDropDown.addMouseWheelHandler(MouseWheelEvent::stopPropagation);
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

	@UiHandler("addToListButton")
	public void onAddToListButtonClicked(ClickEvent e) {
		this.showListDropDownSpinner(true);
		this.addToListButton.setEnabled(false);
		this.clearListsDropDown();
		this.presenter.getListsAndShow();
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
		this.selectAllSensors(Languages.selectAllSensors().equals(this.selectAllButton.getText()));
	}

	@UiHandler("favoriteButton")
	public void onFavoriteButtonClicked(ClickEvent e) {
		this.presenter.getEventBus().fireEvent(new AddSensorsToFavoriteListEvent(this.selectedSensors));
	}

	@UiHandler("onlySensorsWithValueBox")
	public void onOnlySensorsWithValueBoxValueChanged(ValueChangeEvent<Boolean> e) {
		this.shownSensorIds.clear();
		this.selectAllSensors(false);
		if(e.getValue()) {
			this.sensors.values().stream().filter(sensor -> sensor.getValuePreview() != null).forEach(sensor -> this.shownSensorIds.add(sensor.getSensorId()));
		}else {
			this.sensors.keySet().forEach(this.shownSensorIds::add);
		}
		this.pagerTop.setPage(0);
		this.pagerTop.update(this.shownSensorIds.size(), true);
	}

	private void selectAllSensors(boolean select) {
		if(select) {
			for (int i = this.shownSensorIds.size() - 1; i >= 0; i --) {
				this.sensorViews.get(this.shownSensorIds.get(i)).setActive(true);
				this.selectedSensors.add(this.shownSensorIds.get(i));
			}
			this.selectAllButton.setText(Languages.deselectAllSensors());
			this.selectAllTooltip.setText(Languages.deselectAllTooltip());
		}else {
			for (int i = this.selectedSensors.size() - 1; i >= 0; i --) {
				this.sensorViews.get(this.selectedSensors.get(i)).setActive(false);
				this.selectedSensors.remove(i);
			}
			this.selectAllButton.setText(Languages.selectAllSensors());
			this.selectAllTooltip.setText(Languages.selectAllTooltip());
		}
		this.onSelectedSensorsChanged();
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
			card.setIcon(MeasurandIconHelper.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
			card.setRating(sensor.getAccuracy());
			card.addContentValue(Languages.altitudeAboveGround(), sensor.getAltitudeAboveGround()+"m");
			card.addContentValue(Languages.origin(), sensor.getAttributionText());

			if((sensor.getValuePreview() != null)) {
				card.addContentValue(Languages.firstValue(), Languages.getDate(sensor.getValuePreview().getMinValue().getTimestamp()) + " - " + sensor.getValuePreview().getMinValue().getNumberValue() + " " + sensor.getUnit().getDisplayName());
				card.addContentValue(Languages.lastValue(),	Languages.getDate(sensor.getValuePreview().getMaxValue().getTimestamp()) + " - " + sensor.getValuePreview().getMaxValue().getNumberValue() + " " + sensor.getUnit().getDisplayName());
			}else {
				card.addContentValue(Languages.values(), Languages.noValuePreviewData());
			}

			card.addActiveChangeHandler(event -> {
				if(event.isActive()) {
					this.selectedSensors.add(sensorId);
				}else {
					this.selectedSensors.remove(sensorId);
				}
				this.onSelectedSensorsChanged();
			});
			final List<Integer> id = new ArrayList<>();
			id.add(sensorId);
			card.addFavButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new AddSensorsToFavoriteListEvent(id)));
			if(!this.onlySensorsWithValueBox.getValue() || (this.onlySensorsWithValueBox.getValue() && (sensor.getValuePreview() != null))) {
				this.shownSensorIds.add(sensorId);
			}
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

	private void buildValidators() {
		final RegExValidator rgx = new RegExValidator("^(?:[0-9]|0[0-9]|10|)$",Languages.errorMessageAccuracy());

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
		final RegExValidator digitrgx = new RegExValidator("^1?\\d{0,4}$",Languages.errorMessageMaxSens());
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
	public void setMeasurandsList(Map<Integer, Measurand> measurands) {
		this.measurandList.clear();
		this.measurandList.addItem("", Languages.all());
		this.measurandList.setSelectedIndex(0);
		measurands.entrySet().forEach(entry -> this.measurandList.addItem(entry.getKey().toString(), entry.getValue().getDisplayName()));
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
		this.selectAllButton.setText(Languages.selectAllSensors());
		this.sensors.clear();
		this.sensorContainer.clear();
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
		for(int i = page * maxObjectsOnPage; (i < this.shownSensorIds.size()) && (i < ((page + 1) * maxObjectsOnPage)); i++){
			SensorItemCard card = this.sensorViews.get(this.shownSensorIds.get(i));
			this.sensorContainer.add(card);
		}
	}

	public void showListDropDownSpinner(boolean show) {
		if(show) {
			this.listDropDownSpinner.getElement().getStyle().setDisplay(Display.BLOCK);
		} else {
			this.listDropDownSpinner.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@Override
	public void showUserListsInDropDown(List<UserList> userLists) {
		this.clearListsDropDown();
		this.addToListButton.setEnabled(true);
		this.showListDropDownSpinner(false);
		userLists.forEach(userList -> {
			MaterialLink link = new MaterialLink();
			link.setText(userList.getListName());
			link.addClickHandler(event -> this.presenter.addSelectedSensorsToUserList(userList.getListId(), this.selectedSensors));
			this.listDropDown.add(link);
		});
	}

	private void clearListsDropDown() {
		for (int i = this.listDropDown.getChildren().size() - 1; i > 0; i--) {
			this.listDropDown.remove(i);
		}
	}

	private native void blurElement(Element elem) /*-{
		elem.click();
	}-*/;

	@Override
	public void hideListDropDown() {
		if(this.listDropDown.getElement().getAttribute("style").contains("display: block")){
			this.listDropDown.getElement().getStyle().setDisplay(Display.NONE);
			this.blurElement(this.container.getElement()); //blur listDropDown
		}
	}
}
