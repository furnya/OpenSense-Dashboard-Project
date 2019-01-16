package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.placeslib.Autocomplete;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.AddSensorModal.AddSensorModalUiBinder;
import com.opensense.dashboard.shared.License;
import com.opensense.dashboard.shared.Measurand;
import com.opensense.dashboard.shared.Unit;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.ui.MaterialTextBox;

public class MasonryCardManager extends Composite {
	@UiTemplate("MasonryCard.ui.xml")
	interface MasonryCardUiBinder extends UiBinder<Widget, MasonryCardManager> {
	}
	
	@UiField
	MaterialMasonry masonry;

	private static MasonryCardUiBinder uiBinder = GWT.create(MasonryCardUiBinder.class);
	
	private static final Logger LOGGER = Logger.getLogger(MasonryCardManager.class.getName());
	
	private MaterialMasonry masonryCard;
	
	 private int[] items = new int[]{200,100,150};
	 
	 public int getRandomHeight(){
	        return items[Random.nextInt(items.length)];
	    }
		
	public MasonryCardManager(final MaterialMasonry masonry) {
		
		initWidget(uiBinder.createAndBindUi(this));
		this.masonry = masonry;
		setHeight(getRandomHeight() + "px");
	}
	
	
}
