package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SelectedSensorsChangeEventHandler extends EventHandler {
	void onSelectedSensorsChangeEvent(SelectedSensorsChangeEvent selectedSensorsChangeEvent);
}
