package com.opensense.dashboard.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface GUIImageBundle extends ClientBundle {
	
	public static final GUIImageBundle INSTANCE =  GWT.create(GUIImageBundle.class);
	
	@Source("icons/home.png")
	ImageResource homeIcon();
	
	@Source("icons/search.png")
	ImageResource searchIcon();
	
	@Source("icons/diagram.png")
	ImageResource diagramIcon();
	
	@Source("icons/list.png")
	ImageResource listIcon();
	
	@Source("icons/user.png")
	ImageResource userIcon();
	
	@Source("icons/map.png")
	ImageResource mapIcon();
}
