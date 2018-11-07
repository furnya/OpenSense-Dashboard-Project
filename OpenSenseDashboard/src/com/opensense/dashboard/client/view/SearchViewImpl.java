package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

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
	MaterialListBox unitList;
//	@UiField
//	MaterialComboBox<String> unitList;
	
	
	@UiField
	MaterialTextBox maxSensors;
	
	
	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;
	
	private Autocomplete autoComplete;
	
	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		autoComplete = Autocomplete.newInstance(searchInput.getElement(), autoOptions);
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
	public void showSensorData(List<String> result) {
	}
	
	@Override
	public LatLngBounds getBounds() {
		if(autoComplete.getPlace() != null && autoComplete.getPlace().getGeometry() != null)
			return autoComplete.getPlace().getGeometry().getViewPort();
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
	public void setUnitList(Map<Integer, String> units) {
//		units.entrySet().forEach(entry -> unitList.addItem(entry.getValue(), entry.getKey()));
		units.values().forEach(unitList::addItem); 
	}
	
	@Override
	public List<String> getUnits() {
		List<String> list = new ArrayList<>();
		list.add(unitList.getSelectedIndex()+"");
		return list;
//		return unitList.getSelectedValues();
	}
	
	@Override
	public String getMaxSensors() {
		return maxSensors.getText();
	}
	
	@Override
	public void setMaxSensors(Integer maxSensors) {
		this.maxSensors.setText(maxSensors.toString());
	}
}
