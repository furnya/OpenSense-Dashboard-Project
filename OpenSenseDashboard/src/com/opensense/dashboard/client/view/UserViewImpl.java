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

	public UserViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("regiLabel")
	public void onRegiLabelClicked(ClickEvent e) {
		this.resetViewElements();
		if(Languages.register().equals(this.regiLabel.getText())) {
			this.regiLabel.setText(Languages.login());
			this.passwordVerify.getElement().getStyle().setDisplay(Display.BLOCK);
		}else {
			this.regiLabel.setText(Languages.register());
			this.passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@UiHandler("loginButton")
	public void onLoginButtonClicked(ClickEvent e) {
		this.spinner.getElement().getStyle().clearDisplay();
		this.loginButton.setEnabled(false);
		this.regiLabel.setEnabled(false);
		this.presenter.sendLoginRequest(this.userName.getText(), this.password.getText());
	}

	@UiHandler("userName")
	public void onUserNameValueChange(ValueChangeEvent<String> event) {
		this.clearErrors();
	}

	@UiHandler("password")
	public void onPasswordValueChange(ValueChangeEvent<String> event) {
		this.clearErrors();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		if(this.presenter.isUserLoggedIn()) {
			this.loginContainer.getElement().getStyle().setDisplay(Display.NONE);
		}else {
			this.loginContainer.getElement().getStyle().clearDisplay();
		}
	}

	@Override
	public void resetViewElements() {
		this.loginButton.setEnabled(true);
		this.regiLabel.setEnabled(true);
		this.password.reset();
		this.userName.reset();
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
}
