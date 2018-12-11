package com.opensense.dashboard.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class AddSensorsToFavoriteListEvent extends GwtEvent<AddSensorsToFavoriteListEventHandler>{

	public static final Type<AddSensorsToFavoriteListEventHandler> TYPE = new Type<>();

	private List<Integer> ids;

	public AddSensorsToFavoriteListEvent(List<Integer> ids) {
		super();
		this.ids = ids;
	}

	@Override
	public Type<AddSensorsToFavoriteListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddSensorsToFavoriteListEventHandler handler) {
		handler.onAddSensorsToFavoriteListEvent(this);
	}

	public List<Integer> getIds() {
		return this.ids;
	}
}
