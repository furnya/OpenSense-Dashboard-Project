package com.opensense.dashboard.client.view;

import java.util.Iterator;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.Spinner;

public class DataPanelViewImpl extends Composite implements DataPanelView, HasWidgets {

	@UiTemplate("DataPanelView.ui.xml")
	interface DataPanelViewUiBinder extends UiBinder<Widget, DataPanelViewImpl> {
	}

	private static DataPanelViewUiBinder uiBinder = GWT.create(DataPanelViewUiBinder.class);

	@SuppressWarnings("unused")
	private Presenter presenter;

	@UiField
	HasWidgets content;

	@UiField
	Spinner spinner;

	@UiField
	Div spinnerContainer;

	public DataPanelViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		this.content.add(w);
	}

	@Override
	public void clear() {
		this.content.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return this.content.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return this.content.remove(w);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasWidgets getContentContainer() {
		return this.content;
	}

	@Override
	public void showLoader() {
		this.spinner.setDisplay(Display.BLOCK);
		this.spinnerContainer.getElement().getStyle().clearDisplay();
	}

	@Override
	public void hideLoader() {
		this.spinner.setDisplay(Display.NONE);
		this.spinnerContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

}
