package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.UserList;

public interface ListManagerView {

	public interface Presenter {
		void deleteUserList(int listId);
		void deleteSensorsInList(int listId, List<Integer> sensorCardId);
		HandlerManager getEventBus();
		ListManager getController();
		void changeListName(int listId, String listName);
		public void requestAllSensorInfo(int sensorId, List<Integer> idList);
		void requestAndShowUserList(int listId);
		void addSelectedSensorsToUserList(int listId, String listName, List<Integer> selectedSensors);
	}

	public Widget asWidget();
	public void setPresenter(Presenter listManagerPresenter);
	public void initDefaultLists(Runnable object);
	public void showMySensorListsItem(boolean b);
	public void showSelectedSensorListsItem(boolean b);
	public void clearUserLists();
	public void selectAllSensorsInList(int listId);
	public void setCollapsibleListItemSelected(Integer listId);
	public void setSensorsInList(int listId, List<MinimalSensor> sensors);
	public void setSelectedSensorItemsColor(int sensorId, String sensorColor);
	public void addNewUserListItem(UserList userList, boolean addDeleteListButton);
	public void setOneItemStyle(boolean b);
	public Integer getActiveItemId();
	public void setOldSelection();
	public void setActiveItemId(Integer id);
	public void showAllSensorInfo(int listId, List<Sensor> list);
	public void showUserListDropdown(int listId, List<UserList> userLists);
	int clearUserList(int id);
	void addNewUserListItem(UserList userList, boolean editable, int index);
}
