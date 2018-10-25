package com.opensense.dashboard.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.presenter.TestPresenter;
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
	 
	 /**
	  * Views
	  */
	 private TestView testView;
	 
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
		if (testView == null) {
			testView = new TestViewImpl();
		}
		testPresenter = new TestPresenter(eventBus, this, testView);
		testPresenter.go(controlPanel);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
	}
}
