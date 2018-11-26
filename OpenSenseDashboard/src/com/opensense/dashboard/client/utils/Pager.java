package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.view.SearchView;

public class Pager extends Composite{

	private static PagerUiBinder uiBinder = GWT.create(PagerUiBinder.class);
	
	interface PagerUiBinder extends UiBinder<Widget, Pager> {
	}
	
	private final SearchView view;
	//The different types how many Sensors are on one page
	@SuppressWarnings("unused")
	private String[] pageSize = {"2", "5", "10", "25", "50", "100"};
	
    @UiField
    Button backwardsButton;
    
    @UiField
    Button forwardsButton;
    
    @UiField
    Span pageNumber;
    
    @UiField
    Button backwardsStepByStepButton;
    
    @UiField
    Button forwardsStepByStepButton;
    
	public Pager(SearchView view) {
		initWidget(uiBinder.createAndBindUi(this));
		this.view = view;
		
    	forwardsButton.add(new Image(GUIImageBundle.INSTANCE.forwards().getSafeUri().asString()));
    	forwardsStepByStepButton.add(new Image(GUIImageBundle.INSTANCE.forwardsStepByStep().getSafeUri().asString()));
		backwardsButton.add(new Image(GUIImageBundle.INSTANCE.backwards().getSafeUri().asString()));
		backwardsStepByStepButton.add(new Image(GUIImageBundle.INSTANCE.backwardsStepbyStep().getSafeUri().asString()));
	}
	
    @UiHandler("forwardsStepByStepButton")
	public void onForwardsStepByStepButtonClicked(ClickEvent e){
    	//If current page is the last do nothing 
		if(((int) Math.ceil((double) view.getShownSensorIds().size() / view.getMaxSensorsOnPage())) - 1 < view.getSensorPage() + 1){
			return;
		}
    	view.setSensorPage(view.getSensorPage() + 1);
		view.pagination();
	}
	
	@UiHandler("backwardsStepByStepButton")
	public void onBackwardsStepByStepButtonClicked(ClickEvent e){
		//If current page is the first do nothing 
		if(view.getSensorPage() - 1 < 0){
			return;
		}
		view.setSensorPage(view.getSensorPage() - 1);
		view.pagination();
	}
	@UiHandler("forwardsButton")
	public void onForwardsButtonClicked(ClickEvent e){
		//Goes to the last page
		view.setSensorPage(((int) Math.ceil((double) view.getShownSensorIds().size() / view.getMaxSensorsOnPage())) - 1);
		view.pagination();
	}
	
	@UiHandler("backwardsButton")
	public void onBackwardsButtonClicked(ClickEvent e){
		//Goes to the first page
		view.setSensorPage(0);
		view.pagination();
	}
	
    public void setForwardsEnabled(boolean enabled) {
    	forwardsButton.setEnabled(enabled);
    	forwardsStepByStepButton.setEnabled(enabled);
    }
    
    public void setBackwardsEnabled(boolean enabled) {
    	backwardsButton.setEnabled(enabled);
		backwardsStepByStepButton.setEnabled(enabled);
    }
    
    public void setPage(String pageNumber){
    	this.pageNumber.setText(pageNumber);
    }
}