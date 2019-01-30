package com.opensense.dashboard.client.utils;

import java.util.List;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.AddToListEvent;
import com.opensense.dashboard.client.event.AddToListEventHandler;
import com.opensense.dashboard.client.event.ListNameChangedEvent;
import com.opensense.dashboard.client.event.ListNameChangedEventHandler;
import com.opensense.dashboard.client.event.SensorSelectionEvent;
import com.opensense.dashboard.client.event.SensorSelectionEventHandler;
import com.opensense.dashboard.client.model.DefaultListItem;
import com.opensense.dashboard.client.model.Size;
import com.opensense.dashboard.shared.UserList;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTooltip;

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
	MaterialButton deleteSensorsButton;

	@UiField
	MaterialButton showInfoButton;

	@UiField
	MaterialDropDown listDropDown;

	@UiField
	MaterialCollapsibleHeader header;

	@UiField
	Span sensorDetails;

	@UiField
	MaterialTooltip selectAllTooltip;

	@UiField
	Spinner listDropDownSpinner;

	@UiField
	MaterialLink favoriteLink;
	
	@UiField
	MaterialTooltip addToListTooltip;
	
	@UiField
	MaterialTooltip deleteSensorsTooltip;

	private static ListCollapsibleItemUiBinder uiBinder = GWT.create(ListCollapsibleItemUiBinder.class);

	private HandlerRegistration clickHandler;
	private HandlerRegistration enterHandler;
	private HandlerRegistration favoriteButtonHandler;

	public ListCollapsibleItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.setSize(Size.MEDIUM);
		this.showNoDataIndicator(false);
		this.addDomHandler(event -> this.hideListDropDown(), MouseWheelEvent.getType());
		this.listDropDown.addMouseWheelHandler(MouseWheelEvent::stopPropagation);
	}

	public void setActivators(int id) {
		this.addToListButton.setActivates("ddact-"+id);
		this.listDropDown.setActivator("ddact-"+id);
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

	public void addShowInfoButtonButtonClickHandler(ClickHandler handler) {
		this.showInfoButton.getElement().getStyle().clearDisplay();
		this.showInfoButton.addClickHandler(handler);
	}

	public void setSelectAllButtonEnabled(boolean enabled) {
		this.selectAllButton.setEnabled(enabled);
	}

	public void setActionButtonsEnabled(boolean enabled) {
		this.showOnSearchButton.setEnabled(enabled);
		this.showVisualizationsButton.setEnabled(enabled);
		this.showOnMapButton.setEnabled(enabled);
		this.addToListButton.setEnabled(enabled);
		this.deleteSensorsButton.setEnabled(enabled);
		this.showInfoButton.setEnabled(enabled);
		if(enabled) {
			this.deleteSensorsTooltip.reinitialize();
		}else {
			this.deleteSensorsTooltip.remove();
		}
	}

	public void addDeleteButtonClickHandler(ClickHandler handler) {
		this.deleteButton.getElement().getStyle().clearDisplay();
		this.deleteButton.addClickHandler(event -> {
			event.stopPropagation();
			handler.onClick(event);
		});
	}


	public void addDeleteSensorsButtonClickHandler(ClickHandler handler) {
		this.deleteSensorsButton.getElement().getStyle().clearDisplay();
		this.deleteSensorsButton.addClickHandler(handler::onClick);
	}

	public void addListNameInputHandler(final ListNameChangedEventHandler handler) {
		this.listItemName.addStyleName("list-name");
		this.listItemName.addDomHandler(event -> {
			event.stopPropagation();
			this.listNameInput.setValue(this.listItemName.getText());
			this.listNameInput.setSelectionRange(0, this.listItemName.getText().length());
			this.deleteButton.getElement().getStyle().setDisplay(Display.NONE);
			this.sensorDetails.getElement().getStyle().setDisplay(Display.NONE);
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
		this.sensorDetails.getElement().getStyle().clearDisplay();
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
			this.selectAllTooltip.setText(Languages.selectAllTooltip());
		}else {
			this.selectAllButton.setText(Languages.deselectAllSensors());
			this.selectAllTooltip.setText(Languages.deselectAllTooltip());
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
		if(!this.header.getElement().getClassName().contains("active")) {
			this.clickElement(this.header.getElement());
		}
	}

	private native void clickElement(Element element) /*-{
		element.click();
	}-*/;

	public void setSize(Size size) {
		this.sensorItem.getElement().setAttribute("size", size.name().toLowerCase());
	}

	public void close() {
		if(this.header.getElement().getClassName().contains("active")) {
			this.clickElement(this.header.getElement());
		}
	}

	public void setSensorDetails(String details) {
		this.sensorDetails.setText(details);
	}

	public void prepareForDropdown() {
		this.addToListTooltip.remove();
		this.hideListDropDown();
		this.showListDropDownSpinner(true);
		this.addToListButton.setEnabled(false);
	}

	public void hideListDropDown() {
		this.listDropDown.getElement().addClassName("display-none-important");
		this.addToListTooltip.reinitialize();
	}

	private void showListDropDown() {
		this.listDropDown.getElement().removeClassName("display-none-important");
	}

	public void showUserListsInDropDown(AddToListEventHandler handler, List<UserList> userLists) {
		this.showListDropDown();
		this.clearListsDropDown();
		this.addToListButton.setEnabled(true);
		this.showListDropDownSpinner(false);
		if(this.favoriteButtonHandler!=null) {
			this.favoriteButtonHandler.removeHandler();
		}
		this.favoriteButtonHandler = this.favoriteLink.addClickHandler(event -> {
			this.hideListDropDown();
			handler.onAddToListEvent(new AddToListEvent(DefaultListItem.FAVORITE_LIST_ID,Languages.favorites()));
		});
		userLists.forEach(userList -> {
			MaterialLink link = new MaterialLink();
			link.setText(userList.getListName());
			link.addClickHandler(event -> {
				this.hideListDropDown();
				handler.onAddToListEvent(new AddToListEvent(userList.getListId(),userList.getListName()));
			});
			this.listDropDown.add(link);
		});
	}

	public void clearListsDropDown() {
		for (int i = this.listDropDown.getChildren().size() - 1; i > 0; i--) {
			this.listDropDown.remove(i);
		}
	}

	public void showListDropDownSpinner(boolean show) {
		if(show) {
			this.listDropDownSpinner.getElement().getStyle().setDisplay(Display.BLOCK);
		} else {
			this.listDropDownSpinner.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
}
