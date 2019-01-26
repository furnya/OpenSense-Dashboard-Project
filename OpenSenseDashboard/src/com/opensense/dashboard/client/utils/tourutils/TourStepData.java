package com.opensense.dashboard.client.utils.tourutils;

import org.gwtbootstrap3.client.ui.constants.Placement;

import com.opensense.dashboard.client.utils.Rectangle;

public class TourStepData {

	private String id;
	private String message;
	private Placement placement;
	private TourEventType tourEventType = TourEventType.CLICK;
	private Rectangle addingRectangleStates;

	public TourStepData(String id, String message, Placement placement, TourEventType tourEventType) {
		this.id = id;
		this.message = message;
		this.placement = placement;
		this.tourEventType = tourEventType;
	}

	public TourStepData(String id, String message, Placement placement, Rectangle addingRectangleStates, TourEventType tourEventType) {
		this.id = id;
		this.message = message;
		this.placement = placement;
		this.addingRectangleStates = addingRectangleStates;
		this.tourEventType = tourEventType;
	}

	/**
	 * default: tourEventType = TourEventType.CLICK_EVENT
	 * @param message
	 * @param placement
	 */
	public TourStepData(String id, String message, Placement placement) {
		this.id = id;
		this.message = message;
		this.placement = placement;
	}

	/**
	 * default: tourEventType = TourEventType.CLICK_EVENT
	 * @param message
	 * @param placement
	 */
	public TourStepData(String id, String message, Placement placement, Rectangle addingRectangleStates) {
		this.id = id;
		this.message = message;
		this.placement = placement;
		this.addingRectangleStates = addingRectangleStates;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Placement getPlacement() {
		return this.placement;
	}
	public void setPlacement(Placement placement) {
		this.placement = placement;
	}
	public TourEventType getTourEventType() {
		return this.tourEventType;
	}
	public void setTourEventType(TourEventType tourEventType) {
		this.tourEventType = tourEventType;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Rectangle getAddingRectangleStates() {
		return this.addingRectangleStates;
	}
	public void setAddingRectangleStates(Rectangle addingRectangleStates) {
		this.addingRectangleStates = addingRectangleStates;
	}
}
