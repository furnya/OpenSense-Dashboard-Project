package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ActiveChangeEventHandler extends EventHandler {
	void onActiveChangeEvent(ActiveChangeEvent activeChangeEvent);
}
