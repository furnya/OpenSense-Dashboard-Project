package com.opensense.dashboard.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.FooterView;

/**
 * Displays the footer functionality which is at the moment only the language switch
 * @author carlr
 *
 */
public class FooterPresenter implements IPresenter, FooterView.Presenter{

	private final FooterView view;
	private final AppController appController;
	private final HandlerManager eventBus;

	public FooterPresenter(HandlerManager eventBus, AppController appController, FooterView view) {
		this.appController = appController;
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	public FooterView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	@Override
	public void switchLanguage(String lang) {
		this.appController.switchLanguage(lang);
	}
}
