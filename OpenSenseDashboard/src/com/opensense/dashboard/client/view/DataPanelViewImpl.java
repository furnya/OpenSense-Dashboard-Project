package com.opensense.dashboard.client.view;

import java.util.Iterator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.presenter.DataPanelPresenter;

public class DataPanelViewImpl extends Composite implements DataPanelView, HasWidgets {
	
	@UiTemplate("DataPanelView.ui.xml")
	interface DataPanelViewUiBinder extends UiBinder<Widget, DataPanelViewImpl> {
	}

	private static DataPanelViewUiBinder uiBinder = GWT.create(DataPanelViewUiBinder.class);
	private Presenter presenter;
	
	public DataPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPresenter(DataPanelPresenter dataPanelPresenter) {
		// TODO Auto-generated method stub
		
	}
}
