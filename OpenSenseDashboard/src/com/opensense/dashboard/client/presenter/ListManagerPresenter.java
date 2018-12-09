package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.event.RemoveSensorsFromFavoriteListEvent;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.view.ListManagerView;
import com.opensense.dashboard.client.view.ListManagerViewImpl;
import com.opensense.dashboard.shared.ActionResult;

public class ListManagerPresenter implements IPresenter, ListManagerView.Presenter{

	private ListManagerView view;
	private ListManager controller;
	private HandlerManager eventBus;

	public ListManagerPresenter(HandlerManager eventBus, ListManager controller, ListManagerViewImpl view) {
		this.controller = controller;
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	/**
	 * updates all lists except the selectedList
	 */
	public void updateLists() {
		this.updateFavoriteList();
		this.view.clearUserLists();
		if(this.controller.isUserLoggedIn()) {
			this.view.showMySensorListsItem(true);
			GeneralService.Util.getInstance().getUserLists(new DefaultAsyncCallback<Map<Integer, List<Integer>>>(result -> {
				if(result != null) {
					result.entrySet().forEach(entry -> this.view.addNewUserListItem(entry.getKey(), entry.getValue()));
				}else {
					GWT.log("Shit");
				}
			}, caught -> {
				GWT.log("Shit2");
			},true));
			GeneralService.Util.getInstance().getMySensorsUserList(new DefaultAsyncCallback<List<Integer>>(result -> {
				if(result != null) {
					this.view.setSensorsInList(-3, result);
				}else {
					GWT.log("Shit1");
				}
			}, caught -> {
				GWT.log("Shit3");
			},true));
		}else {
			this.view.showMySensorListsItem(false);
		}
	}

	public void createNewList() {
		GeneralService.Util.getInstance().createNewUserList(new DefaultAsyncCallback<Integer>(result -> {
			if(result != null) {
				this.view.addNewUserListItem(result, new ArrayList<>());
			}else {
				GWT.log("Shit1");
			}
		}, caught -> {
			GWT.log("Shit3");
		},true));
	}

	@Override
	public void deleteList(final int listId) {
		GeneralService.Util.getInstance().deleteUserList(listId, new DefaultAsyncCallback<ActionResult>(result -> {
			if(result != null) {
				this.view.removeListItem(listId);
			}else {
				GWT.log("Shit1");
			}
		}, caught -> {
			GWT.log("Shit3");
		},true));
	}


	@Override
	public void deleteSensorsInList(final int listId, final List<Integer> sensorCardId) {
		// TODO Auto-generated method stub
		if(listId == -1) { // -1 fav list, -2 selected sensor list, -3 mysensor list
			this.eventBus.fireEvent(new RemoveSensorsFromFavoriteListEvent(sensorCardId));
		}
	}

	@Override
	public HandlerManager getEventBus() {
		return this.eventBus;
	}

	@Override
	public ListManager getController() {
		return this.controller;
	}

	public void updateFavoriteList() {
		this.view.setSensorsInList(-1, CookieManager.getFavoriteList());
	}

	public void updateSelectedSensorsList(List<Integer> idList) {
		if(!idList.isEmpty()) {
			this.view.showSelectedSensorListsItem(true);
			this.view.setSensorsInList(-2, idList);
		}else {
			this.view.showSelectedSensorListsItem(false);
		}
	}

	public void waitUntilViewInit(Runnable runnable) {
		this.view.initDefaultLists(runnable);
		this.updateLists();
	}

	@Override
	public void changeListName(int listId, String listName) {
		GWT.log(listId + " " + listName);
		//TODO: serverCall to change the list name
	}
}
