package com.opensense.dashboard.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Star extends Composite{
	
	@UiTemplate("Star.ui.xml")
	interface StarUiBinder extends UiBinder<Widget, Star> {
	}
	
	private static StarUiBinder uiBinder = GWT.create(StarUiBinder.class);
	
	public Star() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
