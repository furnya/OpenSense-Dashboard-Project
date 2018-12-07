package com.opensense.dashboard.client.utils;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;

public class ListManagerOptions {

	private HasWidgets container;
	private AppController appController;
	private HandlerManager eventBus;
	private boolean editingActive = false;
	private boolean showSearchButton = true;
	private boolean showMapButton = true;
	private boolean showVisualizationButton = true;

	private ListManagerOptions(HandlerManager eventBus, AppController appController, HasWidgets container) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.container = container;
	}

	public static ListManagerOptions getInstance(HandlerManager eventBus, AppController appController, HasWidgets container) {
		return new ListManagerOptions(eventBus, appController, container);
	}

	public HasWidgets getContainer() {
		return this.container;
	}

	public AppController getEventBus() {
		return this.appController;
	}

	public HandlerManager getAppController() {
		return this.eventBus;
	}

	public boolean isShowSearchButton() {
		return this.showSearchButton;
	}

	public void setShowSearchButton(boolean showSearchButton) {
		this.showSearchButton = showSearchButton;
	}

	public boolean isShowMapButton() {
		return this.showMapButton;
	}

	public void setShowMapButton(boolean showMapButton) {
		this.showMapButton = showMapButton;
	}

	public boolean isShowVisualizationButton() {
		return this.showVisualizationButton;
	}

	public void setShowVisualizationButton(boolean showVisualizationButton) {
		this.showVisualizationButton = showVisualizationButton;
	}

	public boolean isEditingActive() {
		return this.editingActive;
	}

	public void setEditingActive(boolean editingActive) {
		this.editingActive = editingActive;
	}
}
