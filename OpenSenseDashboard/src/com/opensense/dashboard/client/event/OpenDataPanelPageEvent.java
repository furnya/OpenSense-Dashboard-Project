package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.opensense.dashboard.client.model.DataPanelPage;

public class OpenDataPanelPageEvent extends GwtEvent<OpenDataPanelPageEventHandler>{
	
	public static final Type<OpenDataPanelPageEventHandler> TYPE = new Type<>();
	
	private DataPanelPage dataPanelPage;
	
	public OpenDataPanelPageEvent(DataPanelPage dataPanelPage) {
		super();
		this.dataPanelPage = dataPanelPage;
	}
	
	@Override
	public Type<OpenDataPanelPageEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenDataPanelPageEventHandler handler) {
		handler.onOpenDataPanelPageEvent(this);
	}

	public DataPanelPage getDataPanelPage() {
		return dataPanelPage;
	}

	public void setDataPanelPage(DataPanelPage dataPanelPage) {
		this.dataPanelPage = dataPanelPage;
	}
}
