package com.opensense.dashboard.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.services.GeneralStartService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;

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
	 * Constructs the application controller (main presenter).
	 * 
	 * @param eventBus
	 */
	 public AppController(final HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindHandler();
		go(RootPanel.get());
		GeneralStartService.Util.getInstance().getData("Das ist der String", new DefaultAsyncCallback<Integer>(result -> GWT.log(result+"")));
	}
	
	private void bindHandler() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
	}
}
