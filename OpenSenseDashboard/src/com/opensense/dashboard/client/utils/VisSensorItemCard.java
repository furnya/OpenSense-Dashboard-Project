package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;

public class VisSensorItemCard extends Composite{
	
	@UiTemplate("VisSensorItemCard.ui.xml")
	interface VisSensorItemCardUiBinder extends UiBinder<Widget, VisSensorItemCard> {
	}
	
	private static VisSensorItemCardUiBinder uiBinder = GWT.create(VisSensorItemCardUiBinder.class);
	
	@UiField
	Div layout;
	
	@UiField
	MaterialLabel header;
	
	@UiField
	Image icon;
	
	@UiField
	Div midContainer;
	
	@UiField
	MaterialCheckBox checkbox;
	
	@UiField
	Button favButton;
	
	public VisSensorItemCard() {
		initWidget(uiBinder.createAndBindUi(this));
//		addClickHandler();
		favButton.add(new Image(GUIImageBundle.INSTANCE.favorite().getSafeUri().asString()));
		this.setActive(true);
	}
	
	@UiHandler("favButton")
	public void onFavButtonClicked(ClickEvent e) {
		e.stopPropagation();
	}
	
	public void setHeader(String text) {
		this.header.setText(text);
	}
	
	public void setIcon(String url) {
		this.icon.setUrl(url);
	}
	
	public void setIconTitle(String title) {
		this.icon.setTitle(title);
	}
	
	public Div getMiddleHeader() {
		return this.midContainer;
	}
	
	private void addClickHandler() {
		layout.addDomHandler(event -> checkbox.setValue(!checkbox.getValue(), true), ClickEvent.getType());
	}
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return checkbox.addValueChangeHandler(event -> {
			setActive(event.getValue());
			handler.onValueChange(event);
		});
	}

	public void setActive(boolean active) {
		checkbox.setValue(active);
		if(active) {
			layout.addStyleName("card-active");
			layout.removeStyleName("card-deactive");
		}else {
			layout.addStyleName("card-deactive");
			layout.removeStyleName("card-active");
		}
	}
}
