package com.opensense.dashboard.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.opensense.dashboard.client.model.DataPanelPage;

public class OpenDataPanelPageEvent extends GwtEvent<OpenDataPanelPageEventHandler>{
	
	public static final Type<OpenDataPanelPageEventHandler> TYPE = new Type<>();
	
	private DataPanelPage dataPanelPage;
	private boolean issueHistoryEvent;
	
	public OpenDataPanelPageEvent(DataPanelPage dataPanelPage, boolean issueHistoryEvent) {
		super();
		this.dataPanelPage = dataPanelPage;
		this.setIssueHistoryEvent(issueHistoryEvent);
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

	public boolean getIssueHistoryEvent() {
		return issueHistoryEvent;
	}

	public void setIssueHistoryEvent(boolean issueHistoryEvent) {
		this.issueHistoryEvent = issueHistoryEvent;
	}

}
