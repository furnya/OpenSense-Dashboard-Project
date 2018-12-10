package com.opensense.dashboard.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserList implements IsSerializable{

	private int listId;
	private String listName;
	private List<Integer> sensorIds;

	public UserList() {
	}

	public int getListId() {
		return this.listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public String getListName() {
		return this.listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public List<Integer> getSensorIds() {
		return this.sensorIds;
	}

	public void setSensorIds(List<Integer> sensorIds) {
		this.sensorIds = sensorIds;
	}
}
