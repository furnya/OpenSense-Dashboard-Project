package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;

public interface ListManagerView {

	public interface Presenter {
		void deleteList(int listId);
		void deleteSensorCardInList(int listId, int sensorCardId);
		HandlerManager getEventBus();
	}

	public Widget asWidget();
	public void addNewListItem(int listid);
	public void removeListItem(int listId);
	public void setSensorsInList(int listId, List<Integer> sensors);
}
