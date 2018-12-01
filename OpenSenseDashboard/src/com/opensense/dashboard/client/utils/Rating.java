package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Rating extends Composite{
	
	@UiTemplate("Rating.ui.xml")
	interface RatingUiBinder extends UiBinder<Widget, Rating> {
	}
	
	private static final double STAR_WIDTH = 30.5;
	
	@UiField
	Div starContainer;
	
	@UiField
	Div overlay;
	
	private static RatingUiBinder uiBinder = GWT.create(RatingUiBinder.class);
	
	public Rating() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * integer from 0-5 sets the star rating (percentage how many star will be shown is needed 0-100%)
	 */
	public void setRating(double rating) {
		overlay.setWidth(STAR_WIDTH * (5 * (100 - rating) / 100) + 10 + "" + Unit.PX);
		starContainer.setTitle(Languages.rating(5 * (rating / 100)));
	}
}
