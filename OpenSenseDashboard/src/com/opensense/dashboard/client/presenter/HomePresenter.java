package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.view.HomeView;

public class HomePresenter extends DataPanelPagePresenter implements HomeView.Presenter {

	private final HomeView view;

	public HomePresenter(HandlerManager eventBus, AppController appController, HomeView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
		this.getUserName();
	}

	@Override
	public void onPageLeave() {
	}

	@Override
	public void onPageReturn() {
		this.view.setCheckboxValue();
	}

	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleIds(List<Integer> ids) {
		// TODO Auto-generated method stub
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		this.view.initView();
		runnable.run();
	}

	public void getUserName() {
		if (this.isUserLoggedIn()) {
			GeneralService.Util.getInstance()
			.getUserName(new DefaultAsyncCallback<String>(result -> this.view.setUserInfo(result)));
		} else {
			this.view.setUserInfo(Languages.guest());
		}
	}

	@Override
	public boolean isUserLoggedIn() {
		return !this.appController.isGuest();
	}

	public void onUserLoggedIn() {
		GWT.log("hier");
		GeneralService.Util.getInstance()
		.getUserName(new DefaultAsyncCallback<String>(result ->{  this.view.setUserInfo(result);}));
	}

	public void onUserLoggedOut() {
		this.view.setUserInfo(Languages.guest());
	}

}
