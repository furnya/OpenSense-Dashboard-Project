package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;

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
    
	public NavigationPanelViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		bindImages();
	}

	private void bindImages() {
		homeButton.add(new Image(GUIImageBundle.INSTANCE.homeIcon()));
		searchButton.add(new Image(GUIImageBundle.INSTANCE.searchIcon()));
		mapButton.add(new Image(GUIImageBundle.INSTANCE.mapIcon()));
		listButton.add(new Image(GUIImageBundle.INSTANCE.listIcon()));
		visualisationsButton.add(new Image(GUIImageBundle.INSTANCE.diagramIcon()));
		userButton.add(new Image(GUIImageBundle.INSTANCE.userIcon()));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
