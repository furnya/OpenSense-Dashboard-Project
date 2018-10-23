/**
 * 
 */
package com.opensense.dashboard.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;


/**
 * Interface for all the presenters.
 */
public abstract interface IPresenter {
	/**
	 * Fires the presenter. ("Present your view in the container!")
	 * @param container
	 * 	The container in which the presenter should display its view.
	 */
	public abstract void go(final HasWidgets container);
}
