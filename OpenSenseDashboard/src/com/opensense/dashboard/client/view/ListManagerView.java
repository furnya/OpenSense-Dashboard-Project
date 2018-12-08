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
	}

	public Widget asWidget();
	public void removeListItem(int listId);
	public void setSensorsInList(int listId, List<Integer> sensors);
	public void addNewUserListItem(int listId, List<Integer> sensorList);
	public void clearLists();
	public void initDefaultLists();
	public void setPresenter(Presenter listManagerPresenter);
}
