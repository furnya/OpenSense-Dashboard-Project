package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.AddSensorModal;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.view.ListsView;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;
import com.opensense.dashboard.shared.CreateSensorRequest;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;

public class ListsPresenter extends DataPanelPagePresenter implements IPresenter, ListsView.Presenter{

	private final ListsView view;

	private static final Logger LOGGER = Logger.getLogger(ListsPresenter.class.getName());


	public ListsPresenter(HandlerManager eventBus, AppController appController, ListsView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}

	public ListsView getView() {
		return this.view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
		Document.get().getElementById("content").addClassName("padding-right-10");
	}

	@Override
	public void onPageReturn() {
		this.view.getListManager().setUserLoggedInAndUpdate(!this.appController.isGuest());
		this.view.setCreateListButtonEnabled(!this.appController.isGuest());
		this.view.setCreateSensorButtonEnabled(!this.appController.isGuest());
	}

	@Override
	public void onPageLeave() {
		Document.get().getElementById("content").removeClassName("padding-right-10");
	}

	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleIds(List<Integer> ids) {
		this.view.getListManager().updateSelectedSensorsList(ids);
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		this.view.initView(runnable, !this.appController.isGuest());
		this.view.setCreateListButtonEnabled(!this.appController.isGuest());
		this.view.setCreateSensorButtonEnabled(!this.appController.isGuest());
	}

	public void updateFavoriteList() {
		this.view.getListManager().updateFavoriteList();
	}

	public void onUserLoggedIn() {
		this.view.getListManager().onUserLoggedIn();
		this.view.setCreateListButtonEnabled(true);
		this.view.setCreateSensorButtonEnabled(true);
	}

	public void onUserLoggedOut() {
		this.view.getListManager().onUserLoggedOut();
		this.view.setCreateListButtonEnabled(false);
		this.view.setCreateSensorButtonEnabled(false);
	}

	@Override
	public void requestDataAndShowCreateSensorModal() {
		AddSensorModal modal = new AddSensorModal(this);
		this.requestLicenses(modal);
		this.requestMeasurands(modal);
		this.requestUnits(modal);
		RootPanel.get().add(modal);
		modal.open();
	}

	private void requestLicenses(final AddSensorModal modal) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.LICENSE, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(),
				new DefaultAsyncCallback<Response>(result -> {
					if ((result != null) && (result.getResultType() != null)
							&& requestBuilder.getRequest().getRequestType().equals(result.getResultType())
							&& (result.getLicenses() != null)) {
						modal.setLicences(result.getLicenses());
					} else {
						LOGGER.log(Level.WARNING, "Failure requesting the units.");
					}
				}, caught -> {
					LOGGER.log(Level.WARNING, "Failure requesting the units.");
				}, false));
	}

	private void requestUnits(final AddSensorModal modal) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.UNIT, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(),
				new DefaultAsyncCallback<Response>(result -> {
					if ((result != null) && (result.getResultType() != null)
							&& requestBuilder.getRequest().getRequestType().equals(result.getResultType())
							&& (result.getUnits() != null)) {
						modal.setUnits(result.getUnits());
					} else {
						LOGGER.log(Level.WARNING, "Failure requesting the units.");
					}
				}, caught -> {
					LOGGER.log(Level.WARNING, "Failure requesting the units.");
				}, false));
	}

	private void requestMeasurands(final AddSensorModal modal) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.MEASURAND, false);
		GeneralService.Util.getInstance().getDataFromRequest(requestBuilder.getRequest(),
				new DefaultAsyncCallback<Response>(result -> {
					if ((result != null) && (result.getResultType() != null)
							&& requestBuilder.getRequest().getRequestType().equals(result.getResultType())
							&& (result.getMeasurands() != null)) {
						modal.setMeasurands(result.getMeasurands());
					} else {
						LOGGER.log(Level.WARNING, "Failure requesting the measurands.");
					}
				}, caught -> {
					LOGGER.log(Level.WARNING, "Failure requesting the measurands.");
				}, false));
	}

	@Override
	public void createSensorRequest(CreateSensorRequest request) {
		GeneralService.Util.getInstance().createSensor(request, new DefaultAsyncCallback<ActionResult>(result -> {
			if (result.getActionResultType() == ActionResultType.SUCCESSFUL) {
				this.view.getListManager().updateLists();
				AppController.showSuccess(result.getErrorMessage());
			}else {
				AppController.showError(result.getErrorMessage());
			}
		}, caught -> {
			LOGGER.log(Level.WARNING, "Failure creating the sensor.");
		}, false));
	}

}
