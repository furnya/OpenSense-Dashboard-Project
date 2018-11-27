package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.UserView;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

import gwt.material.design.client.ui.MaterialToast;

public class UserPresenter extends DataPanelPagePresenter implements IPresenter, UserView.Presenter{
	
	private final UserView view;
	
	private static final Logger LOGGER = Logger.getLogger(SearchPresenter.class.getName());
	
	public UserPresenter(HandlerManager eventBus, AppController appController, UserView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public UserView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
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
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleIds(List<Integer> ids) {
		// TODO Auto-generated method stub
	}


	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		view.initView();
		runnable.run();
	}
	
	@Override
	public void sendLoginRequest(String username, String password) {
		Request loginRequest = new Request(ResultType.TOKEN);
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		GeneralService.Util.getInstance().userLoginRequest(loginRequest, new DefaultAsyncCallback<String>(result -> {
			MaterialToast.fireToast(result==null? "Login failed" : "Logged in successfully");
			GWT.log(result);
			if(result!=null) Cookies.setCookie("access_token", result);
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the login.");
		}, false));
	}
}