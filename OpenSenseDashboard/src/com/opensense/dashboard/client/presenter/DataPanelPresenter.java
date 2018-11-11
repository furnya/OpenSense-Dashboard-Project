package com.opensense.dashboard.client.presenter;

import java.util.EnumMap;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.view.DataPanelPageView;
import com.opensense.dashboard.client.view.DataPanelView;

public class DataPanelPresenter implements IPresenter, DataPanelView.Presenter{
	
	private final HandlerManager eventBus;
	private final AppController appController;
	private final DataPanelView view;

	private DataPanelPagePresenter activeDataPanelPagePresenter = null;

	private final EnumMap<DataPanelPage, DataPanelPageView> pageViews = new EnumMap<>(DataPanelPage.class);

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

	public void navigateTo(DataPanelPage page, Map<ParamType, String> parameters) {
		if (activeDataPanelPagePresenter != null) {
			activeDataPanelPagePresenter.onPageLeave();
		}
		try {
			// Creating the view of the new page if the user hasn't used this page yet
			if (pageViews.get(page) == null)
				pageViews.put(page, page.createViewInstance());

			// Creating the presenter of the new page and assigning the view.
			activeDataPanelPagePresenter = page.createPresenterInstance(eventBus, appController, pageViews.get(page));
			// Initializing the new page if needed. This will happen only when
			// using the page for the first time.
			activeDataPanelPagePresenter.initIfNeeded();
			
			view.setHeading(page.displayName());
			
			activeDataPanelPagePresenter.onPageReturn();
			
			// Firing the presenter of the new page.
			activeDataPanelPagePresenter.go(view.getContentContainer());
			
			//If parameters are not empty give them to the active presenter
			if(parameters != null && !parameters.isEmpty()) {
				parameters.entrySet().forEach(entry -> GWT.log(entry.getKey().name() + " " + entry.getValue()));
				activeDataPanelPagePresenter.handleParamters(parameters);
			}
			
		} catch (Exception e) {
			GWT.log("Error while navigating to page " + page + ".", e);
			view.getContentContainer().clear();
			view.setHeading(Languages.errorDataPanelPageLoading());
		}
	}

}
