package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.model.PagerImages;
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
	
	private JavaScriptObject backwardsButtonHandler;
	private JavaScriptObject backwardsStepByStepButtonHandler;
	private JavaScriptObject forwardsButtonHandler;
	private JavaScriptObject forwardsStepByStepButtonHandler;
	private Map<Element, JavaScriptObject> mouseOutHandler = new HashMap<>();
	
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
    	mouseOutHandler.entrySet().forEach(entry -> {
    		removeListener(entry.getKey(), entry.getValue(), "mouseout");
    	});
    	mouseOutHandler.clear();
    	if(forwardsButtonHandler != null) {
    		removeListener(forwardsButton.getElement(), forwardsButtonHandler, "mouseover");
    		forwardsButtonHandler = null;
    	}
    	if(forwardsStepByStepButtonHandler != null) {
    		removeListener(forwardsStepByStepButton.getElement(), forwardsStepByStepButtonHandler, "mouseover");
    		forwardsStepByStepButtonHandler = null;
    	}
    	if(enabled){
    		forwardsButtonHandler = addMouseOverListener(forwardsButton.getElement(), forwardsButton, PagerImages.FORWARDS_BUTTON_HOVER.name());
    		forwardsStepByStepButtonHandler = addMouseOverListener(forwardsStepByStepButton.getElement(), forwardsStepByStepButton, PagerImages.FORWARDSSTEPBYSTEP_BUTTON_HOVER.name());
	    	forwardsButton.setUrl(PagerImages.FORWARDS_BUTTON_ACTIVE.getImageUrl());
	    	forwardsStepByStepButton.setUrl(PagerImages.FORWARDSSTEPBYSTEP_BUTTON_ACTIVE.getImageUrl());
	    	forwardsButton.addStyleName(STYLE_CLICKABLE);
	    	forwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
    	}else{
    		forwardsButton.setUrl(PagerImages.FORWARDSBUTTON_DISABLED.getImageUrl());
	    	forwardsStepByStepButton.setUrl(PagerImages.FORWARDSSTEPBYSTEP_BUTTON_DISABLED.getImageUrl());
	    	forwardsButton.removeStyleName(STYLE_CLICKABLE);
	    	forwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
    	}
    }
    
    public void setBackwardsEnabled(final boolean enabled) {
    	mouseOutHandler.entrySet().forEach(entry -> {
    		removeListener(entry.getKey(), entry.getValue(), "mouseout");
    	});
    	mouseOutHandler.clear();
    	if(backwardsButtonHandler != null) {
    		removeListener(backwardsButton.getElement(), backwardsButtonHandler, "mouseover");
    		backwardsButtonHandler = null;
    	}
    	if(backwardsStepByStepButtonHandler != null) {
    		removeListener(backwardsStepByStepButton.getElement(), backwardsStepByStepButtonHandler, "mouseover");
    		backwardsStepByStepButtonHandler = null;
    	}
    	if(enabled){
    		backwardsButtonHandler = addMouseOverListener(backwardsButton.getElement(), backwardsButton, PagerImages.BACKWARDS_BUTTON_HOVER.name());
    		backwardsStepByStepButtonHandler = addMouseOverListener(backwardsStepByStepButton.getElement(), backwardsStepByStepButton, PagerImages.BACKWARDSSTEPBYSTEP_BUTTON_HOVER.name());
    		backwardsButton.setUrl(PagerImages.BACKWARDS_BUTTON_ACTIVE.getImageUrl());
    		backwardsStepByStepButton.setUrl(PagerImages.BACKWARDSSTEPBYSTEP_BUTTON_ACTIVE.getImageUrl());
    		backwardsButton.addStyleName(STYLE_CLICKABLE);
        	backwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
    	}else{
    		backwardsButton.setUrl(PagerImages.BACKWARDS_BUTTON_DISABLED.getImageUrl());
    		backwardsStepByStepButton.setUrl(PagerImages.BACKWARDSSTEPBYSTEP_BUTTON_DISABLED.getImageUrl());
    		backwardsButton.removeStyleName(STYLE_CLICKABLE);
    		backwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
    	}
    }
    
	private native void removeListener(Element elem, JavaScriptObject handler, String type) /*-{
		elem.removeEventListener(type, handler);
	}-*/;
    
	private native JavaScriptObject addMouseOverListener(Element elem, Image image, String imageType) /*-{
		var that = this;
		var handler = function() { 
			that.@com.opensense.dashboard.client.utils.Pager::setImage(*)(image, imageType);
		};
		elem.addEventListener("mouseover", handler);
		return handler
	}-*/;
	
	private native JavaScriptObject addMouseOutListener(Element elem, Image image, String imageType) /*-{
		var that = this;
		var handler = function() { 
			that.@com.opensense.dashboard.client.utils.Pager::setImage(*)(image, imageType);
			elem.removeEventListener("mouseout", handler);
		};
		elem.addEventListener("mouseout", handler);
		return handler;
	}-*/;
	
	public void setImage(Image image, String imageType) {
		try{
			image.setUrl(PagerImages.valueOf(imageType).getImageUrl());
			if(imageType.contains("HOVER")) {
				mouseOutHandler.put(image.getElement(), addMouseOutListener(image.getElement(), image, PagerImages.valueOf(imageType.replace("HOVER", "ACTIVE")).name()));
			}
			}catch(Exception e) {
			//TODO:
			GWT.log("Exception");
		}
	}
	
    public void setPage(String pageNumber){
    	this.pageNumber.setText(pageNumber);
    }
}