package com.opensense.dashboard.client.view;

import java.util.ArrayList;
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
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.client.utils.ListCollapsibleItem;
import com.opensense.dashboard.client.utils.Pager;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialPreLoader;

public class ListManagerViewImpl extends Composite implements ListManagerView {

	@UiTemplate("ListManagerView.ui.xml")
	interface ListManagerViewUiBinder extends UiBinder<Widget, ListManagerViewImpl> {
	}

	private static ListManagerViewUiBinder uiBinder = GWT.create(ListManagerViewUiBinder.class);

	protected Presenter presenter;

	private Map<Integer, ListCollapsibleItem> collapsiblesItems = new HashMap<>();

	@UiField
	MaterialCollapsible collapsible;

	@UiField
	Div favSensorContainer;

	@UiField
	Div favNoDataIndicator;

	@UiField
	MaterialPreLoader favSpinner;

	@UiField
	Pager favPagerTop;

	@UiField
	Pager favPagerBottom;

	@UiField
	MaterialCollapsibleItem selectedSensorsItem;

	@UiField
	Div selectedSensorsSensorContainer;

	@UiField
	Div selectedSensorsNoDataIndicator;

	@UiField
	MaterialPreLoader selectedSensorsSpinner;

	@UiField
	Div mySensorsSensorContainer;

	@UiField
	Div mySensorsNoDataIndicator;

	@UiField
	MaterialPreLoader mySensorsSpinner;

	private Map<Integer, BasicSensorItemCard> favoriteSensorCards = new HashMap<>();
	private List<Integer> showFavoriteIds = new ArrayList<>();

	public ListManagerViewImpl(ListManagerPresenter presenter) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		this.initFavoritePager();
	}

	@Override
	public void addNewListItem(int id) {
		ListCollapsibleItem item = new ListCollapsibleItem(this, id);
		this.collapsible.add(item);
		this.collapsiblesItems.put(id, item);
	}

	public void deleteList(int listId) {
		this.presenter.deleteList(listId);
	}

	@Override
	public void removeListItem(int listId) {
		this.collapsiblesItems.get(listId).removeFromParent();
		this.collapsiblesItems.remove(listId);
	}

	@Override
	public void addFavoriteSensors(List<Integer> favList) {
		this.favoriteSensorCards.clear();
		this.showFavoriteIds.clear();
		if(favList.isEmpty()) {
			this.favNoDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			this.favNoDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
			favList.forEach(id -> {
				BasicSensorItemCard card = new BasicSensorItemCard();
				card.setHeader("ID " + id);
				card.addValueChangeHandler(event -> {
				});
				card.addTrashButtonClickHandler(event -> {
					this.presenter.deleteSensorCardInList(-1, id);
				});
				this.favoriteSensorCards.put(id, card);
				this.showFavoriteIds.add(id);
			});
			this.favPagerTop.update(this.showFavoriteIds.size(), true);
		}
	}

	private void initFavoritePager() {
		this.favPagerTop.setMaxObjectsOnPage(10);
		this.favPagerTop.addBackwardsButtonClickHandler(event -> this.favPagerTop.onBackwardsButtonClicked(this.showFavoriteIds.size()));
		this.favPagerTop.addBackwardsStepByStepClickHandler(event -> this.favPagerTop.onBackwardsStepByStepButtonClicked(this.showFavoriteIds.size()));
		this.favPagerTop.addForwardsStepByStepClickHandler(event -> this.favPagerTop.onForwardsStepByStepButtonClicked(this.showFavoriteIds.size()));
		this.favPagerTop.addForwardsButtonClickHandler(event -> this.favPagerTop.onForwardsButtonClicked(this.showFavoriteIds.size()));

		this.favPagerTop.addPaginationEventHandler(event -> this.favoriteListpagination(event.getPage(), event.getMaxObjectsOnPage()));

		this.favPagerBottom.setMaxObjectsOnPage(10);
		this.favPagerBottom.addBackwardsButtonClickHandler(event -> this.favPagerBottom.onBackwardsButtonClicked(this.showFavoriteIds.size()));
		this.favPagerBottom.addBackwardsStepByStepClickHandler(event -> this.favPagerBottom.onBackwardsStepByStepButtonClicked(this.showFavoriteIds.size()));
		this.favPagerBottom.addForwardsStepByStepClickHandler(event -> this.favPagerBottom.onForwardsStepByStepButtonClicked(this.showFavoriteIds.size()));
		this.favPagerBottom.addForwardsButtonClickHandler(event -> this.favPagerBottom.onForwardsButtonClicked(this.showFavoriteIds.size()));

		this.favPagerBottom.addPaginationEventHandler(event -> this.favoriteListpagination(event.getPage(), event.getMaxObjectsOnPage()));

		this.favPagerTop.setDependentPager(this.favPagerBottom);
		this.favPagerBottom.setDependentPager(this.favPagerTop);
	}

	private void favoriteListpagination(int page, int maxObjectsOnPage) {
		this.favSensorContainer.clear();
		for(int i = page * maxObjectsOnPage; (i < this.showFavoriteIds.size()) && (i < ((page + 1) * maxObjectsOnPage)); i++){
			this.favSensorContainer.add(this.favoriteSensorCards.get(this.showFavoriteIds.get(i)));
		}
	}
}
