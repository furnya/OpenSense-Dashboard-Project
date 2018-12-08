package com.opensense.dashboard.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class RemoveSensorsFromFavoriteListEvent extends GwtEvent<RemoveSensorsFromFavoriteListEventHandler>{

	public static final Type<RemoveSensorsFromFavoriteListEventHandler> TYPE = new Type<>();

	private List<Integer> ids;

	public RemoveSensorsFromFavoriteListEvent(List<Integer> ids) {
		super();
		this.ids = ids;
	}

	@Override
	public Type<RemoveSensorsFromFavoriteListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RemoveSensorsFromFavoriteListEventHandler handler) {
		handler.onRemoveSensorsFromFavoriteListEvent(this);
	}

	public List<Integer> getIds() {
		return this.ids;
	}
}
