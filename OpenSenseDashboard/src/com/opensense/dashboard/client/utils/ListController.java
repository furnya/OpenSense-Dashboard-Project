package com.opensense.dashboard.client.utils;

import com.google.gwt.user.client.ui.Widget;

public class ListController {

	private ListRenderer rendererInstance;

	public ListController() {
		this.rendererInstance = new ListRenderer(this);
	}

	public Widget getContainer() {
		return this.rendererInstance.asWidget();
	}

	public void createNewList() {
		this.rendererInstance.addNewListItem();
	}
}
