package com.opensense.dashboard.client.view;

import java.util.EnumMap;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;

import gwt.material.design.client.ui.MaterialButton;

public class NavigationPanelViewImpl extends Composite implements NavigationPanelView {

	@UiTemplate("NavigationPanelView.ui.xml")
	interface NavigationPanelViewUiBinder extends UiBinder<Widget, NavigationPanelViewImpl> {
	}
	
	private static NavigationPanelViewUiBinder uiBinder = GWT.create(NavigationPanelViewUiBinder.class);

	protected Presenter presenter;
	
	@UiField
	ButtonGroup buttonGroup;
	
	@UiField
	Div logoutContainer;
	
	@UiField
	Button lastButton;
	
	@UiField
	MaterialButton logout;
	
	private final EnumMap<DataPanelPage, Button> navElements = new EnumMap<>(DataPanelPage.class);
	
	private HandlerRegistration clickHandler;
	
	public NavigationPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		logoutContainer.getElement().getStyle().setDisplay(Display.NONE);
		buildNavButtons();
	}
	
	@UiHandler("logout")
	public void onLogoutButtonClicked(ClickEvent e) {
		presenter.onLogoutButtonClicked();
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
			if(!page.logoutButton()) {
				navButton.addClickHandler(event -> {
					showLogoutButton(false);
					presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true));
				});
				buttonGroup.add(navButton);
				navElements.put(page, navButton);
			}else {
				lastButton.add(new Image(page.iconImagePath()));
				lastButton.addClickHandler(event -> {
					if(presenter.isGuest()){
						presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true));
					}else {
						showLogoutButton(true);
					}
				});
				navElements.put(page, lastButton);
			}
		}
	}
	
	public void showLogoutButton(boolean show) {
		if(show) {
			new Timer() {@Override public void run() {
				clickHandler = RootPanel.get().addDomHandler(event -> showLogoutButton(false), ClickEvent.getType());
			}}.schedule(100);
			lastButton.addStyleName("remove-hover");
			logoutContainer.getElement().getStyle().clearDisplay();
		}else {
			if(clickHandler != null) {
				clickHandler.removeHandler();
			}
			lastButton.removeStyleName("remove-hover");
			logoutContainer.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@Override
	public void setActiveDataPanelPage(DataPanelPage page) {
		navElements.entrySet().forEach(entry -> entry.getValue().setActive(entry.getKey().equals(page)));
	}
}
