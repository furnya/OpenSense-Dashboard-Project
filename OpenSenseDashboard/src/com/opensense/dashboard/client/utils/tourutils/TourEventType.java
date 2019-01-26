package com.opensense.dashboard.client.utils.tourutils;

public enum TourEventType {
	CLICK,
	/**
	 * On the element is a clickListener that will close the tour on click
	 */
	HINT,
	/**
	 * On the element is not a clickListener that the tour will close. The user can try to move the element and see the result in background.
	 */
	HINT_TRY_OUT,
	/**
	 * Binds a valueChangeListener on the element (element has to be {@link org.gwtbootstrap3.client.ui.Input}). Next tour step will be only shown if the user entered something
	 */
	INPUT;
}
