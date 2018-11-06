package com.opensense.dashboard.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
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

import gwt.material.design.client.ui.MaterialNavBar;

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
	Button searchButton;
	
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
		presenter.buildRequestAndSend();
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
	
	public LatLngBounds getBounds() {
		if(autoComplete.getPlace() != null) {
			return autoComplete.getBounds();
		}
		return null;
	}
}
