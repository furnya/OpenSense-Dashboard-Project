package com.opensense.dashboard.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.presenter.DataPanelPresenter;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.presenter.NavigationPanelPresenter;
import com.opensense.dashboard.client.view.DataPanelViewImpl;
import com.opensense.dashboard.client.view.NavigationPanelViewImpl;

public class AppController implements IPresenter, ValueChangeHandler<String> {
	
	private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());
	 
	/**
	 * Final reference to this object so the appController's functions can be used in event handlers
	 */
	@SuppressWarnings("unused")
	private final AppController instance = this;
	 
	 /**
	  * Handler manager mechanism for passing events and registering to be
	  * notified of some of these events.
	  */
	 private final HandlerManager eventBus;
	 
	 /**
	  * GUI Image Bundle
	  */
	 protected GUIImageBundle gui = GWT.create(GUIImageBundle.class);
	 
	 /**
	  *  Presenter
	  */
	 private DataPanelPresenter dataPanelPresenter = null;
	 private NavigationPanelPresenter navigationPanelPresenter = null;
	 
	 /**
	  * Views
	  */
	 private DataPanelViewImpl dataPanelView = null;
	 private NavigationPanelViewImpl navigationPanelView = null;
	 
	 /**
	 * Constructs the application controller (main presenter).
	 * 
	 * @param eventBus
	 */
	 public AppController(final HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindHandler();
		go(RootPanel.get());
		handleStart();
	}
	
	private void handleStart() {
		if(History.getToken() != null && !History.getToken().isEmpty()) {
			eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.valueOf(History.getToken().toUpperCase())));
		}else {
			eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.HOME));
		}
	}

	private void bindHandler() {
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(OpenDataPanelPageEvent.TYPE, event -> {
			History.newItem(event.getDataPanelPage().name(), false);
			dataPanelPresenter.navigateTo(event.getDataPanelPage());
			navigationPanelPresenter.setActiveDataPanelPage(event.getDataPanelPage());
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		HasWidgets controlPanel = RootPanel.get("control-panel");
		controlPanel.clear();
		if (navigationPanelView == null) {
			navigationPanelView = new NavigationPanelViewImpl();
		}
		navigationPanelPresenter = new NavigationPanelPresenter(eventBus, this, navigationPanelView);
		navigationPanelPresenter.go(controlPanel);
		
		HasWidgets dataPanelContainer = RootPanel.get("data-panel");
		dataPanelContainer.clear();
		if (dataPanelView == null)
			dataPanelView = new DataPanelViewImpl();
		dataPanelPresenter = new DataPanelPresenter(eventBus, this, dataPanelView);
		dataPanelPresenter.go(dataPanelContainer);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (dataPanelPresenter == null) {
			LOGGER.log(Level.WARNING, "NAVIGATION: The dataPanelPresenter is null.");
			return;
		}
		eventBus.fireEvent(new OpenDataPanelPageEvent(DataPanelPage.valueOf(event.getValue().toUpperCase())));
	}
}
