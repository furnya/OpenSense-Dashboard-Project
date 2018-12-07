package com.opensense.dashboard.client.utils;

import com.opensense.dashboard.client.presenter.ListManagerPresenter;
import com.opensense.dashboard.client.view.ListManagerViewImpl;

public class ListManager {

	private ListManagerOptions options;
	private ListManagerPresenter presenter;
	private ListManagerViewImpl view;

	private ListManager(ListManagerOptions options) {
		this.options = options;
		this.init();
	}

	private void init() {
		this.options.getContainer().clear();
		if (this.view == null) {
			this.view = new ListManagerViewImpl();
		}
		this.presenter = new ListManagerPresenter(this.options.getEventBus(), this.options.getAppController(), this.view);
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

}
