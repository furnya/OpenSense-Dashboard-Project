package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.model.Size;

import gwt.material.design.client.ui.MaterialTooltip;

/**
 * Used to display the accuracy as star rating
 * @author carlr
 *
 */
public class Rating extends Composite{

	@UiTemplate("Rating.ui.xml")
	interface RatingUiBinder extends UiBinder<Widget, Rating> {
	}

	private static final double STAR_WIDTH_MEDIUM = 30.5;
	private static final double STAR_WIDTH_SMALL = 20.1;

	private double starWidth = 0;
	private int addToWidth = 0;

	@UiField
	Div starContainer;

	@UiField
	Div overlay;

	@UiField
	MaterialTooltip toolip;

	private static RatingUiBinder uiBinder = GWT.create(RatingUiBinder.class);

	public Rating() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * integer from 0-5 sets the star rating (percentage how many star will be shown is needed 0-100%)
	 */
	public void setRating(double rating) {
		this.overlay.setWidth((this.starWidth * ((5 * (100 - rating)) / 100)) + this.addToWidth + "" + Unit.PX);
		this.toolip.setText(Languages.rating(5 * (rating / 100)));
	}

	/**
	 * implemented only size.SMALL and size.MEDIUM
	 * @param size
	 */
	public void setSize(Size size) {
		this.starContainer.getElement().setAttribute("size", size.name().toLowerCase());
		switch (size) {
		case LARGE:
			//not implemented
			break;
		case MEDIUM:
			this.starWidth = STAR_WIDTH_MEDIUM;
			this.addToWidth = 10;
			break;
		case SMALL:
			this.starWidth = STAR_WIDTH_SMALL;
			this.addToWidth = 15;
			break;
		default:
			break;
		}
	}
}
