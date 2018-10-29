package com.opensense.dashboard.client.presenter;

import java.util.EnumMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.DataPanelView;

public class DataPanelPresenter implements IPresenter, DataPanelView.Presenter{
	
	private final HandlerManager eventBus;
	private final AppController appController;
	private final DataPanelView view;

	private DataPanelPagePresenter activeDataPanelPagePresenter = null;

//	private final EnumMap<DataPanelPage, DataPanelPageView> pageViews = new EnumMap<>(DataPanelPage.class);

	public DataPanelPresenter(HandlerManager eventBus, AppController appController, DataPanelView view) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

}
