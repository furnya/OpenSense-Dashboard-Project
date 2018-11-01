package com.opensense.dashboard.client.view;

import java.util.EnumMap;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;

public class NavigationPanelViewImpl extends Composite implements NavigationPanelView {

	@UiTemplate("NavigationPanelView.ui.xml")
	interface NavigationPanelViewUiBinder extends UiBinder<Widget, NavigationPanelViewImpl> {
	}
	
	private static NavigationPanelViewUiBinder uiBinder = GWT.create(NavigationPanelViewUiBinder.class);

	protected Presenter presenter;
	
	@UiField
	ButtonGroup buttonGroup;
	
	@UiField
	Div lastButtonCon;
	
	private final EnumMap<DataPanelPage, Button> navElements = new EnumMap<>(DataPanelPage.class);
	
	public NavigationPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		buildNavButtons();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	private void buildNavButtons() {
		for(final DataPanelPage page : DataPanelPage.values()) {
			Button navButton = new Button();
			navButton.addStyleName("nav-button");
			navButton.add(new Image(page.iconImagePath()));
			navButton.addClickHandler(event -> presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, false)));
			if(!page.hasBottomButton()) {
				buttonGroup.add(navButton);
			}else {
				lastButtonCon.add(navButton);
			}
			navElements.put(page, navButton);
		}
	}
	
	@Override
	public void setActiveDataPanelPage(DataPanelPage page) {
		navElements.entrySet().forEach(entry -> entry.getValue().setActive(entry.getKey().equals(page)));
	}
}
