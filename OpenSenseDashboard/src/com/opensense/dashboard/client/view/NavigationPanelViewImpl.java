package com.opensense.dashboard.client.view;

import java.util.EnumMap;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;

import gwt.material.design.client.constants.Position;
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
	Button lastButton;

	@UiField
	MaterialTooltip lastButtonTooltip;

	private final EnumMap<DataPanelPage, Button> navElements = new EnumMap<>(DataPanelPage.class);

	public NavigationPanelViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.buildNavButtons();
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
				navButton.addClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true)));
				MaterialTooltip tooltip = new MaterialTooltip(navButton);
				tooltip.setText(page.displayName());
				tooltip.setPosition(Position.RIGHT);
				this.buttonGroup.add(tooltip);
				this.navElements.put(page, navButton);
			}else {
				this.lastButtonTooltip.setText(page.displayName());
				this.lastButton.add(new Image(GUIImageBundle.INSTANCE.userIconSvg().getSafeUri().asString()));
				this.lastButton.addClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(page, true)));
				this.navElements.put(page, this.lastButton);
			}
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
