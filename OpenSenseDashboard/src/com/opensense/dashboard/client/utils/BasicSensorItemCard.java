package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPreLoader;

public class BasicSensorItemCard extends Composite{

	@UiTemplate("BasicSensorItemCard.ui.xml")
	interface BasicSensorItemCardUiBinder extends UiBinder<Widget, BasicSensorItemCard> {
	}

	private static BasicSensorItemCardUiBinder uiBinder = GWT.create(BasicSensorItemCardUiBinder.class);

	@UiField
	Div layout;
	
	@UiField
	MaterialCollapsible collapsible;
	
	@UiField
	MaterialCollapsibleItem collapsibleItem;

	@UiField
	MaterialLabel header;

	@UiField
	Image icon;

	@UiField
	MaterialCheckBox checkbox;

	@UiField
	MaterialImage expandButton;
	
	@UiField
	MaterialImage favButton;

	@UiField
	MaterialImage trashButton;

	@UiField
	MaterialPreLoader cardSpinner;

	public BasicSensorItemCard() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.addClickHandler();
	}

	public void addFavButtonClickHandler(ClickHandler handler) {
		this.favButton.getElement().getStyle().clearDisplay();
		this.favButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}

	public void addTrashButtonClickHandler(ClickHandler handler) {
		this.trashButton.getElement().getStyle().clearDisplay();
		this.trashButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
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

	private void addClickHandler() {
		this.collapsibleItem.addDomHandler(event -> this.checkbox.setValue(!this.checkbox.getValue(), true), ClickEvent.getType());
		this.addClickListener(this.checkbox.getElement());
		this.addClickListener(this.collapsibleItem.getElement());
		this.expandButton.addDomHandler(event -> {
			if(this.collapsibleItem.isActive()) {
				this.collapsibleItem.collapse();
			}else {
				this.collapsibleItem.expand();
			}
		}, ClickEvent.getType());
		this.addClickListener(this.expandButton.getElement());
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return this.checkbox.addValueChangeHandler(event -> {
			this.setActive(event.getValue());
			handler.onValueChange(event);
		});
	}

	public void setActive(boolean active) {
		this.checkbox.setValue(active);
		if(active) {
			this.layout.addStyleName("card-active");
		}else {
			this.layout.removeStyleName("card-active");
			this.layout.getElement().removeAttribute("style");
		}
	}

	private native void addClickListener(Element elem) /*-{
		elem.addEventListener("click", function(event){
			event.stopPropagation();
		});
	}-*/;

	public void showLoadingIndicator(boolean show) {
		//TODO:
	}

}
