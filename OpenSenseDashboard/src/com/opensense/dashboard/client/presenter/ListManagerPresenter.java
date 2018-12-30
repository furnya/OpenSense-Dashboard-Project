package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.event.AddSensorsToFavoriteListEvent;
import com.opensense.dashboard.client.event.RemoveSensorsFromFavoriteListEvent;
import com.opensense.dashboard.client.model.DefaultListItem;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.ListManagerView;
import com.opensense.dashboard.client.view.ListManagerViewImpl;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;
import com.opensense.dashboard.shared.UserList;

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
		this.updateUserLists();
		if(this.controller.isUserLoggedIn()) {
			this.view.showMySensorListsItem(true);
			this.view.setOneItemStyle(false);
			final RequestBuilder requestBuilder = new RequestBuilder(ResultType.MYSENSORS, false);
			GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
				if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getMyListSensors() != null)
						&& !result.getMyListSensors().isEmpty()){
					this.getMinimalSensorDataAndShow(DefaultListItem.MY_LIST_ID, result.getMyListSensors(), false);
				}else {
					this.view.setSensorsInList(DefaultListItem.MY_LIST_ID, new ArrayList<>());
				}
				this.view.setOldSelection();
			}, caught -> {
				GWT.log("Shit4");
			},true));
		}else {
			this.view.setOneItemStyle(true);
			this.view.showMySensorListsItem(false);
		}
	}

	public void createNewList() {
		GeneralService.Util.getInstance().createNewUserList(new DefaultAsyncCallback<Integer>(result -> {
			GWT.log("Created new list");
			if(result != null) {
				this.view.setActiveItemId(result);
				this.updateUserLists();
			}else {
				GWT.log("Shit5");
			}
		}, caught -> {
			GWT.log("Shit6");
		},true));
	}

	@Override
	public void deleteUserList(final int listId) {
		GeneralService.Util.getInstance().deleteUserList(listId, new DefaultAsyncCallback<ActionResult>(result -> {
			if(result != null) {
				this.updateLists();
			}else {
				GWT.log("Shit7");
			}
		}, caught -> {
			GWT.log("Shit8");
		},true));
	}

	/**
	 * server calls for other list ids
	 */
	public void addSensorsToList(final int listId, final List<Integer> sensorIds) {
		//TODO: Auto-generated method stub
		if(listId == -1) {
			this.eventBus.fireEvent(new AddSensorsToFavoriteListEvent(sensorIds));
		}
	}

	/**
	 * server calls for other list ids
	 */
	@Override
	public void deleteSensorsInList(final int listId, final List<Integer> sensorIds) {
		if(listId == DefaultListItem.FAVORITE_LIST_ID) {
			this.eventBus.fireEvent(new RemoveSensorsFromFavoriteListEvent(sensorIds));
		}else if(listId == DefaultListItem.MY_LIST_ID) {
			//TODO:
		}else if(listId == DefaultListItem.SELECTED_LIST_ID) {
			// Do nothing
		}else {
			GeneralService.Util.getInstance().deleteSensorsFromUserList(listId, sensorIds, new DefaultAsyncCallback<ActionResult>(result -> {
				if((result != null) && ActionResultType.SUCCESSFUL.equals(result.getActionResultType())) {
					this.updateUserLists();
				}else {
					GWT.log("Shit9");
				}
			}, caught -> {
				GWT.log("Shit10");
			},true));
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
		List<Integer> favoriteIds = CookieManager.getFavoriteList();
		if(!favoriteIds.isEmpty()) {
			this.getMinimalSensorDataAndShow(DefaultListItem.FAVORITE_LIST_ID, favoriteIds, false);
		}else {
			this.view.setSensorsInList(DefaultListItem.FAVORITE_LIST_ID, new ArrayList<>());
		}
		this.view.setOldSelection();
	}

	public void updateSelectedSensorsList(List<Integer> idList) {
		if(!idList.isEmpty()) {
			this.view.showSelectedSensorListsItem(true);
			this.view.setCollapsibleListItemSelected(DefaultListItem.SELECTED_LIST_ID);
			this.getMinimalSensorDataAndShow(DefaultListItem.SELECTED_LIST_ID, idList, true);
		}else {
			this.view.showSelectedSensorListsItem(false);
		}
	}

	public void waitUntilViewInit(Runnable runnable) {
		this.view.initDefaultLists(runnable);
		this.updateLists();
	}

	@Override
	public void changeListName(int listId, String newListName) {
		GeneralService.Util.getInstance().changeUserListName(listId, newListName, new DefaultAsyncCallback<ActionResult>(result -> {
			if(result != null) {
				//TODO: the list name should only set in ui if serverCall was successful
			}else {
				GWT.log("Shit9");
			}
		}, caught -> {
			GWT.log("Shit10");
		},true));
	}

	public void getMinimalSensorDataAndShow(final int listId, final List<Integer> sensorIds, final boolean selectAll) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.MINIMAL_SENSOR, false);
		sensorIds.forEach(requestBuilder::addId);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(), new DefaultAsyncCallback<Response>(result -> {
			if((result != null) && (result.getResultType() != null) && requestBuilder.getRequest().getRequestType().equals(result.getResultType()) && (result.getMinimalSensors() != null)) {
				this.view.setSensorsInList(listId, result.getMinimalSensors());
				if(selectAll) {
					this.view.selectAllSensorsInList(listId);
				}
			}else {
				GWT.log("Shit11");
			}
		}, caught -> {
			GWT.log("Shit12");
		},true));
	}

	public void setSelectedSensorItemsColor(int sensorId, String sensorColor) {
		this.view.setSelectedSensorItemsColor(sensorId, sensorColor);
	}
	private void updateUserLists() {
		this.view.clearUserLists();
		if(this.controller.isUserLoggedIn()) {
			GeneralService.Util.getInstance().getUserLists(new DefaultAsyncCallback<List<UserList>>(result -> {
				if((result != null)) {
					GWT.log("List length="+result.size());
					result.forEach(userList -> {
						this.view.addNewUserListItem(userList, true);
						if(!userList.getSensorIds().isEmpty()) {
							this.getMinimalSensorDataAndShow(userList.getListId(), userList.getSensorIds(), false);
						}else {
							this.view.setSensorsInList(userList.getListId(), new ArrayList<>());
						}
					});
					this.view.setOldSelection();
				}else {
					GWT.log("Shit1");
				}
			}, caught -> {
				GWT.log("Shit2");
			},true));
		}
	}
}
