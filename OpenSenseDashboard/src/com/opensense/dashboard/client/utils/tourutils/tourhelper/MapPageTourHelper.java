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

public class MapPageTourHelper implements ITourHelper{

	@Override
	public void prepare(Runnable runnable) {
		List<Integer> favs = CookieManager.getFavoriteList();
		if(!favs.contains(301574)) favs.add(0, 301574);
		if(!favs.contains(332713)) favs.add(0, 332713);
		CookieManager.writeFavoriteListCookie(favs);
		History.newItem(DataPanelPage.MAP.name());
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
		tours.addLast(new TourStepData("list_map_container", Languages.mapPageTourStep1(), Placement.LEFT, TourEventType.HINT_TRY_OUT));
		tours.addLast(new TourStepData("map", Languages.mapPageTourStep2(), Placement.RIGHT, TourEventType.HINT));
		return tours;
	}

	@Override
	public void cleanUp() {
	}
}
