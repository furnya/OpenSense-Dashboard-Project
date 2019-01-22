package com.opensense.dashboard.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.Languages;

import gwt.material.design.client.ui.MaterialImage;

public class FooterViewImpl extends Composite implements FooterView{

	@UiTemplate("FooterView.ui.xml")
	interface FooterViewUiBinder extends UiBinder<Widget, FooterViewImpl> {
	}

	private static final String ACTIVE = "active";

	private static FooterViewUiBinder uiBinder = GWT.create(FooterViewUiBinder.class);

	protected Presenter presenter;


	@UiField
	MaterialImage germanButton;

	@UiField
	MaterialImage englishButton;

	@UiField
	MaterialImage spanishButton;

	@UiField
	HTML htmlCon;

	public FooterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		setLangButtonActive(Languages.getActualLanguageString());
		bindLinks();
	}

	@UiHandler("germanButton")
	public void onGermanButtonClicked(ClickEvent event) {
		this.presenter.switchLanguage(Languages.GERMAN);
	}

	@UiHandler("englishButton")
	public void onEnglishButtonClicked(ClickEvent event) {
		this.presenter.switchLanguage(Languages.ENGLISH);
	}

	@UiHandler("spanishButton")
	public void onSpanishButtonClicked(ClickEvent event) {
		this.presenter.switchLanguage(Languages.SPANISH);
	}

	private void bindLinks() {
		StringBuilder builder = new StringBuilder();
		builder.append("<a href=\"https://www.opensense.network/\" target=\"_blank\" rel=\"noopener\">OpenSense.network</a>");
		this.htmlCon.setHTML(builder.toString());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void setLangButtonActive(String lang) {
		if(Languages.GERMAN.equals(lang)) {
			this.germanButton.addStyleName(ACTIVE);
			this.englishButton.removeStyleName(ACTIVE);
			this.spanishButton.removeStyleName(ACTIVE);
		}else if(Languages.ENGLISH.equals(lang)) {
			this.germanButton.removeStyleName(ACTIVE);
			this.englishButton.addStyleName(ACTIVE);
			this.spanishButton.removeStyleName(ACTIVE);
		}else {
			this.germanButton.removeStyleName(ACTIVE);
			this.englishButton.removeStyleName(ACTIVE);
			this.spanishButton.addStyleName(ACTIVE);
		}
	}


}
