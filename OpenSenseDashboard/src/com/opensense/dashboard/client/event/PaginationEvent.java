package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PaginationEvent extends GwtEvent<PaginationEventHandler>{

	public static final Type<PaginationEventHandler> TYPE = new Type<>();

	private int page;
	private int maxObjectsOnPage;

	public PaginationEvent(int page, int maxObjectsOnPage) {
		super();
		this.page = page;
		this.maxObjectsOnPage = maxObjectsOnPage;
	}

	@Override
	public Type<PaginationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PaginationEventHandler handler) {
		handler.onPagiantionEvent(this);
	}

	public int getPage() {
		return this.page;
	}

	public int getMaxObjectsOnPage() {
		return this.maxObjectsOnPage;
	}
}
