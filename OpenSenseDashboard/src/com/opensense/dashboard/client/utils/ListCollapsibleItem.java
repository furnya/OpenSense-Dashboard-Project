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
import com.opensense.dashboard.client.event.SensorSelectionEvent;
import com.opensense.dashboard.client.event.SensorSelectionEventHandler;
import com.opensense.dashboard.client.model.Size;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialImage;
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
	Spinner listItemSpinner;

	@UiField
	Div noDataIndicator;

	@UiField
	Div sensorContainer;

	@UiField
	MaterialTextBox listNameInput;

	@UiField
	MaterialButton showOnMapButton;

	@UiField
	MaterialButton showOnSearchButton;

	@UiField
	MaterialButton showVisualizationsButton;

	@UiField
	MaterialButton selectAllButton;

	@UiField
	MaterialButton addToListButton;

	@UiField
	MaterialDropDown listDropDown;

	@UiField
	MaterialCollapsibleHeader header;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	private HandlerRegistration clickHandler;
	private HandlerRegistration enterHandler;

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.showNoDataIndicator(false);
	}

	public void setSpinnerSize(Size spinnerSize) {
		this.listItemSpinner.setSize(spinnerSize);
	}

	public void setName(String name) {
		this.listItemName.setText(name);
	}

	public void addSelectAllButtonClickHandler(SensorSelectionEventHandler handler) {
		this.selectAllButton.getElement().getStyle().clearDisplay();
		this.selectAllButton.addClickHandler(event -> {
			if(Languages.selectAllSensors().equals(this.selectAllButton.getText())) {
				handler.onSensorSelectionEvent(new SensorSelectionEvent(true));
				this.changeToSelectAll(false);
			}else {
				handler.onSensorSelectionEvent(new SensorSelectionEvent(false));
				this.changeToSelectAll(true);
			}
		});
	}

	public void addShowVisualizationsButtonClickHandler(ClickHandler handler) {
		this.showVisualizationsButton.getElement().getStyle().clearDisplay();
		this.showVisualizationsButton.addClickHandler(handler);
	}

	public void addShowSearchButtonClickHandler(ClickHandler handler) {
		this.showOnSearchButton.getElement().getStyle().clearDisplay();
		this.showOnSearchButton.addClickHandler(handler);
	}

	public void addShowOnMapButtonClickHandler(ClickHandler handler) {
		this.showOnMapButton.getElement().getStyle().clearDisplay();
		this.showOnMapButton.addClickHandler(handler);
	}

	public void addAddToListButtonButtonClickHandler(ClickHandler handler) {
		this.addToListButton.getElement().getStyle().clearDisplay();
		this.addToListButton.addClickHandler(handler);
	}

	public void setSelectAllButtonEnabled(boolean enabled) {
		this.selectAllButton.setEnabled(enabled);
	}

	public void setGoToButtonEnabled(boolean enabled) {
		this.showOnSearchButton.setEnabled(enabled);
		this.showVisualizationsButton.setEnabled(enabled);
		this.showOnMapButton.setEnabled(enabled);
		this.addToListButton.setEnabled(enabled);
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

	public void addHeaderClickedHandler(ClickHandler handler) {
		this.header.addClickHandler(handler);
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

	public boolean isActive() {
		return this.sensorItem.getElement().getClassName().contains("active");
	}

	public void setActive() {
		this.sensorItem.setActive(true);
	}

	public void changeToSelectAll(boolean selectAll) {
		if(selectAll) {
			this.selectAllButton.setText(Languages.selectAllSensors());
		}else {
			this.selectAllButton.setText(Languages.deselectAllSensors());
		}
	}

	public void showItem(boolean show) {
		if(show) {
			this.sensorItem.getElement().getStyle().clearDisplay();
		}else{
			this.sensorItem.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void showItemSpinner(boolean show) {
		if(show) {
			this.listItemSpinner.getElement().getStyle().clearDisplay();
		}else{
			this.listItemSpinner.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void showNoDataIndicator(boolean show) {
		if(show) {
			this.noDataIndicator.getElement().getStyle().clearDisplay();
		}else{
			this.noDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void open(){
		this.clickElement(this.header.getElement());
	}


	private native void clickElement(Element element) /*-{
		element.click();
	}-*/;
}
