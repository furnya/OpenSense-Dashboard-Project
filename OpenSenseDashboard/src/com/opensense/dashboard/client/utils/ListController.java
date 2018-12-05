package com.opensense.dashboard.client.utils;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.services.GeneralService;

public class ListController {

	private AppController appController;
	private ListRenderer rendererInstance;

	int ids = 0;

	public ListController() {
		this.rendererInstance = new ListRenderer(this);
		this.rendererInstance.addFavoriteSensors(CookieManager.getFavoriteList());
	}

	public void updateLists() {
		if(!this.appController.isGuest()) {
			GeneralService.Util.getInstance().getUserLists(new DefaultAsyncCallback<Map<Integer, List<Integer>>>(result -> {

			}));
		}
	}

	public Widget getContainer() {
		return this.rendererInstance.asWidget();
	}

	public void createNewList() {
		this.rendererInstance.addNewListItem(this.ids++);
	}

	public void deleteList(final int listId) {
		// TODO Auto-generated method stub
		this.rendererInstance.removeListItem(listId);
	}
}
