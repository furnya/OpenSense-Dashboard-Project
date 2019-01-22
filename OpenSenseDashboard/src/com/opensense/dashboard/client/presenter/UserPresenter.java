package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.view.UserView;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

public class UserPresenter extends DataPanelPagePresenter implements IPresenter, UserView.Presenter{

	private final UserView view;

	private static final Logger LOGGER = Logger.getLogger(DataPanelPagePresenter.class.getName());

	public UserPresenter(HandlerManager eventBus, AppController appController, UserView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	public UserView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public void onPageReturn() {
		this.view.showLoginPopup(!this.isUserLoggedIn());
	}

	@Override
	public void onPageLeave() {
		this.view.resetViewElements();
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

	@Override
	public void sendLoginRequest(String username, String password) {
		AuthenticationService.Util.getInstance().userLoginRequest(username, password, new DefaultAsyncCallback<ActionResult>(result -> {
			if((result != null) && ActionResultType.SUCCESSFUL.equals(result.getActionResultType())){
				this.appController.onUserLoggedIn(true);
			}else{
				this.view.showLoginNotValid();
			}
		},caught -> {
			this.view.showLoginNotValid();
			AppController.showError(Languages.connectionError());
			LOGGER.log(Level.WARNING, "Failure requesting the login.");
		}, false));
	}

	@Override
	public boolean isUserLoggedIn() {
		return !this.appController.isGuest();
	}

	@Override
	public void sendRegisterRequest(String username, String password, String email) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(new byte[] { -110, 121, -65, 22, -60, 61, -22, -60, 21, -122, 41, -89, -89, -68, -8, 41, -119, -51, -12, -36, 19, -8, -17, 47 });
		try {
			password = cipher.encrypt(password);
		} catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
			AppController.showError(Languages.connectionError());
			return;
		}
		AuthenticationService.Util.getInstance().userRegisterRequest(username, password, email, new DefaultAsyncCallback<ActionResult>(result -> {
			if((result != null) && ActionResultType.SUCCESSFUL.equals(result.getActionResultType())){
				AppController.showSuccess(Languages.successfullyCreatedAccout());
				this.view.resetViewElements();
			}else{
				AppController.showError(Languages.invalidParameters());
			}
		},caught -> {
			this.view.showLoginNotValid();
			AppController.showError(Languages.connectionError());
			LOGGER.log(Level.WARNING, "Failure requesting the register.");
		}, false));
	}

	@Override
	public void sendForgotPasswordRequest(String email) {
		AuthenticationService.Util.getInstance().forgotPasswordRequest(email, new DefaultAsyncCallback<ActionResult>(result -> {
			if((result != null) && ActionResultType.SUCCESSFUL.equals(result.getActionResultType())){
				AppController.showSuccess(Languages.passwordResetSent());
				this.view.resetViewElements();
			}else{
				AppController.showError(Languages.connectionError());
			}
		},caught -> {
			AppController.showError(Languages.connectionError());
			LOGGER.log(Level.WARNING, "Failure requesting the password reset.");
		}, false));
	}

	@Override
	public void logout() {
		this.appController.logout();
	}

	@Override
	public void sendChangePasswordRequest(String oldPassword, String newPassword) {
		AuthenticationService.Util.getInstance().changePassword(oldPassword, newPassword, new DefaultAsyncCallback<ActionResult>(result -> {
			if((result != null) && ActionResultType.SUCCESSFUL.equals(result.getActionResultType())){
				this.view.hideChangePasswordContainer(true);
				this.view.showSaveButtonSpinner(false);
				AppController.showSuccess(Languages.successfullyChangedPassword());
			}else{
				AppController.showError(result.getErrorMessage());
				this.view.showSaveButtonSpinner(false);
			}
		},caught -> {
			this.view.showSaveButtonSpinner(false);
			AppController.showError(Languages.connectionError());
			LOGGER.log(Level.WARNING, "Failure requesting the password change.");
		}, false));
	}



}