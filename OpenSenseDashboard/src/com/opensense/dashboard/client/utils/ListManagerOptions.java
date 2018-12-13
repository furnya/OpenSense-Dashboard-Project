package com.opensense.dashboard.client.utils;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.model.Size;

public class ListManagerOptions {

	private HasWidgets container;
	private HandlerManager eventBus;
	private boolean editingActive = false;
	private boolean showSearchButton = true;
	private boolean showMapButton = true;
	private boolean showVisualizationButton = true;
	private Size pagerSize = Size.MEDIUM;
	private int maxObjectOnPage = 10;
	private Size spinnerSize = Size.MEDIUM;

	private ListManagerOptions(HandlerManager eventBus, HasWidgets container) {
		this.eventBus = eventBus;
		this.container = container;
	}

	public static ListManagerOptions getInstance(HandlerManager eventBus, HasWidgets container) {
		return new ListManagerOptions(eventBus, container);
	}

	public HasWidgets getContainer() {
		return this.container;
	}

	public HandlerManager getEventBus() {
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

	public void setPagerSize(Size pagerSize) {
		this.pagerSize = pagerSize;
	}

	public Size getPagerSize() {
		return this.pagerSize;
	}

	public int getMaxObjectOnPage() {
		return this.maxObjectOnPage;
	}

	public void setMaxObjectOnPage(int maxObjectOnPage) {
		this.maxObjectOnPage = maxObjectOnPage;
	}

	public Size getSpinnerSize() {
		return spinnerSize;
	}

	public void setSpinnerSize(Size spinnerSize) {
		this.spinnerSize = spinnerSize;
	}
}
