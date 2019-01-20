package com.opensense.dashboard.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class AddToListEvent extends GwtEvent<AddToListEventHandler>{

	public static final Type<AddToListEventHandler> TYPE = new Type<>();

	private int listId;

	public AddToListEvent(Integer listId) {
		super();
		this.listId = listId;
	}

	@Override
	public Type<AddToListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddToListEventHandler handler) {
		handler.onAddToListEvent(this);
	}

	public int getListId() {
		return this.listId;
	}
}
