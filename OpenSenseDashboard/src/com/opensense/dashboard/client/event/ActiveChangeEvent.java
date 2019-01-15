package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ActiveChangeEvent extends GwtEvent<ActiveChangeEventHandler>{

	public static final Type<ActiveChangeEventHandler> TYPE = new Type<>();

	private boolean active;

	public ActiveChangeEvent(boolean active) {
		super();
		this.active = active;
	}

	@Override
	public Type<ActiveChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActiveChangeEventHandler handler) {
		handler.onActiveChangeEvent(this);
	}

	public boolean isActive() {
		return this.active;
	}
}
