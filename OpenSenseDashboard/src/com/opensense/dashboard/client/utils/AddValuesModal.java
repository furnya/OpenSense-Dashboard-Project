package com.opensense.dashboard.client.utils;

import java.util.List;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.ListsView.Presenter;
import com.opensense.dashboard.shared.AddValuesRequest;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;

public class AddValuesModal extends Composite {

	@UiTemplate("AddValuesModal.ui.xml")
	interface AddValuesModalUiBinder extends UiBinder<Widget, AddValuesModal> {
	}

	private static AddValuesModalUiBinder uiBinder = GWT.create(AddValuesModalUiBinder.class);

	private Presenter presenter;

	@UiField
	MaterialModal modal;

	@UiField
	MaterialListBox sensorIdList;

	@UiField
	MaterialButton closeButton;

	@UiField
	MaterialButton confirmButton;

	@UiField
	MaterialModalContent content;

	@UiField
	FormPanel uploadForm;

	@UiField
	FileUpload fileUpload;

	@UiField
	MaterialImage infoIcon;

	@UiField
	Div infoContainer;

	public AddValuesModal(Presenter presenter) {
		this.presenter = presenter;
		this.initWidget(uiBinder.createAndBindUi(this));
		this.modal.setId("dashboard-modal");

		this.initFileUpload();
		this.initInfoContainer();
		//Remove the modal from DOM to prevent multiple modal which stays forever in the DOM
		this.modal.addCloseHandler(event -> RootPanel.get().remove(RootPanel.get("dashboard-modal")));
	}

	@UiHandler("closeButton")
	public void onCloseButtonClicked(ClickEvent e) {
		this.modal.close();
	}

	@UiHandler("confirmButton")
	public void onConfirmButtonClicked(ClickEvent e) {
		this.uploadForm.submit();
	}

	private void initFileUpload() {
		this.uploadForm.setAction(GWT.getHostPageBaseURL() + "fileupload");
		this.uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		this.uploadForm.setMethod(FormPanel.METHOD_POST);
		this.uploadForm.addSubmitHandler(event -> {
			String filename = this.fileUpload.getFilename();
			if (filename.length()==0 || !filename.endsWith(".csv")) {
				event.cancel();
				AppController.showInfo("Es können nur Datein im csv Format hochgeladen werden");
			}
		});
		this.uploadForm.addSubmitCompleteHandler(event -> {
			if(event.getResults().contains(Languages.successful())) {
				this.sendAddValuesRequest();
			}else {
				AppController.showError(event.getResults());
			}
		});
	}
	
	private void sendAddValuesRequest() {
		AddValuesRequest request = new AddValuesRequest();
		request.setSensorId(Integer.valueOf(this.sensorIdList.getValue()));
		this.presenter.addValuesRequest(request);
		this.modal.close();
	}


	public void open() {
		this.modal.open();
	}

	public void setSensorIds(List<Integer> sensorIds) {
		this.sensorIdList.clear();
		this.sensorIdList.setSelectedIndex(0);
		sensorIds.forEach(sensorId -> this.sensorIdList.addItem(sensorId+"", sensorId+""));
	}

	private void initInfoContainer() {
		this.infoContainer.getElement().getStyle().setDisplay(com.google.gwt.dom.client.Style.Display.NONE);
		this.infoIcon.addMouseOverHandler(event -> {
			this.infoContainer.getElement().getStyle().clearDisplay();
		});
		this.infoIcon.addMouseOutHandler(event -> {
			this.infoContainer.getElement().getStyle().setDisplay(com.google.gwt.dom.client.Style.Display.NONE);
		});
	}
}
