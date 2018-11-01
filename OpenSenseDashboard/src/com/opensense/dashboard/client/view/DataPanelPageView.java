package com.opensense.dashboard.client.view;

import com.google.gwt.user.client.ui.Composite;

public abstract class DataPanelPageView extends Composite implements IDataPanelPageView {
	
	private boolean isInitialized = false;
	
	@Override
	public final void setInitializedToTrue() {
		isInitialized = true;
	}
	
	@Override
	public final boolean isInitialized() {
		return isInitialized;
	}
}
