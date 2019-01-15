package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.AddSensorsToFavoriteListEvent;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.event.SelectedSensorsChangeEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.DefaultListItem;
import com.opensense.dashboard.client.model.Size;
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListCollapsibleItem;
import com.opensense.dashboard.client.utils.ListManagerOptions;
import com.opensense.dashboard.client.utils.MeasurandIconHelper;
import com.opensense.dashboard.client.utils.Pager;
import com.opensense.dashboard.shared.MinimalSensor;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.UserList;

import gwt.material.design.client.ui.MaterialCollapsible;

public class ListManagerViewImpl extends Composite implements ListManagerView {

	@UiTemplate("ListManagerView.ui.xml")
	interface ListManagerViewUiBinder extends UiBinder<Widget, ListManagerViewImpl> {
	}

	private static final Logger LOGGER = Logger.getLogger(ListManagerViewImpl.class.getName());

	private static ListManagerViewUiBinder uiBinder = GWT.create(ListManagerViewUiBinder.class);

	protected Presenter presenter;

	@UiField
	MaterialCollapsible collapsible;

	private Map<Integer, Map<Integer, BasicSensorItemCard>> sensorCardsInLists = new HashMap<>();
	private Map<Integer, List<Integer>> showSensorIdsInLists = new HashMap<>();

	private Map<Integer, List<Integer>> selectedSensorIdsInLists = new HashMap<>();

	private Map<Integer, ListCollapsibleItem> collapsiblesItems = new HashMap<>();

	private Integer activeItemId = null;

