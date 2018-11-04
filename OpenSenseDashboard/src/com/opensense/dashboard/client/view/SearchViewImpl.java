package com.opensense.dashboard.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.maps.client.placeslib.AutocompleteOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class SearchViewImpl extends DataPanelPageView implements SearchView {
	
	@UiTemplate("SearchView.ui.xml")
	interface SearchViewUiBinder extends UiBinder<Widget, SearchViewImpl> {
	}
	
	@UiField
	Div container;
	
//	@UiField
//	MaterialNavBar navBarSearch;
	
	@UiField
	Input searchInput;
	
	private static SearchViewUiBinder uiBinder = GWT.create(SearchViewUiBinder.class);

	protected Presenter presenter;
	
	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		AutocompleteOptions autoOptions = AutocompleteOptions.newInstance();
		Autocomplete auto = Autocomplete.newInstance(searchInput.getElement(), autoOptions);
//		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
//		searchTextBox = new SuggestBox(oracle);
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
		result.forEach(sensor -> GWT.log("YEAAH, ich habe den Sensor mit der Id "+sensor + " bekommen"));
	}
}
