package com.opensense.dashboard.client.utils.tourutils.tourhelper;

import java.util.LinkedList;
import java.util.List;

import org.gwtbootstrap3.client.ui.constants.Placement;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.Rectangle;
import com.opensense.dashboard.client.utils.tourutils.TourEventType;
import com.opensense.dashboard.client.utils.tourutils.TourStepData;

public class ListPageTourHelper implements ITourHelper{

	@Override
	public void prepare(Runnable runnable) {
		History.newItem(DataPanelPage.LISTS.name());
		//TODO: wait till mearands arrived ::workaround with timer
		new Timer() {
			@Override
			public void run() {
				runnable.run();
			}
		}.schedule(500);
	}

	@Override
	public List<TourStepData> getTourData(){
		LinkedList<TourStepData> tours = new LinkedList<>();
		tours.addLast(new TourStepData("list_createList_button", Languages.createListButtonTour(), Placement.BOTTOM, TourEventType.HINT));
		tours.addLast(new TourStepData("list_createSensor_button", Languages.createSensorButtonTour(), Placement.BOTTOM, TourEventType.HINT));
		tours.addLast(new TourStepData("list_list_container", Languages.accuracyTour(), Placement.BOTTOM,TourEventType.HINT));
		return tours;
	}

	@Override
	public void cleanUp() {
	}
}
