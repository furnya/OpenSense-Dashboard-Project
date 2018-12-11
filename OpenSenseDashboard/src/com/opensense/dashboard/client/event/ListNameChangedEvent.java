package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ListNameChangedEvent extends GwtEvent<ListNameChangedEventHandler>{

	public static final Type<ListNameChangedEventHandler> TYPE = new Type<>();

	private String listName;

	public ListNameChangedEvent(String listName) {
		super();
		this.listName = listName;
	}

	@Override
	public Type<ListNameChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ListNameChangedEventHandler handler) {
		handler.onListNameChangedEvent(this);
	}

	public String getListName() {
		return this.listName;
	}
}
