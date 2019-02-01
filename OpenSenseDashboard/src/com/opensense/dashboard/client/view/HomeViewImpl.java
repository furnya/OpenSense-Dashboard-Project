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
import com.opensense.dashboard.client.event.StartTourEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.tourutils.Tours;

public class HomeViewImpl extends DataPanelPageView implements HomeView {

	@UiTemplate("HomeView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	protected Presenter presenter;
	/*welcomePageCard*/
	@UiField
	Heading userInfo;
	@UiField
	Span welcomeText;

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

	@Override
	public void setUserInfo(String userInfo) {
		this.userInfo.setText(userInfo);
	}

	public void setAllCardInfos() {
		setWelcomeCardText();
		setSearchCardText();
		setMapCardText();
		setVisuCardText();
		setListCardText();
	}

	public void setWelcomeCardText(){
		this.welcomeText.setText(Languages.welcomeInfoText());
		this.welcomeText.setColor(this.whiteColor);
		this.welcomeText.setMarginLeft(40);
		this.welcomeText.setMarginRight(40);
	}


	public void setSearchCardText(){
		this.searchCardInfo.setText("Rundgang für die Suche von Sensoren");
		this.searchCardText.setText(Languages.searchInfoText());
		this.searchCardInfo.setColor(this.whiteColor);
	}


	public void setMapCardText(){
		this.mapCardInfo.setText(Languages.map());
		this.mapCardText.setText(Languages.mapInfoText());
		this.mapCardInfo.setColor(this.whiteColor);
	}



	public void setVisuCardText(){
		this.visuCardInfo.setText(Languages.graphics());
		this.visuCardText.setText(Languages.visuInfoText());
		this.visuCardInfo.setColor(this.whiteColor);
	}

	public void setListCardText(){
		this.listCardInfo.setText(Languages.list());
		this.listCardText.setText(Languages.listInfoText());
		this.listCardInfo.setColor(this.whiteColor);
	}

	public void triggerEventOnCardClick() {
		this.searchCard.addDomHandler(event -> {
			this.presenter.getEventBus().fireEvent(new StartTourEvent(Tours.SEARCH_PAGE, true));
			//			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, null));

		}, ClickEvent.getType());

		this.mapCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new StartTourEvent(Tours.MAP_PAGE, true));

		}, ClickEvent.getType());

		this.listCard.addDomHandler(event -> {

			this.presenter.getEventBus().fireEvent(new StartTourEvent(Tours.LIST_PAGE, true));

		}, ClickEvent.getType());

		this.visuCard.addDomHandler(event -> {
			this.presenter.getEventBus().fireEvent(new StartTourEvent(Tours.VIS_PAGE, true));
//			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, null));

		}, ClickEvent.getType());

	}

}
