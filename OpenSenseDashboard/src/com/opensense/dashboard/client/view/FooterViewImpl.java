package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class FooterViewImpl extends Composite implements FooterView{
	
	@UiTemplate("FooterView.ui.xml")
	interface FooterViewUiBinder extends UiBinder<Widget, FooterViewImpl> {
	}
	
	private static FooterViewUiBinder uiBinder = GWT.create(FooterViewUiBinder.class);
	
	protected Presenter presenter;
	
	@UiField
	HTML htmlCon;
	
	@UiField
	Span languageSwitch;
	
	public FooterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		languageSwitch.addDomHandler(event -> presenter.switchLanguage(), ClickEvent.getType());
		bindLinks();
	}
	
	private void bindLinks() {
		StringBuilder builder = new StringBuilder();
		builder.append("<a href=\"https://www.opensense.network/\" target=\"_blank\" rel=\"noopener\">Hauptseite</a>");
		htmlCon.setHTML(builder.toString());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
