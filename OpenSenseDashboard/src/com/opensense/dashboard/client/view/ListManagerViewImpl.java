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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.presenter.ListManagerPresenter;
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.client.utils.Languages;
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
	Pager mySensorsPagerTop;

	@UiField
	Pager mySensorsPagerBottom;

	@UiField
	Pager selectedSensorsPagerTop;

	@UiField
	Pager selectedSensorsPagerBottom;

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

	private Map<Integer, Map<Integer, BasicSensorItemCard>> sensorCardsInLists = new HashMap<>();
	private Map<Integer, List<Integer>> showSensorIdsInLists = new HashMap<>();

	private Map<Integer, ListCollapsibleItem> collapsiblesItems = new HashMap<>();

	public ListManagerViewImpl(ListManagerPresenter presenter) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		this.initPager(this.favSensorContainer, this.favPagerTop, this.favPagerBottom, -1);
		this.initPager(this.selectedSensorsSensorContainer, this.mySensorsPagerTop, this.mySensorsPagerBottom, -2);
		this.initPager(this.mySensorsSensorContainer, this.selectedSensorsPagerTop, this.selectedSensorsPagerBottom, -3);
	}

	@Override
	public void addNewListItem(final int listId) {
		ListCollapsibleItem item = new ListCollapsibleItem();
		item.setName(Languages.newList() + listId);
		item.addDeleteButtonClickHandler(event -> this.deleteList(listId));
		this.collapsible.add(item);
		this.collapsiblesItems.put(listId, item);
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
	public void setSensorsInList(final int listId, final List<Integer> sensors) {
		final Map<Integer, BasicSensorItemCard> sensorCards = new HashMap<>();
		final List<Integer> showFavoriteIds = new ArrayList<>();
		if(sensors.isEmpty()) {
			this.favNoDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			this.favNoDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
			sensors.forEach(id -> {
				final List<Integer> idList = new ArrayList<>();
				idList.add(id);
				BasicSensorItemCard card = new BasicSensorItemCard();
				card.setHeader("ID " + id);
				card.addValueChangeHandler(event -> {
				});
				card.addTrashButtonClickHandler(event -> this.presenter.deleteSensorCardInList(-1, id));
				card.addSearchButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, idList)));
				card.addMapButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, idList)));
				card.addVisButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, idList)));
				sensorCards.put(id, card);
				showFavoriteIds.add(id);
			});
		}
		this.sensorCardsInLists.put(listId, sensorCards);
		this.showSensorIdsInLists.put(listId, showFavoriteIds);
		this.favPagerTop.update(showFavoriteIds.size(), true);
	}

	private void initPager(final HasWidgets container, final Pager pagerTop, final Pager pagerBottom, final int listId) {
		pagerTop.setMaxObjectsOnPage(10);
		pagerTop.addBackwardsButtonClickHandler(event -> pagerTop.onBackwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addBackwardsStepByStepClickHandler(event -> pagerTop.onBackwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsStepByStepClickHandler(event -> pagerTop.onForwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsButtonClickHandler(event -> pagerTop.onForwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));

		pagerTop.addPaginationEventHandler(event -> this.pagination(container, event.getPage(), event.getMaxObjectsOnPage(), listId));

		pagerBottom.setMaxObjectsOnPage(10);
		pagerBottom.addBackwardsButtonClickHandler(event -> pagerBottom.onBackwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addBackwardsStepByStepClickHandler(event -> pagerBottom.onBackwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addForwardsStepByStepClickHandler(event -> pagerBottom.onForwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addForwardsButtonClickHandler(event -> pagerBottom.onForwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));

		pagerBottom.addPaginationEventHandler(event -> this.pagination(container, event.getPage(), event.getMaxObjectsOnPage(), listId));

		pagerTop.setDependentPager(pagerBottom);
		pagerBottom.setDependentPager(pagerTop);
	}

	private void pagination(HasWidgets container, int page, int maxObjectsOnPage, int listId) {
		container.clear();
		for(int i = page * maxObjectsOnPage; (i < this.showSensorIdsInLists.get(listId).size()) && (i < ((page + 1) * maxObjectsOnPage)); i++){
			this.favSensorContainer.add(this.sensorCardsInLists.get(listId).get(this.showSensorIdsInLists.get(listId).get(i)));
		}
	}
}
