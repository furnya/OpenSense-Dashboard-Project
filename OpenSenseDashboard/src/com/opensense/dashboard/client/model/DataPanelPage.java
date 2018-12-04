package com.opensense.dashboard.client.model;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.safehtml.shared.SafeUri;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.presenter.DataPanelPagePresenter;
import com.opensense.dashboard.client.presenter.HomePresenter;
import com.opensense.dashboard.client.presenter.IDataPanelPagePresenter;
import com.opensense.dashboard.client.presenter.ListsPresenter;
import com.opensense.dashboard.client.presenter.MapPresenter;
import com.opensense.dashboard.client.presenter.SearchPresenter;
import com.opensense.dashboard.client.presenter.UserPresenter;
import com.opensense.dashboard.client.presenter.VisualisationsPresenter;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.view.DataPanelPageView;
import com.opensense.dashboard.client.view.HomeView;
import com.opensense.dashboard.client.view.HomeViewImpl;
import com.opensense.dashboard.client.view.IDataPanelPageView;
import com.opensense.dashboard.client.view.ListsView;
import com.opensense.dashboard.client.view.ListsViewImpl;
import com.opensense.dashboard.client.view.MapView;
import com.opensense.dashboard.client.view.MapViewImpl;
import com.opensense.dashboard.client.view.SearchView;
import com.opensense.dashboard.client.view.SearchViewImpl;
import com.opensense.dashboard.client.view.UserView;
import com.opensense.dashboard.client.view.UserViewImpl;
import com.opensense.dashboard.client.view.VisualisationsView;
import com.opensense.dashboard.client.view.VisualisationsViewImpl;

public enum DataPanelPage {
	HOME {
		@Override public String displayName() {return Languages.home();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return HomeView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return HomePresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new HomePresenter(eventBus, appController, (HomeView) view);}
		@Override public DataPanelPageView createViewInstance() {return new HomeViewImpl();}
		@Override public boolean logoutButton() {return false;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.homeIconSvg().getSafeUri();}
	},
	SEARCH{
		@Override public String displayName() {return Languages.search();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return SearchView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return SearchPresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new SearchPresenter(eventBus, appController, (SearchView) view);}
		@Override public DataPanelPageView createViewInstance() {return new SearchViewImpl();}
		@Override public boolean logoutButton() {return false;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri();}
	},
	MAP{
		@Override public String displayName() {return Languages.map();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return MapView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return MapPresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new MapPresenter(eventBus, appController, (MapView) view);}
		@Override public DataPanelPageView createViewInstance() {return new MapViewImpl();}
		@Override public boolean logoutButton() {return false;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.mapIconSvg().getSafeUri();}
	},
	VISUALISATIONS{
		@Override public String displayName() {return Languages.graphics();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return VisualisationsView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return VisualisationsPresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new VisualisationsPresenter(eventBus, appController, (VisualisationsView) view);}
		@Override public DataPanelPageView createViewInstance() {return new VisualisationsViewImpl();}
		@Override public boolean logoutButton() {return false;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.diagramIconSvg().getSafeUri();}
	},
	LISTS{
		@Override public String displayName() {return Languages.list();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return ListsView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return ListsPresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new ListsPresenter(eventBus, appController, (ListsView) view);}
		@Override public DataPanelPageView createViewInstance() {return new ListsViewImpl();}
		@Override public boolean logoutButton() {return false;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.listIconSvg().getSafeUri();}
	},
	USER{
		@Override public String displayName() {return Languages.user();}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return UserView.class;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return UserPresenter.class;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return new UserPresenter(eventBus, appController, (UserView) view);} 
		@Override public DataPanelPageView createViewInstance() {return new UserViewImpl();}
		@Override public boolean logoutButton() {return true;}
		@Override public SafeUri iconImagePath() {return GUIImageBundle.INSTANCE.userIconSvg().getSafeUri();}
	};
	
	/**
 * 		@return the display name of the DataPanelPage
	 */
	public abstract String displayName();
		
	/**
	 *  @return the ViewClass of the DataPanelPage
	 */
	public abstract Class<? extends IDataPanelPageView> getViewClass();

	/**
	 * @return the type of the presenter that is used to present the represented DataPanelPage
	 */
	public abstract Class<? extends IDataPanelPagePresenter> getPresenterClass();
	
	
	/**
	 * Creates a new Presenter for the DataPanelPage
	 * @param EventBus 
	 * @param View 
	 * @return Presenter of the DataPanelPage
	 */
	public abstract DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController, IDataPanelPageView view);
	
	/**
	 * creates a new Instance of the according view
	 * @return DataPanelPageView
	 */
	public abstract DataPanelPageView createViewInstance();
	
	/**
	 * If the page button is in the navigation at the bottom this should be true
	 * @return
	 */
	public abstract boolean logoutButton();
	
	/**
	 * @return the icon imagePath
	 */
	public abstract SafeUri iconImagePath();
}
