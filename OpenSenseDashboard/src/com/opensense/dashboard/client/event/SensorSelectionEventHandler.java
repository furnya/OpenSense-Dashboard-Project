package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SensorSelectionEventHandler extends EventHandler {
	void onSensorSelectionEvent(SensorSelectionEvent sensorSelectionEvent);
}
