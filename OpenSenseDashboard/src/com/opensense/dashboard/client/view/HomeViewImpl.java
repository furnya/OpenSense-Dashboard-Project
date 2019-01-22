package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;

public class HomeViewImpl extends DataPanelPageView implements HomeView {

	@UiTemplate("HomeView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	protected Presenter presenter;

	@UiField
	Heading userInfo;
	
	/* searchPage */
	@UiField
	Div searchCard;
	@UiField
	Heading searchCardInfo;
	@UiField
	Span searchCardText;
	
	/* mapPage */
	@UiField
	Div mapCard;
	@UiField
	Heading mapCardInfo;
	@UiField
	Span mapCardText;
	
	/* visuPage */
	@UiField
	Div visuCard;
	@UiField
	Heading visuCardInfo;
	@UiField
	Span visuCardText;
	
	/* listPage */
	@UiField
	Div listCard;
	@UiField
	Heading listCardInfo;
	@UiField
	Span listCardText;
	
	private String whiteColor = "white";

	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		triggerEventOnCardClick();
		setAllCardInfos();
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
	
	public void setAllCardInfos() {
		setSearchCardText();
		setMapCardText();
		setVisuCardText();
		setListCardText();
	}
	
	
	public void setSearchCardText(){
		this.searchCardInfo.setText(Languages.search());
		this.searchCardText.setText(Languages.searchInfoText());
		this.searchCardInfo.setColor(whiteColor);
	}
	
	
	public void setMapCardText(){
		this.mapCardInfo.setText(Languages.map());
		this.mapCardText.setText(Languages.mapInfoText());
		this.mapCardInfo.setColor(whiteColor);
	}
	
	
	
	public void setVisuCardText(){
		this.visuCardInfo.setText(Languages.graphics());
		this.visuCardText.setText(Languages.visuInfoText());
		this.visuCardInfo.setColor(whiteColor);
	}
	
	public void setListCardText(){
		this.listCardInfo.setText(Languages.list());
		this.listCardText.setText(Languages.listInfoText());
		this.listCardInfo.setColor(whiteColor);
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
