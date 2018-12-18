package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.view.ListsView;

public class ListsPresenter extends DataPanelPagePresenter implements IPresenter, ListsView.Presenter{

	private final ListsView view;

	public ListsPresenter(HandlerManager eventBus, AppController appController, ListsView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	public ListsView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public void onPageReturn() {
		this.view.getListManager().setUserLoggedInAndUpdate(!this.appController.isGuest());
		this.view.setCreateListButtonEnabled(!this.appController.isGuest());
	}

	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleIds(List<Integer> ids) {
		this.view.getListManager().updateSelectedSensorsList(ids);
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		this.view.initView(runnable, !this.appController.isGuest());
		this.view.setCreateListButtonEnabled(!this.appController.isGuest());
	}

	public void updateFavoriteList() {
		this.view.getListManager().updateFavoriteList();
	}

	public void onUserLoggedIn() {
		this.view.getListManager().onUserLoggedIn();
		this.view.setCreateListButtonEnabled(true);
	}

	public void onUserLoggedOut() {
		this.view.getListManager().onUserLoggedOut();
		this.view.setCreateListButtonEnabled(false);
	}
}
