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
import com.opensense.dashboard.client.utils.Languages;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPreLoader;
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
	MaterialPreLoader spinner;
	
	public UserViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("regiLabel")
	public void onRegiLabelClicked(ClickEvent e) {
		resetViewElements();
		if(Languages.register().equals(regiLabel.getText())) {
			regiLabel.setText(Languages.login());
			passwordVerify.getElement().getStyle().setDisplay(Display.BLOCK);
		}else {
			regiLabel.setText(Languages.register());
			passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	@UiHandler("loginButton")
	public void onLoginButtonClicked(ClickEvent e) {
		spinner.getElement().getStyle().clearDisplay();
		loginButton.setEnabled(false);
		regiLabel.setEnabled(false);
		presenter.sendLoginRequest(userName.getText(), password.getText());
	}
	
	@UiHandler("userName")
	public void onUserNameValueChange(ValueChangeEvent<String> event) {
		clearErrors();
	}
	
	@UiHandler("password")
	public void onPasswordValueChange(ValueChangeEvent<String> event) {
		clearErrors();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		if(presenter.isUserLoggedIn()) {
			loginContainer.getElement().getStyle().setDisplay(Display.NONE);
		}else {
			loginContainer.getElement().getStyle().clearDisplay();
		}
	}

	@Override
	public void resetViewElements() {
		loginButton.setEnabled(true);
		regiLabel.setEnabled(true);
		password.reset();
		userName.reset();
		spinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	private void clearErrors() {
		password.getErrorHandler().clearErrors();
		userName.getErrorHandler().clearErrors();
	}

	@Override
	public void showLoginNotValid() {
		resetViewElements();
		password.setError(Languages.loginValidateError());
		userName.setError("");
	}
}
