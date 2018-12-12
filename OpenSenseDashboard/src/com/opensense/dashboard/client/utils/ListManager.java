package com.opensense.dashboard.client.utils;

import java.util.List;
import java.util.Map;

import com.opensense.dashboard.client.event.SelectedSensorsChangeEvent;
import com.opensense.dashboard.client.event.SelectedSensorsChangeEventHandler;
import com.opensense.dashboard.client.presenter.ListManagerPresenter;
import com.opensense.dashboard.client.view.ListManagerViewImpl;

public class ListManager {

	private ListManagerOptions options;
	private ListManagerPresenter presenter;
	private ListManagerViewImpl view;

	private boolean userLoggedIn = false;

	private SelectedSensorsChangeEventHandler selectedSensorsChangeEventHandler;

	private ListManager(ListManagerOptions options) {
		this.options = options;
		this.init();
	}

	private void init() {
		this.options.getContainer().clear();
		if (this.view == null) {
			this.view = new ListManagerViewImpl();
		}
		this.presenter = new ListManagerPresenter(this.options.getEventBus(), this, this.view);
		this.presenter.go(this.options.getContainer());
	}

	public static ListManager getInstance(ListManagerOptions options) {
		return new ListManager(options);
	}

	public ListManagerOptions getOptions() {
		return this.options;
	}

	public void createNewList() {
		this.presenter.createNewList();
	}

	public void updateLists() {
		this.presenter.updateLists();
	}

	public void setUserLoggedInAndUpdate(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
		this.presenter.updateLists();
	}

	public boolean isUserLoggedIn() {
		return this.userLoggedIn;
	}

	public void addSelectedSensorsChangeHandler(SelectedSensorsChangeEventHandler handler) {
		this.selectedSensorsChangeEventHandler = handler;
	}

	public void onSelectedSensorsChangeEvent(SelectedSensorsChangeEvent event) {
		if(this.selectedSensorsChangeEventHandler != null){
			this.selectedSensorsChangeEventHandler.onSelectedSensorsChangeEvent(event);
		}
	}

	public void updateFavoriteList() {
		this.presenter.updateFavoriteList();
	}

	public void waitUntilViewInit(Runnable runnable) {
		this.presenter.waitUntilViewInit(runnable);
	}

	public void onUserLoggedIn() {
		this.setUserLoggedInAndUpdate(true);
	}

	public void onUserLoggedOut() {
		this.setUserLoggedInAndUpdate(false);
	}

	public void updateSelectedSensorsList(List<Integer> ids) {
		this.presenter.updateSelectedSensorsList(ids);
	}

	/**
	 * @param sensorColors map of sensor ids with their new background color
	 */
	public void setSelectedSensorItemsColor(Map<Integer, String> sensorColors) {
		this.presenter.setSelectedSensorItemsColor(sensorColors);
	}
}
