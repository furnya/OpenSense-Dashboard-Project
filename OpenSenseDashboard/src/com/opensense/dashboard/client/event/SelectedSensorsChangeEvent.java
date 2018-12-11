package com.opensense.dashboard.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class SelectedSensorsChangeEvent extends GwtEvent<SelectedSensorsChangeEventHandler>{

	public static final Type<SelectedSensorsChangeEventHandler> TYPE = new Type<>();

	private List<Integer> selectedIds;

	public SelectedSensorsChangeEvent(List<Integer> selectedIds) {
		super();
		this.selectedIds = selectedIds;
	}

	@Override
	public Type<SelectedSensorsChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectedSensorsChangeEventHandler handler) {
		handler.onSelectedSensorsChangeEvent(this);
	}

	public List<Integer> getSelectedIds() {
		return this.selectedIds;
	}
}
