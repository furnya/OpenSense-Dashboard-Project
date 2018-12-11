package com.opensense.dashboard.client.utils;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class ListManagerOptions {

	private HasWidgets container;
	private HandlerManager eventBus;
	private boolean editingActive = false;
	private boolean showSearchButton = true;
	private boolean showMapButton = true;
	private boolean showVisualizationButton = true;
	private PagerSize pagerSize = PagerSize.MEDIUM;
	private int maxObjectOnPage = 10;

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

	public void setPagerSize(PagerSize pagerSize) {
		this.pagerSize = pagerSize;
	}

	public PagerSize getPagerSize() {
		return this.pagerSize;
	}

	public int getMaxObjectOnPage() {
		return maxObjectOnPage;
	}

	public void setMaxObjectOnPage(int maxObjectOnPage) {
		this.maxObjectOnPage = maxObjectOnPage;
	}
}
