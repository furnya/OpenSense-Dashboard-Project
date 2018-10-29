package com.opensense.dashboard.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.presenter.DataPanelPresenter;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.presenter.NavigationPanelPresenter;
import com.opensense.dashboard.client.presenter.TestPresenter;
import com.opensense.dashboard.client.view.DataPanelViewImpl;
import com.opensense.dashboard.client.view.NavigationPanelView;
import com.opensense.dashboard.client.view.NavigationPanelViewImpl;
import com.opensense.dashboard.client.view.TestView;
import com.opensense.dashboard.client.view.TestViewImpl;

public class AppController implements IPresenter, ValueChangeHandler<String> {
	
	private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());
	 
	/**
	 * Final reference to this object so the appController's functions can be
	 * used in event handlers.
	 */
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
	 private TestPresenter testPresenter = null;
	 private DataPanelPresenter dataPanelPresenter = null;
	 private NavigationPanelPresenter navigationPanelPresenter = null;
	 
	 /**
	  * Views
	  */
	 private TestView testView = null;
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
	}
	
	private void bindHandler() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
	}
}
