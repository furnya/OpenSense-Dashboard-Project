package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.ListManager;

public interface ListManagerView {

	public interface Presenter {
		void deleteList(int listId);
		void deleteSensorsInList(int listId, List<Integer> sensorCardId);
		HandlerManager getEventBus();
		ListManager getController();
		void changeListName(int listId, String listName);
	}

	public Widget asWidget();
	public void removeListItem(int listId);
	public void setSensorsInList(int listId, List<Integer> sensors);
	public void addNewUserListItem(int listId, List<Integer> sensorList);
	public void setPresenter(Presenter listManagerPresenter);
	public void initDefaultLists(Runnable object);
	public void showMySensorListsItem(boolean b);
	public void showSelectedSensorListsItem(boolean b);
	public void clearUserLists();
}
