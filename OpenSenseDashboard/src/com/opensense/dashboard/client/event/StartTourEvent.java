package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.opensense.dashboard.client.utils.tourutils.Tours;

public class StartTourEvent extends GwtEvent<StartTourEventHandler> {

	public static final Type<StartTourEventHandler> TYPE = new Type<>();
	private Tours tour;
	private boolean userStartedTourEvent = true;

	public StartTourEvent(Tours tour, boolean userStartedTourEvent) {
		super();
		this.tour = tour;
		this.userStartedTourEvent = userStartedTourEvent;
	}

	@Override
	public Type<StartTourEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(StartTourEventHandler handler) {
		handler.onStartTourEvent(this);
	}

	public boolean getUserStartedTourEvent() {
		return this.userStartedTourEvent;
	}

	public Tours getTour() {
		return this.tour;
	}
}
