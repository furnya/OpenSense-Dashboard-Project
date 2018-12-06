package com.opensense.dashboard.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface ListManagerView {

	public interface Presenter {

	}

	public Widget asWidget();
	public void addFavoriteSensors(List<Integer> favoriteList);
	public void addNewListItem(int listid);
	public void removeListItem(int listId);
}
