package com.opensense.dashboard.client.view;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;

public class NavigationPanelViewImpl extends Composite implements NavigationPanelView {

	@UiTemplate("NavigationPanelView.ui.xml")
	interface NavigationPanelViewUiBinder extends UiBinder<Widget, NavigationPanelViewImpl> {
	}
	
	private static NavigationPanelViewUiBinder uiBinder = GWT.create(NavigationPanelViewUiBinder.class);

	protected Presenter presenter;
	
    @UiField
    Button homeButton;
    
    @UiField
    Button searchButton;
    
    @UiField
    Button mapButton;
    
    @UiField
    Button listButton;
    
    @UiField
    Button visualisationsButton;
    
    @UiField
    Button userButton;
    
	@UiField
	ButtonGroup buttonGroup;
	
	@UiField
	Div lastButtonCon;
	
	private Map<DataPanelPage, Button> navElements = new HashMap<>();
	
	public NavigationPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		buildButtons();
	}

	private void buildButtons() {
		homeButton.add(new Image(GUIImageBundle.INSTANCE.homeIcon().getSafeUri().asString()));
		homeButton.addClickHandler(event -> setActivePage("HOME"));
		searchButton.add(new Image(GUIImageBundle.INSTANCE.searchIcon().getSafeUri().asString()));
		searchButton.addClickHandler(event -> setActivePage("SEARCH"));
		mapButton.add(new Image(GUIImageBundle.INSTANCE.mapIcon().getSafeUri().asString()));
		mapButton.addClickHandler(event -> setActivePage("MAP"));
		listButton.add(new Image(GUIImageBundle.INSTANCE.listIcon().getSafeUri().asString()));
		listButton.addClickHandler(event -> setActivePage("LISTS"));
		visualisationsButton.add(new Image(GUIImageBundle.INSTANCE.diagramIcon().getSafeUri().asString()));
		visualisationsButton.addClickHandler(event -> setActivePage("VISUALISATIONS"));
		userButton.add(new Image(GUIImageBundle.INSTANCE.userIcon().getSafeUri().asString()));
		userButton.addClickHandler(event -> setActivePage("USER"));
//		for(final DataPanelPage page : DataPanelPage.values()) {
//			Button navButton = new Button();
//			navButton.addStyleName("nav-button");
//			navButton.add(new Image(page.iconImagePath()));
//			navButton.addClickHandler(event -> {
//				setActivePage(page);
//			});
//			if(!page.hasBottomButton()) {
//				buttonGroup.add(navButton);
//			}else {
//				lastButtonCon.add(navButton);
//			}
//			navElements.put(page, navButton);
//		}
	}

	private void setActivePage(String page) {
		navElements.entrySet().forEach(entry -> {
			entry.getValue().setActive(entry.getKey().equals(page));
			History.replaceItem(page, true);
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
