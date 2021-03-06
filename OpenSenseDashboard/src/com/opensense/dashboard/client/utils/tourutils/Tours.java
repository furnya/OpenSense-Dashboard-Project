package com.opensense.dashboard.client.utils.tourutils;

import java.util.List;

import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.utils.tourutils.tourhelper.ITourHelper;
import com.opensense.dashboard.client.utils.tourutils.tourhelper.ListPageTourHelper;
import com.opensense.dashboard.client.utils.tourutils.tourhelper.MapPageTourHelper;
import com.opensense.dashboard.client.utils.tourutils.tourhelper.SearchPageTourHelper;
import com.opensense.dashboard.client.utils.tourutils.tourhelper.VisPageTourHelper;

public enum Tours implements ITourHelper{

	SEARCH_PAGE {
		@Override public ITourHelper createTourHelperInstance() { return new SearchPageTourHelper(); }
		@Override public String getTitle() { return ""; }
		@Override public String getDescription() { return ""; }
		@Override public String getCardImageUrl() { return GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString(); }
	},
	VIS_PAGE {
		@Override public ITourHelper createTourHelperInstance() { return new VisPageTourHelper(); }
		@Override public String getTitle() { return ""; }
		@Override public String getDescription() { return ""; }
		@Override public String getCardImageUrl() { return GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString(); }
	},
	LIST_PAGE{
		@Override public ITourHelper createTourHelperInstance() { return new ListPageTourHelper(); }
		@Override public String getTitle() { return ""; }
		@Override public String getDescription() { return ""; }
		@Override public String getCardImageUrl() { return GUIImageBundle.INSTANCE.searchIconSvg().getSafeUri().asString(); }
	},
	MAP_PAGE{
		@Override public ITourHelper createTourHelperInstance() { return new MapPageTourHelper(); }
		@Override public String getTitle() { return ""; }
		@Override public String getDescription() { return ""; }
		@Override public String getCardImageUrl() { return GUIImageBundle.INSTANCE.mapIconSvg().getSafeUri().asString(); }
	};


	public abstract ITourHelper createTourHelperInstance();

	public abstract String getTitle();

	public abstract String getDescription();

	public abstract String getCardImageUrl();

	@Override
	public List<TourStepData> getTourData() {
		return this.createTourHelperInstance().getTourData();
	}

	/**
	 * Reset the added style or classes
	 */
	@Override
	public void cleanUp() {
		this.createTourHelperInstance().cleanUp();
	}

	/**
	 * The runnable gets called if the preparations are finished
	 */
	@Override
	public void prepare(Runnable runnable) {
		this.createTourHelperInstance().prepare(runnable);
	}
}
