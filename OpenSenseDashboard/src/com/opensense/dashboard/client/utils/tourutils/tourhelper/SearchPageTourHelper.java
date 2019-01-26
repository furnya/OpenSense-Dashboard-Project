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

public class SearchPageTourHelper implements ITourHelper{

	@Override
	public void prepare(Runnable runnable) {
		History.newItem(DataPanelPage.SEARCH.name());
		//TODO: wait till mearands arrived ::workaround with timer
		new Timer() {
			@Override
			public void run() {
				runnable.run();
			}
		}.schedule(2000);
	}

	@Override
	public List<TourStepData> getTourData(){
		LinkedList<TourStepData> tours = new LinkedList<>();
		tours.addLast(new TourStepData("place_search_input", Languages.placeInputTour(), Placement.BOTTOM, TourEventType.HINT));
		tours.addLast(new TourStepData("measurand_search_listbox", Languages.measurandsTour(), Placement.BOTTOM, new Rectangle(-5,0,5,-15), TourEventType.HINT));
		tours.addLast(new TourStepData("maxsensor_search_textbox", Languages.maxSensorsTour(), Placement.BOTTOM, new Rectangle(-5,0,5,-15), TourEventType.HINT));
		tours.addLast(new TourStepData("accuracy_search_textbox", Languages.accuracyTour(), Placement.BOTTOM, new Rectangle(-5,0,5,-15), TourEventType.HINT));
		tours.addLast(new TourStepData("search_search_button", Languages.searchButtonTour(), Placement.BOTTOM));
		tours.addLast(new TourStepData("sensorwithvalues_search_div", Languages.sensorsWithValuesTour(), Placement.LEFT, TourEventType.HINT_TRY_OUT));

		return tours;
	}

	@Override
	public void cleanUp() {
	}
}
