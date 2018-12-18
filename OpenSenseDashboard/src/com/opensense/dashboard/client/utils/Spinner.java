package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.model.Size;

import gwt.material.design.client.ui.MaterialPreLoader;

public class Spinner extends Composite{

	@UiTemplate("Spinner.ui.xml")
	interface SpinnerUiBinder extends UiBinder<Widget, Spinner> {
	}

	@UiField
	MaterialPreLoader spinner;

	private static SpinnerUiBinder uiBinder = GWT.create(SpinnerUiBinder.class);


	public Spinner() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.setSize(Size.LARGE);
	}

	public void setSize(Size size) {
		switch (size) {
		case LARGE:
			this.spinner.setWidth("45px");
			this.spinner.setHeight("45px");
			break;
		case MEDIUM:
			this.spinner.setWidth("30px");
			this.spinner.setHeight("30px");
			break;
		case SMALL:
			this.spinner.setWidth("20px");
			this.spinner.setHeight("20px");
			break;
		default:
			break;
		}
	}

	public void setDisplay(Display display) {
		this.getElement().getStyle().setDisplay(display);
	}
}
