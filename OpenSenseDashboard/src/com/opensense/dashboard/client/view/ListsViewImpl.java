package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.ListController;

import gwt.material.design.client.ui.MaterialButton;

public class ListsViewImpl extends DataPanelPageView implements ListsView {

	@UiTemplate("ListsView.ui.xml")
	interface ListsViewUiBinder extends UiBinder<Widget, ListsViewImpl> {
	}

	private static ListsViewUiBinder uiBinder = GWT.create(ListsViewUiBinder.class);

	protected Presenter presenter;

	private ListController listController = new ListController();

	@UiField
	Div listContainer;

	@UiField
	MaterialButton createListButton;

	public ListsViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.listContainer.add(this.listController.getContainer());
	}

	@UiHandler("createListButton")
	public void onCreateListButtonClicked(ClickEvent e) {
		this.listController.createNewList();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		// init UI Elements if needed
	}
}
