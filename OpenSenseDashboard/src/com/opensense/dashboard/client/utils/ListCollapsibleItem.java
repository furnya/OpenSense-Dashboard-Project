package com.opensense.dashboard.client.utils;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.ListNameChangedEvent;
import com.opensense.dashboard.client.event.ListNameChangedEventHandler;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialTextBox;

public class ListCollapsibleItem extends Composite{

	@UiTemplate("ListCollapsibleItem.ui.xml")
	interface ListCollapsibleItemUiBinder extends UiBinder<Widget, ListCollapsibleItem> {
	}

	@UiField
	MaterialCollapsibleItem sensorItem;

	@UiField
	MaterialImage deleteButton;

	@UiField
	MaterialImage listIcon;

	@UiField
	Span listItemName;

	@UiField
	Pager pagerTop;

	@UiField
	Pager pagerBottom;

	@UiField
	MaterialPreLoader listItemSpinner;

	@UiField
	Div noDataIndicator;

	@UiField
	Div sensorContainer;

	@UiField
	MaterialTextBox listNameInput;

	@UiField
	MaterialButton showOnMapButton;

	@UiField
	MaterialButton showVisualizationsButton;

	@UiField
	MaterialButton selectAllButton;

	@UiField
	MaterialButton addToListButton;

	@UiField
	MaterialDropDown listDropDown;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	private HandlerRegistration clickHandler;
	private HandlerRegistration enterHandler;

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	public void setName(String name) {
		this.listItemName.setText(name);
	}

	public void addDeleteButtonClickHandler(ClickHandler handler) {
		this.deleteButton.getElement().getStyle().clearDisplay();
		this.deleteButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}

	public void addListNameInputHandler(final ListNameChangedEventHandler handler) {
		this.listItemName.addStyleName("list-name");
		this.listItemName.addDomHandler(event -> {
			event.stopPropagation();
			this.listNameInput.setValue(this.listItemName.getText());
			this.listNameInput.setSelectionRange(0, this.listItemName.getText().length());
			this.deleteButton.getElement().getStyle().setDisplay(Display.NONE);
			this.listNameInput.getElement().getStyle().clearDisplay();
			this.focusElement(this.listNameInput.getElement());
			this.enterHandler = RootPanel.get().addDomHandler(keyDownEvent -> {
				if(keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					this.onListNameChangedEvent(handler);
				}
			}, KeyDownEvent.getType());
			this.clickHandler = RootPanel.get().addDomHandler(domClickEvent -> {
				domClickEvent.stopPropagation();
				this.onListNameChangedEvent(handler);
			}, ClickEvent.getType());
		}, ClickEvent.getType());
	}

	private void onListNameChangedEvent(ListNameChangedEventHandler handler) {
		this.clickHandler.removeHandler();
		this.enterHandler.removeHandler();
		final String listName = this.listNameInput.getValue();
		this.listItemName.setText(listName);
		this.deleteButton.getElement().getStyle().clearDisplay();
		this.listNameInput.getElement().getStyle().setDisplay(Display.NONE);
		handler.onListNameChangedEvent(new ListNameChangedEvent(listName));
	}

	private native void focusElement(Element elem) /*-{
		elem.firstChild.focus()
	}-*/;

	public Div getSensorContainer() {
		return this.sensorContainer;
	}

	public Pager getTopPager() {
		return this.pagerTop;
	}

	public Pager getBottomPager() {
		return this.pagerBottom;
	}

	public Div getNoDataIndicator() {
		return this.noDataIndicator;
	}

	public void setListIcon(String url) {
		this.listIcon.setUrl(url);
		this.listIcon.getElement().getStyle().clearDisplay();
	}

	public MaterialCollapsibleItem getCollapsibleItem() {
		return this.sensorItem;
	}
}
