package com.opensense.dashboard.client.presenter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.view.DataPanelPageView;
import com.opensense.dashboard.client.view.DataPanelView;

/**
 * Controls the navigation and knows which datapanelPage is active
 * @author carlr
 *
 */
public class DataPanelPresenter implements IPresenter, DataPanelView.Presenter{

	private final HandlerManager eventBus;
	private final AppController appController;
	private final DataPanelView view;

	private static final Logger LOGGER = Logger.getLogger(DataPanelPresenter.class.getName());

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
		container.add(this.view.asWidget());
	}

	public void navigateTo(final DataPanelPage page, final Map<ParamType, String> parameters, final List<Integer> ids) {
		Runnable openPageRunnable = null;
		if (this.activeDataPanelPagePresenter != null) {
			this.activeDataPanelPagePresenter.onPageLeave();
		}
		try {
			// Creating the view of the new page if the user hasn't used this page yet
			if (this.pageViews.get(page) == null) {
				this.pageViews.put(page, page.createViewInstance());
			}

			// Creating the presenter of the new page and assigning the view.
			this.activeDataPanelPagePresenter = page.createPresenterInstance(this.eventBus, this.appController, this.pageViews.get(page));

			if(this.view != null) {
				this.view.getContentContainer().clear();
				this.view.showLoader();
			}

			openPageRunnable = () -> {
				this.activeDataPanelPagePresenter.onPageReturn();

				// Firing the presenter of the new page.
				this.activeDataPanelPagePresenter.go(this.view.getContentContainer());

				//If parameters are not empty give them to the active presenter
				if((parameters != null) && !parameters.isEmpty()) {
					this.activeDataPanelPagePresenter.handleParamters(parameters);
				}

				//If ids are not empty give them to the active presenter
				if((ids != null) && !ids.isEmpty()) {
					this.activeDataPanelPagePresenter.handleIds(ids);
				}

				this.view.hideLoader();
			};
			// Initializing the new page if needed. This will happen only when
			// using the page for the first time. The runnable is needed to wait until all view elements are initialized
			this.activeDataPanelPagePresenter.initIfNeeded(openPageRunnable);

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error while navigating to page " + page, e);
			if(this.view != null) {
				this.view.getContentContainer().clear();
				this.view.hideLoader();
			}
		}
	}

	public DataPanelPagePresenter getActiveDataPanelPagePresenter() {
		return this.activeDataPanelPagePresenter;
	}


	public void updateFavoriteList() {
		if(this.activeDataPanelPagePresenter instanceof ListsPresenter) {
			ListsPresenter listsPresenter = (ListsPresenter) this.activeDataPanelPagePresenter;
			listsPresenter.updateFavoriteList();
		}
		if(this.activeDataPanelPagePresenter instanceof MapPresenter) {
			MapPresenter mapPresenter = (MapPresenter) this.activeDataPanelPagePresenter;
			mapPresenter.updateFavoriteList();
		}
		if(this.activeDataPanelPagePresenter instanceof VisualisationsPresenter) {
			VisualisationsPresenter visualisationsPresenter = (VisualisationsPresenter) this.activeDataPanelPagePresenter;
			visualisationsPresenter.updateFavoriteList();
		}
	}

	public void onUserLoggedIn() {
		if(this.activeDataPanelPagePresenter instanceof HomePresenter) {
			HomePresenter homePresenter = (HomePresenter) this.activeDataPanelPagePresenter;
			homePresenter.onUserLoggedIn();
		}
		if(this.activeDataPanelPagePresenter instanceof ListsPresenter) {
			ListsPresenter listsPresenter = (ListsPresenter) this.activeDataPanelPagePresenter;
			listsPresenter.onUserLoggedIn();
		}
		if(this.activeDataPanelPagePresenter instanceof MapPresenter) {
			MapPresenter mapPresenter = (MapPresenter) this.activeDataPanelPagePresenter;
			mapPresenter.onUserLoggedIn();
		}
		if(this.activeDataPanelPagePresenter instanceof VisualisationsPresenter) {
			VisualisationsPresenter visualisationsPresenter = (VisualisationsPresenter) this.activeDataPanelPagePresenter;
			visualisationsPresenter.onUserLoggedIn();
		}
	}

	public void onUserLoggedOut() {
		if(this.activeDataPanelPagePresenter instanceof HomePresenter) {
			HomePresenter homePresenter = (HomePresenter) this.activeDataPanelPagePresenter;
			homePresenter.onUserLoggedOut();
		}
		if(this.activeDataPanelPagePresenter instanceof ListsPresenter) {
			ListsPresenter listsPresenter = (ListsPresenter) this.activeDataPanelPagePresenter;
			listsPresenter.onUserLoggedOut();
		}
		if(this.activeDataPanelPagePresenter instanceof MapPresenter) {
			MapPresenter mapPresenter = (MapPresenter) this.activeDataPanelPagePresenter;
			mapPresenter.onUserLoggedOut();
		}
		if(this.activeDataPanelPagePresenter instanceof VisualisationsPresenter) {
			VisualisationsPresenter visualisationsPresenter = (VisualisationsPresenter) this.activeDataPanelPagePresenter;
			visualisationsPresenter.onUserLoggedOut();
		}
	}
}
