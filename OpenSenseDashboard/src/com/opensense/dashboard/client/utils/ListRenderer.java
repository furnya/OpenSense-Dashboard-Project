package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPreLoader;

public class ListRenderer extends Composite{

	@UiTemplate("ListRenderer.ui.xml")
	interface ListRendererUiBinder extends UiBinder<Widget, ListRenderer> {
	}

	private static ListRendererUiBinder uiBinder = GWT.create(ListRendererUiBinder.class);

	private ListController controller;

	private Map<Integer, ListCollapsibleItem> collapsiblesItems = new HashMap<>();

	@UiField
	MaterialCollapsible collapsible;

	@UiField
	Div favSensorContainer;

	@UiField
	Div favNoDataIndicator;

	@UiField
	MaterialPreLoader favSpinner;

	public ListRenderer(ListController listController) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.controller = listController;
	}

	public void addNewListItem(int id) {
		ListCollapsibleItem item = new ListCollapsibleItem(this, id);
		this.collapsible.add(item);
		this.collapsiblesItems.put(id, item);
	}

	public void deleteList(int listId) {
		this.controller.deleteList(listId);
	}

	public void removeListItem(int listId) {
		this.collapsiblesItems.get(listId).removeFromParent();
		this.collapsiblesItems.remove(listId);
	}

	public void addFavoriteSensors(List<Integer> favList) {
		this.favSensorContainer.clear();
		if(favList.isEmpty()) {
			this.favNoDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			this.favNoDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
			favList.forEach(id -> {
				BasicSensorItemCard card = new BasicSensorItemCard();
				card.setHeader("ID " + id);
				this.favSensorContainer.add(card);
			});
		}

	}

}
