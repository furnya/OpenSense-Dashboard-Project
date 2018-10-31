package com.opensense.dashboard.client.model;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.safehtml.shared.SafeUri;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.presenter.DataPanelPagePresenter;
import com.opensense.dashboard.client.presenter.IDataPanelPagePresenter;
import com.opensense.dashboard.client.view.DataPanelPageView;
import com.opensense.dashboard.client.view.IDataPanelPageView;

public enum DataPanelPage {
	HOME {
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
	},
	SEARCH{
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
	},
	MAP{
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
	},
	VISUALISATIONS{
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
	},
	PREFERENCES{
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
	},
	LOGIN{
		@Override public String displayName() {return null;}
		@Override public Class<? extends IDataPanelPageView> getViewClass() {return null;}
		@Override public Class<? extends IDataPanelPagePresenter> getPresenterClass() {return null;}
		@Override public DataPanelPagePresenter createPresenterInstance(HandlerManager eventBus, AppController appController,IDataPanelPageView view) {return null;}
		@Override public DataPanelPageView createViewInstance() {return null;}
		@Override public SafeUri iconImagePath() {return null;}
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
	 * @return the icon imagePath
	 */
	public abstract SafeUri iconImagePath();
}
