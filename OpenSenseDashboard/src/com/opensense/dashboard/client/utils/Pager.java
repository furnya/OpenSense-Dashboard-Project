package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.view.SearchView;

public class Pager extends Composite{

	private static PagerUiBinder uiBinder = GWT.create(PagerUiBinder.class);

	interface PagerUiBinder extends UiBinder<Widget, Pager> {
	}

	private final SearchView view;

	@UiField
	Button backwardsButton;

	@UiField
	Button forwardsButton;

	@UiField
	Span pageNumber;

	@UiField
	Button backwardsStepByStepButton;

	@UiField
	Button forwardsStepByStepButton;

	public Pager(SearchView view) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.view = view;

		this.forwardsButton.add(new Image(GUIImageBundle.INSTANCE.forwards().getSafeUri().asString()));
		this.forwardsStepByStepButton.add(new Image(GUIImageBundle.INSTANCE.forwardsStepByStep().getSafeUri().asString()));
		this.backwardsButton.add(new Image(GUIImageBundle.INSTANCE.backwards().getSafeUri().asString()));
		this.backwardsStepByStepButton.add(new Image(GUIImageBundle.INSTANCE.backwardsStepbyStep().getSafeUri().asString()));
	}

	@UiHandler("forwardsStepByStepButton")
	public void onForwardsStepByStepButtonClicked(ClickEvent e){
		//If current page is the last do nothing
		if((((int) Math.ceil(this.view.getShownSensorIds().size() / this.view.getMaxSensorsOnPage())) - 1) < (this.view.getSensorPage() + 1)){
			return;
		}
		this.view.setSensorPage(this.view.getSensorPage() + 1);
		this.view.pagination();
	}

	@UiHandler("backwardsStepByStepButton")
	public void onBackwardsStepByStepButtonClicked(ClickEvent e){
		//If current page is the first do nothing
		if((this.view.getSensorPage() - 1) < 0){
			return;
		}
		this.view.setSensorPage(this.view.getSensorPage() - 1);
		this.view.pagination();
	}
	@UiHandler("forwardsButton")
	public void onForwardsButtonClicked(ClickEvent e){
		//Goes to the last page
		this.view.setSensorPage(((int) Math.ceil(this.view.getShownSensorIds().size() / this.view.getMaxSensorsOnPage())) - 1);
		this.view.pagination();
	}

	@UiHandler("backwardsButton")
	public void onBackwardsButtonClicked(ClickEvent e){
		//Goes to the first page
		this.view.setSensorPage(0);
		this.view.pagination();
	}

	public void setForwardsEnabled(boolean enabled) {
		this.forwardsButton.setEnabled(enabled);
		this.forwardsStepByStepButton.setEnabled(enabled);
	}

	public void setBackwardsEnabled(boolean enabled) {
		this.backwardsButton.setEnabled(enabled);
		this.backwardsStepByStepButton.setEnabled(enabled);
	}

	public void setPage(String pageNumber){
		this.pageNumber.setText(pageNumber);
	}
}