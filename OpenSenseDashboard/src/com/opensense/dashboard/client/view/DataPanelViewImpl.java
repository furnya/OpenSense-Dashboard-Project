package com.opensense.dashboard.client.view;

import java.util.Iterator;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.LoaderSize;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialSpinner;

public class DataPanelViewImpl extends Composite implements DataPanelView, HasWidgets {
	
	@UiTemplate("DataPanelView.ui.xml")
	interface DataPanelViewUiBinder extends UiBinder<Widget, DataPanelViewImpl> {
	}

	private static DataPanelViewUiBinder uiBinder = GWT.create(DataPanelViewUiBinder.class);
	
	@SuppressWarnings("unused")
	private Presenter presenter;
	
	@UiField
	HasText heading;

	@UiField
	HasWidgets content;
	
	@UiField
	Div loadingContainer;
	
	public DataPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		buildLoader();
	}

	private void buildLoader() {
		MaterialPreLoader materialPreLoader = new MaterialPreLoader(); 
		materialPreLoader.setSize(LoaderSize.MEDIUM);
		MaterialSpinner spinner = new MaterialSpinner();
		materialPreLoader.add(spinner);
		loadingContainer.add(materialPreLoader);
	}

	@Override
	public void add(Widget w) {
		content.add(w);
	}

	@Override
	public void clear() {
		content.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return content.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return content.remove(w);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public HasWidgets getContentContainer() {
		return content;
	}
	
	@Override
	public void setHeading(String heading) {
		this.heading.setText(heading);
	}
	
	@Override
	public void showLoader() {
		loadingContainer.getElement().getStyle().clearDisplay();
	}
	
	@Override
	public void hideLoader() {
		loadingContainer.getElement().getStyle().setDisplay(Display.NONE);
	}
	
}