	public ListManagerViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * These are the default added lists (favorites-(id = -1), selected-(id = -2), my-(id = -3)sensors)
	 */
	@Override
	public void initDefaultLists(Runnable runnable) {
		// Create favorite list
		this.addNewUserListItem(new UserList(DefaultListItem.FAVORITE_LIST_ID, Languages.favorites()), false);
		this.collapsiblesItems.get(DefaultListItem.FAVORITE_LIST_ID).setListIcon(GUIImageBundle.INSTANCE.favoriteRed().getSafeUri().asString());
		this.collapsiblesItems.get(DefaultListItem.FAVORITE_LIST_ID).setActive();
		this.activeItemId = DefaultListItem.FAVORITE_LIST_ID;

		// Create selected sensors list
		this.addNewUserListItem(new UserList(DefaultListItem.SELECTED_LIST_ID, Languages.selectedSensors()), false);
		this.collapsiblesItems.get(DefaultListItem.SELECTED_LIST_ID).showItem(false);

		// Create my sensors list
		this.addNewUserListItem(new UserList(DefaultListItem.MY_LIST_ID, Languages.mySensors()), false);
		this.collapsiblesItems.get(DefaultListItem.MY_LIST_ID).setListIcon(GUIImageBundle.INSTANCE.mySesnors().getSafeUri().asString());
		runnable.run();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void addNewUserListItem(final UserList userList, boolean editable) {
		final int listId = userList.getListId();
		final ListManagerOptions options = this.presenter.getController().getOptions();

		ListCollapsibleItem item = new ListCollapsibleItem();
		item.setName(userList.getListName());
		item.setSpinnerSize(options.getSpinnerSize());
		item.addSelectAllButtonClickHandler(event -> this.selectAllSensorsInList(listId, event.isSelectAll()));
		item.addHeaderClickedHandler(event -> this.onSelectedItemsChanged());

		if(editable && options.isEditingActive()) {
			item.addListNameInputHandler(event -> this.presenter.changeListName(listId, event.getListName()));
			item.addDeleteButtonClickHandler(event -> this.deleteList(listId));
		}
		if(options.isEditingActive()) {
			item.addDeleteSensorsButtonClickHandler(event -> this.presenter.deleteSensorsInList(listId, this.selectedSensorIdsInLists.get(listId)));
		}
		if(options.isShowMapButton()) {
			item.addShowOnMapButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, this.selectedSensorIdsInLists.get(this.activeItemId))));
		}
		if(options.isShowVisualizationButton()) {
			item.addShowVisualizationsButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, this.selectedSensorIdsInLists.get(this.activeItemId))));
		}
		if(options.isShowSearchButton()) {
			item.addShowSearchButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, this.selectedSensorIdsInLists.get(this.activeItemId))));
		}

		if(this.collapsiblesItems.containsKey(listId)) {
			this.collapsiblesItems.get(listId).removeFromParent();
			this.collapsiblesItems.replace(listId, item);
		}else {
			this.collapsiblesItems.put(listId, item);
		}
		this.collapsible.add(item);
		this.initPager(listId);
		item.showItem(true);
	}

	public void deleteList(int listId) {
		this.presenter.deleteUserList(listId);
	}

	@Override
	public void setSensorsInList(final int listId, final List<MinimalSensor> sensors) {
		final Map<Integer, BasicSensorItemCard> sensorCards = new HashMap<>();
		final List<Integer> showSensorIds = new ArrayList<>();
		this.collapsiblesItems.get(listId).showItemSpinner(false);
		this.collapsiblesItems.get(listId).changeToSelectAll(true);
		if(sensors.isEmpty()) {
			this.collapsiblesItems.get(listId).setSelectAllButtonEnabled(false);
			this.collapsiblesItems.get(listId).showNoDataIndicator(true);
		}else {
			this.collapsiblesItems.get(listId).showNoDataIndicator(false);
			sensors.forEach(sensor -> {
				final List<Integer> idList = new ArrayList<>();
				idList.add(sensor.getSensorId());
				final BasicSensorItemCard card = new BasicSensorItemCard(sensor.getSensorId(), this);
				card.setIcon(MeasurandIconHelper.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
				card.setHeader("ID " + sensor.getSensorId() + " - " +  sensor.getMeasurand().getDisplayName());
				card.addValueChangeHandler(event -> {
					if(event.getValue()) {
						if((this.presenter.getController().getOptions().getMaxSelectedObjects() != null) &&
								(this.selectedSensorIdsInLists.get(listId).size() >= this.presenter.getController().getOptions().getMaxSelectedObjects())){
							card.setActive(false);
							AppController.showInfo(Languages.maxSensorSelectedLimitExceeded(this.presenter.getController().getOptions().getMaxSelectedObjects()));
							return;
						}
						this.selectedSensorIdsInLists.get(listId).add(sensor.getSensorId());
					}else {
						this.selectedSensorIdsInLists.get(listId).remove((Object) sensor.getSensorId());
					}
					this.collapsiblesItems.get(listId).setSensorDetails("(Enthält " + this.showSensorIdsInLists.get(listId).size() + " Sensoren davon ausgewählt "+ this.selectedSensorIdsInLists.get(listId).size() + ")");
					this.collapsiblesItems.get(listId).setActionButtonsEnabled(!this.selectedSensorIdsInLists.get(listId).isEmpty());
					this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(this.selectedSensorIdsInLists.get(listId)));
				});
				if(this.presenter.getController().getOptions().isEditingActive() && (listId != DefaultListItem.SELECTED_LIST_ID)) {
					card.addTrashButtonClickHandler(event -> this.presenter.deleteSensorsInList(listId, idList));
				}
				if(listId != DefaultListItem.FAVORITE_LIST_ID) {
					card.addFavButtonClickHandler(event -> this.presenter.getEventBus().fireEvent(new AddSensorsToFavoriteListEvent(idList)));
				}
				sensorCards.put(sensor.getSensorId(), card);
				showSensorIds.add(sensor.getSensorId());
			});
			this.collapsiblesItems.get(listId).setSelectAllButtonEnabled(true);
		}
		if(this.sensorCardsInLists.containsKey(listId)) {
			this.sensorCardsInLists.replace(listId, sensorCards);
		}else {
			this.sensorCardsInLists.put(listId, sensorCards);
		}
		if(this.selectedSensorIdsInLists.containsKey(listId)) {
			for (int i = this.selectedSensorIdsInLists.get(listId).size() - 1; i >= 0 ; i--) {
				if(this.sensorCardsInLists.get(listId).containsKey(this.selectedSensorIdsInLists.get(listId).get(i))) {
					this.sensorCardsInLists.get(listId).get(this.selectedSensorIdsInLists.get(listId).get(i)).setActive(true);
				}else {
					this.selectedSensorIdsInLists.get(listId).remove(i);
				}
			}
			this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(this.selectedSensorIdsInLists.get(listId)));
		}else {
			this.selectedSensorIdsInLists.put(listId, new ArrayList<>());
		}
		if(this.showSensorIdsInLists.containsKey(listId)) {
			this.showSensorIdsInLists.replace(listId, showSensorIds);
		} else {
			this.showSensorIdsInLists.put(listId, showSensorIds);
		}
		this.collapsiblesItems.get(listId).setActionButtonsEnabled(!this.selectedSensorIdsInLists.get(listId).isEmpty());
		this.collapsiblesItems.get(listId).getTopPager().update(showSensorIds.size(), true);
		this.collapsiblesItems.get(listId).setSensorDetails("(Enthält " + this.showSensorIdsInLists.get(listId).size() + " Sensoren davon ausgewählt "+ this.selectedSensorIdsInLists.get(listId).size() + ")");
	}

	private void initPager(final int listId) {
		final Size pagerSize = this.presenter.getController().getOptions().getPagerSize();
		final int maxObjectOnPage = this.presenter.getController().getOptions().getMaxObjectOnPage();

		final Pager pagerTop = this.collapsiblesItems.get(listId).getTopPager();
		pagerTop.setPagerSize(pagerSize);
		pagerTop.setMaxObjectsOnPage(maxObjectOnPage);
		pagerTop.addBackwardsButtonClickHandler(event -> pagerTop.onBackwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addBackwardsStepByStepClickHandler(event -> pagerTop.onBackwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsStepByStepClickHandler(event -> pagerTop.onForwardsStepByStepButtonClicked(this.showSensorIdsInLists.get(listId).size()));
		pagerTop.addForwardsButtonClickHandler(event -> pagerTop.onForwardsButtonClicked(this.showSensorIdsInLists.get(listId).size()));

		pagerTop.addPaginationEventHandler(event -> this.pagination(event.getPage(), event.getMaxObjectsOnPage(), listId));

		final Pager pagerBottom = this.collapsiblesItems.get(listId).getBottomPager();
		pagerBottom.setPagerSize(pagerSize);
		pagerBottom.setMaxObjectsOnPage(maxObjectOnPage);
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
	public void showMySensorListsItem(boolean show) {
		this.collapsiblesItems.get(DefaultListItem.MY_LIST_ID).showItem(show);
	}

	@Override
	public void showSelectedSensorListsItem(boolean show) {
		this.collapsiblesItems.get(DefaultListItem.SELECTED_LIST_ID).showItem(show);
	}

	@Override
	public void clearUserLists() {
		final List<Integer> idsToRemove = new ArrayList<>();
		this.collapsiblesItems.keySet().stream().filter(id -> id >= 0).forEach(idsToRemove::add);
		idsToRemove.forEach(id -> {
			this.collapsiblesItems.get(id).removeFromParent();
			this.collapsiblesItems.remove(id);
			this.showSensorIdsInLists.remove(id);
			this.sensorCardsInLists.remove(id);
			this.selectedSensorIdsInLists.remove(id);
		});
	}

	private void selectAllSensorsInList(int listId, boolean isSelect) {
		if(this.selectedSensorIdsInLists.get(listId) == null) {
			LOGGER.log(Level.WARNING, () -> "Can not select all sensors in list with id " + listId);
			return;
		}
		final List<Integer> selectedSensors = this.selectedSensorIdsInLists.get(listId);
		if(isSelect) {
			for (int id : this.showSensorIdsInLists.get(listId)) {
				if((this.presenter.getController().getOptions().getMaxSelectedObjects() != null) && (selectedSensors.size() >= this.presenter.getController().getOptions().getMaxSelectedObjects())) {
					AppController.showInfo(Languages.maxSensorSelectedLimitExceeded(this.presenter.getController().getOptions().getMaxSelectedObjects()));
					break;
				}
				if(!this.selectedSensorIdsInLists.get(listId).contains(id)) {
					selectedSensors.add(id);
					this.sensorCardsInLists.get(listId).get(id).setActive(true);
				}
			}
		}else {
			this.selectedSensorIdsInLists.get(listId).forEach(id -> this.sensorCardsInLists.get(listId).get(id).setActive(false));
			selectedSensors.clear();
		}
		this.selectedSensorIdsInLists.replace(listId, selectedSensors);
		this.collapsiblesItems.get(listId).setActionButtonsEnabled(!this.selectedSensorIdsInLists.get(listId).isEmpty());
		this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(selectedSensors));
		this.collapsiblesItems.get(listId).setSensorDetails("(Enthält " + this.showSensorIdsInLists.get(listId).size() + " Sensoren davon ausgewählt "+ this.selectedSensorIdsInLists.get(listId).size() + ")");
	}

	private void onSelectedItemsChanged() {
		//TODO: find a better solution to detect which item is active (the item animation need some time to set the style active so the call has to be delayed)
		new Timer() {
			@Override
			public void run() {
				ListManagerViewImpl.this.activeItemId = null;
				ListManagerViewImpl.this.collapsiblesItems.entrySet().forEach(entry -> {
					if(entry.getValue().isActive()) {
						ListManagerViewImpl.this.activeItemId = entry.getKey();
					}
				});
				if((ListManagerViewImpl.this.activeItemId != null) && (ListManagerViewImpl.this.selectedSensorIdsInLists.get(ListManagerViewImpl.this.activeItemId) != null)) {
					ListManagerViewImpl.this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(ListManagerViewImpl.this.selectedSensorIdsInLists.get(ListManagerViewImpl.this.activeItemId)));
				}else {
					ListManagerViewImpl.this.presenter.getController().onSelectedSensorsChangeEvent(new SelectedSensorsChangeEvent(new ArrayList<>()));
				}
			}
		}.schedule(100);
	}

	@Override
	public void setCollapsibleListItemSelected(final Integer listId) {
		if(listId == null) {
			if(this.collapsiblesItems.get(this.activeItemId) != null) {
				this.collapsiblesItems.get(this.activeItemId).close();
			}
			return;
		}
		if(this.collapsiblesItems.get(listId) == null) {
			AppController.showError(Languages.selectListItemError());
			LOGGER.log(Level.WARNING, () -> "Can not select the list with id " + listId);
			return;
		}
		if((this.activeItemId == null) || (this.activeItemId != listId)) {
			this.collapsiblesItems.get(listId).open();
		}
	}

	@Override
	public void selectAllSensorsInList(int listId) {
		this.collapsiblesItems.get(listId).changeToSelectAll(false);
		this.selectAllSensorsInList(listId, true);
	}

	@Override
	public void setSelectedSensorItemsColor(final int sensorId, final String sensorColor) {
		if((this.activeItemId == null) && (this.selectedSensorIdsInLists.get(this.activeItemId) == null)) {
			LOGGER.log(Level.WARNING, () -> "Can not set the color to the sensor with id " + sensorId);
			return;
		}
		if(this.selectedSensorIdsInLists.get(this.activeItemId).contains(sensorId)){
			this.sensorCardsInLists.get(this.activeItemId).get(sensorId).getElement().setAttribute("style",  "background-color: " + sensorColor + " !important");
		}
	}

	@Override
	public void setOneItemStyle(boolean set) {
		if(set) {
			this.collapsible.getElement().setAttribute("one","");
		}else {
			this.collapsible.getElement().removeAttribute("one");
		}
	}

	@Override
	public Integer getActiveItemId() {
		return this.activeItemId;
	}

	@Override
	public void setOldSelection() {
		if(this.collapsiblesItems.get(this.activeItemId) == null) {
			return;
		}
		this.collapsiblesItems.get(this.activeItemId).open();
	}

	@Override
	public void setActiveItemId(Integer id) {
		this.activeItemId = id;
	}

	@Override
	public void loadAllSensorInfo(Integer sensorId, BasicSensorItemCard card) {
		this.presenter.requestAllSensorInfo(sensorId, card);
	}

	@Override
	public void showAllSensorInfo(Sensor sensor, BasicSensorItemCard card) {
		if(sensor==null) {
			card.hideBody();
		}else {
			card.showSensorInfo(sensor);
		}
	}

}