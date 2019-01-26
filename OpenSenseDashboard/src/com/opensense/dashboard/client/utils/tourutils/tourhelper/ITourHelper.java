package com.opensense.dashboard.client.utils.tourutils.tourhelper;

import java.util.List;

import com.opensense.dashboard.client.utils.tourutils.TourStepData;

public interface ITourHelper {
	void prepare(Runnable runnable);
	List<TourStepData> getTourData();
	void cleanUp();
}
