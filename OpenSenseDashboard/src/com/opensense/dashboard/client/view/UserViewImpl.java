package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

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
	
	public UserViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("regiLabel")
	public void onRegiLabelClicked(ClickEvent e) {
		if("Registrieren".equals(regiLabel.getText())) {
			regiLabel.setText("Anmelden");
			passwordVerify.getElement().getStyle().setDisplay(Display.BLOCK);
		}else {
			regiLabel.setText("Registrieren");
			passwordVerify.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	@UiHandler("loginButton")
	public void onLoginButtonClicked(ClickEvent e) {
		presenter.sendLoginRequest(userName.getText(), password.getText());
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
}
