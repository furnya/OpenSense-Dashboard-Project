package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.Spinner;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class UserViewImpl extends DataPanelPageView implements UserView {

	@UiTemplate("UserView.ui.xml")
	interface UserViewUiBinder extends UiBinder<Widget, UserViewImpl> {
	}

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);

	protected Presenter presenter;

	@UiField
	Div loginContainer;

	@UiField
	MaterialTextBox userName;

	@UiField
	MaterialTextBox password;

	@UiField
	MaterialTextBox passwordVerify;

	@UiField
	MaterialButton loginButton;

	@UiField
	MaterialLabel regiLabel;

	@UiField
	Spinner spinner;

	@UiField
	Spinner saveSpinner;

	@UiField
	MaterialTextBox email;

	@UiField
	MaterialLabel forgotPassword;

	@UiField
	MaterialLabel goBack;

	@UiField
	Div buttonPanel;

	@UiField
	Div forgotPasswordContainer;

	@UiField
	MaterialTextBox oldPassword;

	@UiField
	MaterialTextBox newPassword;

	@UiField
	MaterialTextBox verifyNewPassword;

	@UiField
	MaterialButton saveButton;

	public UserViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("forgotPassword")
	public void onForgotPasswordClicked(ClickEvent e) {
		this.resetViewElements();
		this.regiLabel.setText(Languages.login());
		this.userName.getElement().getStyle().setDisplay(Display.NONE);
		this.password.getElement().getStyle().setDisplay(Display.NONE);
		this.passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
		this.email.getElement().getStyle().setDisplay(Display.BLOCK);
		this.loginButton.setText(Languages.send());
	}

	@UiHandler("regiLabel")
	public void onRegiLabelClicked(ClickEvent e) {
		if(Languages.register().equals(this.regiLabel.getText())) {
			this.regiLabel.setText(Languages.login());
			this.loginButton.setText(Languages.register());
			this.userName.getElement().getStyle().setDisplay(Display.BLOCK);
			this.password.getElement().getStyle().setDisplay(Display.BLOCK);
			this.passwordVerify.getElement().getStyle().setDisplay(Display.BLOCK);
			this.email.getElement().getStyle().setDisplay(Display.BLOCK);
		}else {
			this.regiLabel.setText(Languages.register());
			this.loginButton.setText(Languages.login());
			this.userName.getElement().getStyle().setDisplay(Display.BLOCK);
			this.password.getElement().getStyle().setDisplay(Display.BLOCK);
			this.passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
			this.email.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@UiHandler("loginButton")
	public void onLoginButtonClicked(ClickEvent e) {
		if(Languages.register().equals(this.loginButton.getText())) {
			//TODO ask database if username exists
			if((this.userName.getValue()==null) && this.userName.getValue().isEmpty()) {
				AppController.showError(Languages.invalidUsername());
				return;
			}
			if((this.password.getValue()==null) || this.password.getValue().isEmpty() || this.passwordVerify.getValue().isEmpty() || (this.passwordVerify.getValue()==null) || (this.password.getValue()!=this.passwordVerify.getValue())) {
				AppController.showError(Languages.passwordsDontMatch());
				return;
			}
			if((this.email.getValue()==null) || !this.email.getValue().matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
				AppController.showError(Languages.invalidEmail());
				return;
			}
			this.spinner.getElement().getStyle().clearDisplay();
			this.loginButton.setEnabled(false);
			this.regiLabel.setEnabled(false);
			this.forgotPassword.setEnabled(false);
			this.presenter.sendRegisterRequest(this.userName.getValue(),this.password.getValue(),this.email.getValue());
		}else if(Languages.login().equals(this.loginButton.getText())){
			this.spinner.getElement().getStyle().clearDisplay();
			this.loginButton.setEnabled(false);
			this.regiLabel.setEnabled(false);
			this.forgotPassword.setEnabled(false);
			this.presenter.sendLoginRequest(this.userName.getText(), this.password.getText());
		}else if(Languages.send().equals(this.loginButton.getText())) {
			if((this.email.getValue()==null) || !this.email.getValue().matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
				AppController.showError(Languages.invalidEmail());
				return;
			}
			this.spinner.getElement().getStyle().clearDisplay();
			this.loginButton.setEnabled(false);
			this.regiLabel.setEnabled(false);
			this.forgotPassword.setEnabled(false);
			this.presenter.sendForgotPasswordRequest(this.email.getValue());
		}
	}

	@UiHandler("userName")
	public void onUserNameValueChange(ValueChangeEvent<String> event) {
		this.clearErrors();
	}

	@UiHandler("password")
	public void onPasswordValueChange(ValueChangeEvent<String> event) {
		this.clearErrors();
	}

	@UiHandler("logoutButton")
	public void onLogoutButtonClicked(ClickEvent event) {
		this.presenter.logout();
	}

	@UiHandler("changePassword")
	public void onChangePasswordButtonClicked(ClickEvent event) {
		this.buttonPanel.getElement().getStyle().setDisplay(Display.NONE);
		this.forgotPasswordContainer.getElement().getStyle().clearDisplay();
	}

	@UiHandler("goBack")
	public void onGoBackButtonClicked(ClickEvent event) {
		this.buttonPanel.getElement().getStyle().clearDisplay();
		this.forgotPasswordContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

	@UiHandler("saveButton")
	public void onSaveButtonClicked(ClickEvent e) {
		if((this.oldPassword.getValue()==null) && this.oldPassword.getValue().isEmpty()) {
			AppController.showError(Languages.invalidUsername());
			return;
		}
		if((this.newPassword.getValue()==null) || (this.verifyNewPassword.getValue()==null) || this.newPassword.getValue().isEmpty() || this.verifyNewPassword.getValue().isEmpty() || (this.newPassword.getValue()!=this.verifyNewPassword.getValue())) {
			AppController.showError(Languages.passwordsDontMatch());
			return;
		}
		this.showSaveButtonSpinner(true);
		this.presenter.sendChangePasswordRequest(this.oldPassword.getValue(),this.newPassword.getValue());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		this.showLoginPopup(this.presenter.isUserLoggedIn());
	}

	@Override
	public void resetViewElements() {
		this.loginButton.setEnabled(true);
		this.regiLabel.setEnabled(true);
		this.forgotPassword.setEnabled(true);
		this.regiLabel.setText(Languages.register());
		this.loginButton.setText(Languages.login());
		this.userName.getElement().getStyle().setDisplay(Display.BLOCK);
		this.password.getElement().getStyle().setDisplay(Display.BLOCK);
		this.passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
		this.email.getElement().getStyle().setDisplay(Display.NONE);
		this.password.reset();
		this.email.reset();
		this.passwordVerify.reset();
		this.userName.reset();
		this.spinner.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	@Override
	public void reEnableButtons() {
		this.loginButton.setEnabled(true);
		this.regiLabel.setEnabled(true);
		this.forgotPassword.setEnabled(true);
		this.spinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	private void clearErrors() {
		this.password.getErrorHandler().clearErrors();
		this.userName.getErrorHandler().clearErrors();
	}

	@Override
	public void showLoginNotValid() {
		this.resetViewElements();
		this.password.setError(Languages.loginValidateError());
		this.userName.setError("");
	}

	@Override
	public void showLoginPopup(boolean show) {
		if(this.presenter.isUserLoggedIn()) {
			this.loginContainer.getElement().getStyle().setDisplay(Display.NONE);
			this.buttonPanel.getElement().getStyle().clearDisplay();
		}else {
			this.loginContainer.getElement().getStyle().clearDisplay();
			this.buttonPanel.getElement().getStyle().setDisplay(Display.NONE);
		}
		this.hideChangePasswordContainer(true);
	}

	@Override
	public void hideChangePasswordContainer(boolean hide) {
		if(hide) {
			this.forgotPasswordContainer.getElement().getStyle().setDisplay(Display.NONE);
		}else {
			this.forgotPasswordContainer.getElement().getStyle().clearDisplay();
		}
	}

	@Override
	public void showSaveButtonSpinner(boolean show){
		if(!show) {
			this.saveSpinner.getElement().getStyle().setDisplay(Display.NONE);
		}else {
			this.saveSpinner.getElement().getStyle().clearDisplay();
		}
		this.saveButton.setEnabled(!show);
	}

}
