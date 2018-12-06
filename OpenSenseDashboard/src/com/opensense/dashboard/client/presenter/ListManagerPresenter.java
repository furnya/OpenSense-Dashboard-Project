package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.ListManagerView;
import com.opensense.dashboard.client.view.ListManagerViewImpl;

public class ListManagerPresenter implements IPresenter, ListManagerView.Presenter{

	private final ListManagerView view = new ListManagerViewImpl(this);

	private AppController appController;
	private HandlerManager eventBus;

	int ids = 0;

	public ListManagerPresenter(AppController appController, HandlerManager eventBus) {
		this.appController = appController;
		this.eventBus = eventBus;
	}


	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	public void updateLists() {
		this.view.setSensorsInList(-1, CookieManager.getFavoriteList());
		if(!this.appController.isGuest()) {
			GeneralService.Util.getInstance().getUserLists(new DefaultAsyncCallback<Map<Integer, List<Integer>>>(result -> {

			}));
		}
	}

	public void createNewList() {
		this.view.addNewListItem(this.ids++);
	}

	@Override
	public void deleteList(final int listId) {
		// TODO Auto-generated method stubd
		this.view.removeListItem(listId);
	}


	@Override
	public void deleteSensorCardInList(final int listId, final int sensorCardId) {
		// TODO Auto-generated method stub
		if(listId == -1) { // -1 fav list, -2 selected sensor list, -3 mysensor list
			this.appController.removeSensorFromFavoriteList(sensorCardId);
			this.updateLists();
		}
	}

	@Override
	public HandlerManager getEventBus() {
		return this.eventBus;
	}
}
