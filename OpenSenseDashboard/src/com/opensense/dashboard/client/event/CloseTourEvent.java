package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CloseTourEvent extends GwtEvent<CloseTourEventHandler> {

	public static final Type<CloseTourEventHandler> TYPE = new Type<>();
	private Boolean neverShowToursAgain;

	public CloseTourEvent(Boolean neverShowToursAgain) {
		super();
		this.neverShowToursAgain = neverShowToursAgain;
	}

	public CloseTourEvent() {
		super();
	}

	@Override
	public Type<CloseTourEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CloseTourEventHandler handler) {
		handler.onCloseTourEvent(this);
	}

	public Boolean getNeverShowToursAgain() {
		return this.neverShowToursAgain;
	}
}
