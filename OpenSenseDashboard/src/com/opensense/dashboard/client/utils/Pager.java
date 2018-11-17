package com.opensense.dashboard.client.utils;

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

	private static final String STYLE_CLICKABLE = "clickable";
	private static PagerUiBinder uiBinder = GWT.create(PagerUiBinder.class);
	
	interface PagerUiBinder extends UiBinder<Widget, Pager> {
	}
	
	private final SearchView view;
	//The different types how many Sensors are on one page
	@SuppressWarnings("unused")
	private String[] pageSize = {"2", "5", "10", "25", "50", "100"};
	
    @UiField
    Image backwardsButton;
    @UiField
    Image forwardsButton;
    @UiField
    Span pageNumber;
    @UiField
    Image backwardsStepByStepButton;
    @UiField
    Image forwardsStepByStepButton;
//    @UiField
//    Modal pageConfigurationModal;
//    @UiField
//    Button loadPageConfig;
//    @UiField
//    ListBox pagesListBox;
	
	public Pager(SearchView view) {
		initWidget(uiBinder.createAndBindUi(this));
		this.view = view;
	}
	
    @UiHandler("forwardsStepByStepButton")
	public void onForwardsStepByStepButtonClicked(ClickEvent e){
    	//If current page is the last do nothing 
		if(((int) Math.ceil((double) view.getShownSensorIds().size() / (double) view.getMaxSensorsOnPage())) - 1 < view.getSensorPage() + 1){
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
		view.setSensorPage(((int) Math.ceil((double) view.getShownSensorIds().size() / (double) view.getMaxSensorsOnPage())) - 1);
		view.pagination();
	}
	
	@UiHandler("backwardsButton")
	public void onBackwardsButtonClicked(ClickEvent e){
		//Goes to the first page
		view.setSensorPage(0);
		view.pagination();
	}
	
    public void setForwardsEnabled(final boolean enabled) {
    	if(enabled){
	    	forwardsButton.setUrl(GUIImageBundle.INSTANCE.forwardsBlue().getSafeUri().asString());
	    	forwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.forwardsStepByStepBlue().getSafeUri().asString());
	    	forwardsButton.addStyleName(STYLE_CLICKABLE);
	    	forwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
    	}else{
    		forwardsButton.setUrl(GUIImageBundle.INSTANCE.forwards().getSafeUri().asString());
	    	forwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.forwardsStepByStep().getSafeUri().asString());
	    	forwardsButton.removeStyleName(STYLE_CLICKABLE);
	    	forwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
    	}
    }
    
    public void setBackwardsEnabled(final boolean enabled) {
    	if(enabled){
    		backwardsButton.setUrl(GUIImageBundle.INSTANCE.backwardsBlue().getSafeUri().asString());
    		backwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.backwardsStepbyStepBlue().getSafeUri().asString());
    		backwardsButton.addStyleName(STYLE_CLICKABLE);
        	backwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
    	}else{
    		backwardsButton.setUrl(GUIImageBundle.INSTANCE.backwards().getSafeUri().asString());
    		backwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.backwardsStepbyStep().getSafeUri().asString());
    		backwardsButton.removeStyleName(STYLE_CLICKABLE);
    		backwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
    	}
    }
    
    public void setPage(String pageNumber){
    	this.pageNumber.setText(pageNumber);
    }
}