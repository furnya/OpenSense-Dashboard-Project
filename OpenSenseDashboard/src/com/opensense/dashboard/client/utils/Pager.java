package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.PaginationEvent;
import com.opensense.dashboard.client.event.PaginationEventHandler;

import gwt.material.design.client.ui.MaterialButton;

public class Pager extends Composite{

	private static PagerUiBinder uiBinder = GWT.create(PagerUiBinder.class);

	interface PagerUiBinder extends UiBinder<Widget, Pager> {
	}

	@UiField
	MaterialButton backwardsButton;

	@UiField
	MaterialButton forwardsButton;

	@UiField
	Span pageNumber;

	@UiField
	MaterialButton backwardsStepByStepButton;

	@UiField
	MaterialButton forwardsStepByStepButton;

	private int maxObjectsOnPage = 20;
	private int page = 0;

	private Pager dependentPager;

	private PaginationEventHandler paginationEventHandler;

	public Pager() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void addForwardsStepByStepClickHandler(ClickHandler handler) {
		this.forwardsStepByStepButton.addClickHandler(handler);
	}

	public void onForwardsStepByStepButtonClicked(int showObjectsSize){
		//If current page is the last do nothing
		if((this.page + 1) > (((int) Math.ceil(showObjectsSize / (double) this.maxObjectsOnPage)) - 1)){
			return;
		}
		this.page = this.page + 1;
		this.update(showObjectsSize, true);
	}

	public void addBackwardsStepByStepClickHandler(ClickHandler handler) {
		this.backwardsStepByStepButton.addClickHandler(handler);
	}

	public void onBackwardsStepByStepButtonClicked(int showObjectsSize){
		//If current page is the first do nothing
		if((this.page - 1) < 0){
			return;
		}
		this.page = this.page - 1;
		this.update(showObjectsSize, true);
	}

	public void addForwardsButtonClickHandler(ClickHandler handler) {
		this.forwardsButton.addClickHandler(handler);
	}

	public void onForwardsButtonClicked(int showObjectsSize){
		//Goes to the last page
		this.page = ((int) Math.ceil(showObjectsSize / (double) this.maxObjectsOnPage)) - 1;
		this.update(showObjectsSize, true);
	}

	public void addBackwardsButtonClickHandler(ClickHandler handler) {
		this.backwardsButton.addClickHandler(handler);
	}

	public void onBackwardsButtonClicked(int showObjectsSize){
		//Goes to the first page
		this.page = 0;
		this.update(showObjectsSize, true);
	}

	public void setForwardsEnabled(boolean enabled) {
		this.forwardsButton.setEnabled(enabled);
		this.forwardsStepByStepButton.setEnabled(enabled);
	}

	public void setBackwardsEnabled(boolean enabled) {
		this.backwardsButton.setEnabled(enabled);
		this.backwardsStepByStepButton.setEnabled(enabled);
	}

	public void setPage(int page){
		this.page = page;
	}

	public void setMaxObjectsOnPage(int maxObjectsOnPage) {
		this.maxObjectsOnPage = maxObjectsOnPage;
	}

	public void addPaginationEventHandler(PaginationEventHandler handler) {
		this.paginationEventHandler = handler;
	}

	public void clearPager() {
		this.pageNumber.setText(Languages.setPageNumber(0, 0, 0));
		this.setForwardsEnabled(false);
		this.setBackwardsEnabled(false);
		this.page = 0;
	}

	public void update(int showObjectsSize, boolean fireEvent) {
		this.pageNumber.setText(Languages.setPageNumber(this.page, this.maxObjectsOnPage, showObjectsSize));
		this.setForwardsEnabled((this.page + 1) < ((int) Math.ceil(showObjectsSize / (double) this.maxObjectsOnPage)));
		this.setBackwardsEnabled(this.page > 0);
		if(fireEvent && (this.paginationEventHandler != null)) {
			if(this.dependentPager != null) {
				this.dependentPager.setPage(this.page);
				this.dependentPager.update(showObjectsSize, false);
			}
			this.paginationEventHandler.onPagiantionEvent(new PaginationEvent(this.page, this.maxObjectsOnPage));
		}
	}

	public void setDependentPager(Pager dependentPager) {
		this.dependentPager = dependentPager;
	}
}