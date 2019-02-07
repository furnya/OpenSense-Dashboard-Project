package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;

public class ConfirmDeleteModal extends Composite{

	@UiTemplate("ConfirmDeleteModal.ui.xml")
	interface ConfirmDeleteModalUiBinder extends UiBinder<Widget, ConfirmDeleteModal> {
	}

	private static ConfirmDeleteModalUiBinder uiBinder = GWT.create(ConfirmDeleteModalUiBinder.class);

	@UiField
	MaterialModal modal;

	@UiField
	MaterialButton cancelButton;

	@UiField
	MaterialButton confirmButton;

	ClickHandler handler;

	public ConfirmDeleteModal(ClickHandler handler) {
		this.handler = handler;
		this.initWidget(uiBinder.createAndBindUi(this));
	}


	public void open() {
		this.modal.open();
	}

	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent e) {
		this.modal.close();
	}

	@UiHandler("confirmButton")
	public void onConfirmButtonClicked(ClickEvent e) {
		this.modal.close();
		this.handler.onClick(e);
	}
}
