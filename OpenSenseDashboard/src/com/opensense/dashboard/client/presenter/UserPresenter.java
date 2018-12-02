package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
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
	
	private static final byte[] GWT_DES_KEY = new byte[]{
			(byte)5,(byte)6,(byte)1,(byte)1,(byte)0,(byte)1,(byte)8,(byte)7,
			(byte)3,(byte)9,(byte)2,(byte)2,(byte)3,(byte)6,(byte)1,(byte)0,
			(byte)9,(byte)2,(byte)1,(byte)1,(byte)5,(byte)7,(byte)1,(byte)1,};
	
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
		String encryptedPassword = getEnrypedPassword(password);
		if(encryptedPassword.isEmpty()) {
			LOGGER.log(Level.WARNING, "Password empty");
			return;
		}
		AuthenticationService.Util.getInstance().userLoginRequest(username, encryptedPassword, new DefaultAsyncCallback<ActionResult>(result -> {
			MaterialToast.fireToast(result!=null? result.getErrorMessage() : "Null");
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the login.");
		}, false));
	}
	
	private String getEnrypedPassword(String password) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(GWT_DES_KEY);
		String encrypt = "";
		try {
			encrypt = cipher.encrypt(password);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failure encrypting password.", e); 
		}
		return encrypt;
	}
	
	@Override
	public boolean isUserLoggedIn() {
		return !appController.isGuest();
	}
	public void userLoggedIn() {
	}
}