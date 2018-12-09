package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.event.SelectedSensorsChangeEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListCollapsibleItem;
import com.opensense.dashboard.client.utils.Pager;

import gwt.material.design.client.ui.MaterialCollapsible;

public class ListManagerViewImpl extends Composite implements ListManagerView {

	@UiTemplate("ListManagerView.ui.xml")
	interface ListManagerViewUiBinder extends UiBinder<Widget, ListManagerViewImpl> {
	}

	private static ListManagerViewUiBinder uiBinder = GWT.create(ListManagerViewUiBinder.class);

	protected Presenter presenter;

	@UiField
	MaterialCollapsible collapsible;

	private Map<Integer, Map<Integer, BasicSensorItemCard>> sensorCardsInLists = new HashMap<>();
	private Map<Integer, List<Integer>> showSensorIdsInLists = new HashMap<>();

	private Map<Integer, List<Integer>> selectedSensorIdsInLists = new HashMap<>();

	private Map<Integer, ListCollapsibleItem> collapsiblesItems = new HashMap<>();

	public ListManagerViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * These are the default added lists (favorites-(id = -1), selected-(id = -2), my-(id = -3)sensors)
	 */
	@Override
	public void initDefaultLists(Runnable runnable) {
		ListCollapsibleItem favoriteListItem = new ListCollapsibleItem();
		favoriteListItem.setName(Languages.favorites());
		favoriteListItem.setListIcon(GUIImageBundle.INSTANCE.favoriteRed().getSafeUri().asString());
		this.collapsiblesItems.put(-1, favoriteListItem);
		this.initPager(-1);
		this.collapsible.add(favoriteListItem);
		favoriteListItem.getCollapsibleItem().setActive(true);
		favoriteListItem.getCollapsibleItem().getElement().getStyle().clearDisplay();

		ListCollapsibleItem selectedSensorsListItem = new ListCollapsibleItem();
		selectedSensorsListItem.setName(Languages.selectedSensors());
		this.collapsiblesItems.put(-2, selectedSensorsListItem);
		this.initPager(-2);
		this.collapsible.add(selectedSensorsListItem);

		ListCollapsibleItem mySensorListsItem = new ListCollapsibleItem();
		mySensorListsItem.setName(Languages.mySensors());
		mySensorListsItem.setListIcon(GUIImageBundle.INSTANCE.mySesnors().getSafeUri().asString());
		this.collapsiblesItems.put(-3, mySensorListsItem);
		this.initPager(-3);
		this.collapsible.add(mySensorListsItem);
		mySensorListsItem.getCollapsibleItem().getElement().getStyle().clearDisplay();
		runnable.run();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void addNewUserListItem(final int listId, final List<Integer> sensorList) {
		ListCollapsibleItem item = new ListCollapsibleItem();
		item.setName(Languages.newList() + listId);
		item.addDeleteButtonClickHandler(event -> this.deleteList(listId));
		this.collapsible.add(item);
		this.collapsiblesItems.put(listId, item);
		this.setSensorsInList(listId, sensorList);
		item.getCollapsibleItem().getElement().getStyle().clearDisplay();
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
		final List<Integer> showSensorIds = new ArrayList<>();
		if(sensors.isEmpty()) {
			this.collapsiblesItems.get(listId).getNoDataIndicator().getElement().getStyle().clearDisplay();
		}else {
			this.collapsiblesItems.get(listId).getNoDataIndicator().getElement().getStyle().setDisplay(Display.NONE);
			sensors.forEach(id -> {
				final List<Integer> idList = new ArrayList<>();
				idList.add(id);
				BasicSensorItemCard card = new BasicSensorItemCard();
				card.setHeader("ID " + id);
				card.addValueChangeHandler(event -> {
					if(event.getValue()) {
						this.selectedSensorIdsInLists.get(listId).add(id);
					}else {
						this.selectedSensorIdsInLists.get(listId).remove(id);
					}
					this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(this.selectedSensorIdsInLists.get(listId)));
				});
				card.addTrashButtonClickHandler(event -> this.presenter.deleteSensorsInList(listId, idList));
				card.addSearchButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, idList)));
				card.addMapButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, idList)));
				card.addVisButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, idList)));
				sensorCards.put(id, card);
				showSensorIds.add(id);
			});
		}
		this.sensorCardsInLists.put(listId, sensorCards);
		this.selectedSensorIdsInLists.put(listId, new ArrayList<>());
		this.showSensorIdsInLists.put(listId, showSensorIds);
		this.collapsiblesItems.get(listId).getTopPager().update(showSensorIds.size(), true);
	}

	private void initPager(final int listId) {
		final Pager pagerTop = this.collapsiblesItems.get(listId).getTopPager();
		pagerTop.setMaxObjectsOnPage(10);
		pagerTop.addBackwardsButtonClickHandler(event -> pagerTop.onBackwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addBackwardsStepByStepClickHandler(event -> pagerTop.onBackwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsStepByStepClickHandler(event -> pagerTop.onForwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsButtonClickHandler(event -> pagerTop.onForwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));

		pagerTop.addPaginationEventHandler(event -> this.pagination(event.getPage(), event.getMaxObjectsOnPage(), listId));

		final Pager pagerBottom = this.collapsiblesItems.get(listId).getBottomPager();
		pagerBottom.setMaxObjectsOnPage(10);
		pagerBottom.addBackwardsButtonClickHandler(event -> pagerBottom.onBackwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addBackwardsStepByStepClickHandler(event -> pagerBottom.onBackwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addForwardsStepByStepClickHandler(event -> pagerBottom.onForwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerBottom.addForwardsButtonClickHandler(event -> pagerBottom.onForwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));

		pagerBottom.addPaginationEventHandler(event -> this.pagination(event.getPage(), event.getMaxObjectsOnPage(), listId));

		pagerTop.setDependentPager(pagerBottom);
		pagerBottom.setDependentPager(pagerTop);
	}

	private void pagination(final int page, final int maxObjectsOnPage, final int listId) {
		this.collapsiblesItems.get(listId).getSensorContainer().clear();
		for(int i = page * maxObjectsOnPage; (i < this.showSensorIdsInLists.get(listId).size()) && (i < ((page + 1) * maxObjectsOnPage)); i++){
			this.collapsiblesItems.get(listId).getSensorContainer().add(this.sensorCardsInLists.get(listId).get(this.showSensorIdsInLists.get(listId).get(i)));
		}
	}

	@Override
	public void clearLists() {
		this.collapsible.clear();
		this.collapsible.closeAll();
		this.collapsible.reinitialize();
		this.selectedSensorIdsInLists.clear(); //TODO: find a better soution that selected senors are still selected on page return
		this.sensorCardsInLists.clear();
		this.showSensorIdsInLists.clear();
		this.collapsiblesItems.clear();
	}
}
