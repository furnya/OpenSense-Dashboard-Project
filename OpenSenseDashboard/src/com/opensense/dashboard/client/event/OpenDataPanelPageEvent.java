package com.opensense.dashboard.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.shared.Parameter;

public class OpenDataPanelPageEvent extends GwtEvent<OpenDataPanelPageEventHandler>{
	
	public static final Type<OpenDataPanelPageEventHandler> TYPE = new Type<>();
	
	private DataPanelPage dataPanelPage;
	private List<Parameter> parameters;
	private List<Integer> ids;
	private boolean fireEvent;
	
	public OpenDataPanelPageEvent(DataPanelPage dataPanelPage, boolean fireEvent) {
		super();
		this.dataPanelPage = dataPanelPage;
		this.fireEvent = fireEvent;
	}
	
	public OpenDataPanelPageEvent(DataPanelPage dataPanelPage, List<Parameter> parameters, boolean fireEvent) {
		super();
		this.dataPanelPage = dataPanelPage;
		this.parameters = parameters;
		this.fireEvent = fireEvent;
	}
	
	public OpenDataPanelPageEvent(DataPanelPage dataPanelPage, boolean fireEvent, List<Integer> ids) {
		super();
		this.dataPanelPage = dataPanelPage;
		this.ids = ids;
		this.fireEvent = fireEvent;
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
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public List<Integer> getIds() {
		return ids;
	}
	
	public boolean isFireEvent() {
		return fireEvent;
	}

}
