package com.opensense.dashboard.client.view;

import java.util.Iterator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class DataPanelViewImpl extends Composite implements DataPanelView, HasWidgets {
	
	@UiTemplate("DataPanelView.ui.xml")
	interface DataPanelViewUiBinder extends UiBinder<Widget, DataPanelViewImpl> {
	}

	private static DataPanelViewUiBinder uiBinder = GWT.create(DataPanelViewUiBinder.class);
	private Presenter presenter;
	
	@UiField
	HasText heading;

	@UiField
	HasWidgets content;
	
	public DataPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
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
}
