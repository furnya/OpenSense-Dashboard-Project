package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SensorSelectionEvent extends GwtEvent<SensorSelectionEventHandler>{

	public static final Type<SensorSelectionEventHandler> TYPE = new Type<>();

	private boolean selectAll;

	public SensorSelectionEvent(boolean selectAll) {
		super();
		this.selectAll = selectAll;
	}

	@Override
	public Type<SensorSelectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SensorSelectionEventHandler handler) {
		handler.onSensorSelectionEvent(this);
	}

	public boolean isSelectAll() {
		return this.selectAll;
	}
}
