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
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;

import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTooltip;

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

	@UiField
	MaterialTooltip lastButtonTooltip;

	private final EnumMap<DataPanelPage, Button> navElements = new EnumMap<>(DataPanelPage.class);

	private HandlerRegistration clickHandler;

	public NavigationPanelViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.logoutContainer.getElement().getStyle().setDisplay(Display.NONE);
		this.buildNavButtons();
	}

	@UiHandler("logout")
	public void onLogoutButtonClicked(ClickEvent e) {
		this.presenter.onLogoutButtonClicked();
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
					this.showLogoutButton(false);
					this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true));
				});
				MaterialTooltip tooltip = new MaterialTooltip(navButton);
				tooltip.setText(page.displayName());
				tooltip.setPosition(Position.RIGHT);
				this.buttonGroup.add(tooltip);
				this.navElements.put(page, navButton);
			}else {
				this.lastButtonTooltip.setText(page.displayName());
				this.lastButton.add(new Image(GUIImageBundle.INSTANCE.userIconSvg().getSafeUri().asString()));
				this.lastButton.addClickHandler(event -> {
					if(this.presenter.isGuest()){
						this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true));
					}else {
						this.showLogoutButton(true);
					}
				});
				this.navElements.put(page, this.lastButton);
			}
		}
	}

	public void showLogoutButton(boolean show) {
		if(this.clickHandler != null) {
			this.clickHandler.removeHandler();
		}
		if(show) {
			new Timer() {@Override public void run() {
				NavigationPanelViewImpl.this.clickHandler = RootPanel.get().addDomHandler(event -> NavigationPanelViewImpl.this.showLogoutButton(false), ClickEvent.getType());
			}}.schedule(100);
			this.lastButtonTooltip.remove();
			this.lastButton.addStyleName("remove-hover");
			this.logoutContainer.getElement().getStyle().clearDisplay();
		}else {
			this.lastButton.removeStyleName("remove-hover");
			this.lastButtonTooltip.reinitialize();
			this.logoutContainer.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	@Override
	public void setActiveDataPanelPage(DataPanelPage page) {
		this.navElements.entrySet().forEach(entry -> entry.getValue().setActive(entry.getKey().equals(page)));
	}

	@Override
	public void setLastButtonActive(boolean active) {
		this.lastButton.clear();
		if(active) {
			this.lastButton.add(new Image(GUIImageBundle.INSTANCE.userIconActive().getSafeUri().asString()));
			this.lastButton.addStyleName("filter-none");
		} else {
			this.lastButton.add(new Image(GUIImageBundle.INSTANCE.userIconSvg().getSafeUri().asString()));
			this.lastButton.removeStyleName("filter-none");
		}
	}
}
