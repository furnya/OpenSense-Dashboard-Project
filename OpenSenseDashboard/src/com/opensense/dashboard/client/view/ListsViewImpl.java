package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.AddSensorModal;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.ListManagerOptions;

import gwt.material.design.client.ui.MaterialButton;

public class ListsViewImpl extends DataPanelPageView implements ListsView {

	@UiTemplate("ListsView.ui.xml")
	interface ListsViewUiBinder extends UiBinder<Widget, ListsViewImpl> {
	}

	private static ListsViewUiBinder uiBinder = GWT.create(ListsViewUiBinder.class);

	protected Presenter presenter;

	private ListManager listManager;

	@UiField
	Div listContainer;

	@UiField
	MaterialButton createListButton;
	
	@UiField
	MaterialButton createSensor;

	public ListsViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("createListButton")
	public void onCreateListButtonClicked(ClickEvent e) {
		this.listManager.createNewList();
	}
	
	@UiHandler("createSensor")
	public void onCreateSensorClicked(ClickEvent e) {
		AddSensorModal modal = new AddSensorModal(this.listManager);
		RootPanel.get().add(modal);
		modal.open();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView(Runnable runnable, boolean isUserLoggedIn) {
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(), this.listContainer);
		listManagerOptions.setEditingActive(true);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.setUserLoggedIn(isUserLoggedIn);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> event.getSelectedIds().forEach(id -> GWT.log(id+"")));
	}

	@Override
	public ListManager getListManager() {
		return this.listManager;
	}

	@Override
	public void setCreateListButtonEnabled(boolean enabled) {
		this.createListButton.setEnabled(enabled);
	}
	
	@Override
	public void setCreateSensorButtonEnabled(boolean enabled) {
		this.createSensor.setEnabled(enabled);
	}

}
