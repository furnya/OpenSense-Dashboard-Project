package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.SearchView;

public class SearchPresenter extends DataPanelPagePresenter implements IPresenter, SearchView.Presenter{
	
	private final SearchView view;
	
	public SearchPresenter(HandlerManager eventBus, AppController appController, SearchView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		getSensorDataAndDisplay();
	}

	@Override
	public void onPageReturn() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initView() {
		view.initView();
	}
	
	private void getSensorDataAndDisplay() {
//		GeneralService.Util.getInstance().getSensorDataFromString("GIVE ME AS MUCH AS YOU CAN", new DefaultAsyncCallback<TestClass>(result ->  {
//			view.showSensorData(result.getS());
//		}));
	}
}
