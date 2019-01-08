package com.opensense.dashboard.client.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.ChartBounds;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;
import com.opensense.dashboard.shared.ValuePreview;

public interface VisualisationsView extends IDataPanelPageView {
	
	public interface Presenter{
		HandlerManager getEventBus();
		void onSelectedSensorsChange(List<Integer> newIds);
		void setListManager(ListManager listManager);
		void onDateRangeButtonClicked(DateRange dr, Date minT, Date maxT);
		DateRange getDateRange();
		List<Integer> getsensorIds();
		Map<Integer, LineDataset> getDatasetMap();
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	void initView(Runnable runnable);
	void hideLoadingIndicator();
	void showLoadingIndicator();
	boolean showChart(Map<Integer, LineDataset> datasetMap, ChartBounds chartBounds);
	void createChart();
	LineChart getChart();
	Date getStartingDate();
	Date getEndingDate();
	ListManager getListManager();
	void removeDatasetFromChart(LineDataset dataset);
	void setChartAxisY(Double minValue, Double maxValue);
	void setChartDatasets(LineDataset[] datasets);
	void highlightDateRange(DateRange dateRange);
	void addDatasetToChart(Dataset dataset);
	void setChartAxisX(Date minTimestamp, Date maxTimestamp);
	void updateDatasets(List<LineDataset> datasets);
}
