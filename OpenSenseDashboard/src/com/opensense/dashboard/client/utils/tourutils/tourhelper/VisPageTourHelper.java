package com.opensense.dashboard.client.utils.tourutils.tourhelper;

import java.util.LinkedList;
import java.util.List;

import org.gwtbootstrap3.client.ui.constants.Placement;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.tourutils.TourEventType;
import com.opensense.dashboard.client.utils.tourutils.TourStepData;

public class VisPageTourHelper implements ITourHelper{

	@Override
	public void prepare(Runnable runnable) {
		List<Integer> favs = CookieManager.getFavoriteList();
		if(!favs.contains(10186)) favs.add(0, 10186);
		if(!favs.contains(47794)) favs.add(0, 47794);
		if(!favs.contains(47792)) favs.add(0, 47792);
		if(!favs.contains(47790)) favs.add(0, 47790);
		if(!favs.contains(47785)) favs.add(0, 47785);
		CookieManager.writeFavoriteListCookie(favs);
		History.newItem(DataPanelPage.VISUALISATIONS.name());
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
		tours.addLast(new TourStepData("list_vis_container", Languages.listContainerVisTour(), Placement.LEFT, TourEventType.HINT_TRY_OUT));
		tours.addLast(new TourStepData("timespan_vis_panel", Languages.timespanPanelTour(), Placement.BOTTOM, TourEventType.HINT_TRY_OUT));
		tours.addLast(new TourStepData("start_vis_picker", Languages.startVisPickerTour(), Placement.BOTTOM, TourEventType.HINT));
		tours.addLast(new TourStepData("chart_vis_container", Languages.chartVisContainerTour(), Placement.RIGHT, TourEventType.HINT_TRY_OUT));
		return tours;
	}

	@Override
	public void cleanUp() {
	}
}
