package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.UserView;
import com.opensense.dashboard.shared.ActionResult;

import gwt.material.design.client.ui.MaterialToast;

public class UserPresenter extends DataPanelPagePresenter implements IPresenter, UserView.Presenter{
	
	private final UserView view;
	
	private static final Logger LOGGER = Logger.getLogger(DataPanelPagePresenter.class.getName());
	
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
//		String salt = BCrypt.gensalt();
//        String hash1 = BCrypt.hashpw(password, salt);
		AuthenticationService.Util.getInstance().userLoginRequest(username, password, new DefaultAsyncCallback<ActionResult>(result -> {
			MaterialToast.fireToast(result!=null? result.getErrorMessage() : "Null");
//			if(result!=null) Cookies.setCookie("access_token", result);
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the login.");
		}, false));
	}
}