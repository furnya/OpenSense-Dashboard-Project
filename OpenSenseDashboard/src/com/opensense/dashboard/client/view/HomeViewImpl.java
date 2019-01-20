package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;

public class HomeViewImpl extends DataPanelPageView implements HomeView {

	@UiTemplate("HomeView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	protected Presenter presenter;

	@UiField
	Heading userInfo;
	@UiField
	Div searchCard;
	@UiField
	Div mapCard;
	@UiField
	Div visuCard;
	@UiField
	Div listCard;

	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		triggerEventOnCardClick();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		// init UI Elements if needed
	}

	public void setUserInfo(String userInfo) {
		this.userInfo.setText(userInfo);
	}

	public void triggerEventOnCardClick() {
		searchCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, null));

		}, ClickEvent.getType());

		mapCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, null));

		}, ClickEvent.getType());

		listCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.LISTS, true, null));

		}, ClickEvent.getType());

		visuCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, null));

		}, ClickEvent.getType());

	}

}
