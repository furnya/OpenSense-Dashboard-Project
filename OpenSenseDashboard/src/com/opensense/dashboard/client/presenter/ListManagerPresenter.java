package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.view.ListManagerView;
import com.opensense.dashboard.client.view.ListManagerViewImpl;
import com.opensense.dashboard.shared.ActionResult;

public class ListManagerPresenter implements IPresenter, ListManagerView.Presenter{

	private ListManagerView view;
	private AppController appController;
	private HandlerManager eventBus;

	public ListManagerPresenter(AppController appController, HandlerManager eventBus, ListManagerViewImpl view) {
		this.appController = appController;
		this.eventBus = eventBus;
		this.view = view;
		view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	public void updateLists() {
		this.view.clearListContainer();
		this.view.setSensorsInList(-1, CookieManager.getFavoriteList());
		if(!this.appController.isGuest()) {
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
					this.view.addNewUserListItem(-3, result);
				}else {
					GWT.log("Shit1");
				}
			}, caught -> {
				GWT.log("Shit3");
			},true));
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
	public void deleteSensorInList(final int listId, final int sensorCardId) {
		// TODO Auto-generated method stub
		if(listId == -1) { // -1 fav list, -2 selected sensor list, -3 mysensor list
			//			this.view.rem oveSensorInListItem(sensorCardId);
			this.appController.removeSensorFromFavoriteList(sensorCardId);
			this.updateLists();
		}
	}

	@Override
	public HandlerManager getEventBus() {
		return this.eventBus;
	}
}
