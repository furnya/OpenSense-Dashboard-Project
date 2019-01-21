package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.ChartBounds;
import com.opensense.dashboard.client.utils.ColorManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.MinMaxValueHandler;
import com.opensense.dashboard.client.utils.RequestBuilder;
import com.opensense.dashboard.client.utils.UnitMapper;
import com.opensense.dashboard.client.utils.ValueHandler;
import com.opensense.dashboard.client.view.VisualisationsView;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Request;
import com.opensense.dashboard.shared.Response;
import com.opensense.dashboard.shared.ResultType;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Unit;
import com.opensense.dashboard.shared.Value;
import com.opensense.dashboard.shared.ValuePreview;


public class VisualisationsPresenter extends DataPanelPagePresenter implements IPresenter, VisualisationsView.Presenter{

	private final VisualisationsView view;
	
	private static final Logger LOGGER = Logger.getLogger(SearchPresenter.class.getName());
	
	private static final int MAX_POINTS = 100;
	
	private List<Integer> sensorIds = new LinkedList<>();
	private Map<Integer, LineDataset> datasetMap = new HashMap<>();
	private Map<Integer, Integer> requestMap = new HashMap<>();
	
	private Date maxTimestamp = null;
	private Date minTimestamp = null;
	
	private static final DateRange DEFAULT_RANGE = DateRange.PAST_WEEK;
	private DateRange dateRange = DEFAULT_RANGE;

	private Double minValue = Double.POSITIVE_INFINITY;
	private Double maxValue = Double.NEGATIVE_INFINITY;
	
	private ListManager listManager;
	private MinMaxValueHandler minMaxHandler = new MinMaxValueHandler();
	
	public VisualisationsPresenter(HandlerManager eventBus, AppController appController, VisualisationsView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
		this.setListManager(this.view.getListManager());
	}
	
	public VisualisationsView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void onPageReturn() {
		this.view.getListManager().setUserLoggedInAndUpdate(!this.appController.isGuest());
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleParamters(Map<ParamType, String> parameters) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleIds(List<Integer> ids) {
		view.getListManager().updateSelectedSensorsList(ids);
	}

	public void onUserLoggedIn() {
		this.view.getListManager().onUserLoggedIn();
	}

	public void onUserLoggedOut() {
		this.view.getListManager().onUserLoggedOut();
	}

	@Override
	public void waitUntilViewInit(final Runnable runnable) {
		this.view.initView(runnable);
	}
	
	public void onDateRangeButtonClicked(DateRange dr, Date minT, Date maxT) {
		this.setAllSensorCardsGrey(this.sensorIds);
		minMaxHandler.reset();
		this.resetMinMax();
		this.resetDatasets();
		this.setDateRange(dr);
		view.highlightDateRange(dr);
		this.valueRequestForSensorList(this.sensorIds, dr, minT, maxT);
	}
	
	public void resetMinMax() {
		this.minTimestamp = null;
		this.maxTimestamp = null;
		this.minValue = Double.POSITIVE_INFINITY;
		this.maxValue = Double.NEGATIVE_INFINITY;
	}
	
	public void resetDatasets() {
		this.resetMinMax();
		this.datasetMap = new HashMap<>();
		view.setChartDatasets(new LineDataset[0]);
	}
	
	public void onSelectedSensorsChange(List<Integer> newIds) {
		if(newIds==null || newIds.isEmpty()) {
			this.sensorIds.forEach(this::removeDataset);
			this.view.updateDatasets(new ArrayList<>(datasetMap.values()));
			this.setSensorIdsCopy(newIds);
			this.view.showNoSensorsSelected();
			return;
		}
		if(!this.updateNeeded(newIds)) {
			this.setSensorIdsCopy(newIds);
			return;
		}
		List<Integer> idsToRemove = getListDifference(this.sensorIds, newIds);
		List<Integer> idsToAdd = getListDifference(newIds, this.sensorIds);
		idsToRemove.forEach(this::removeDataset);
		this.view.updateDatasets(new ArrayList<>(datasetMap.values()));
		this.setAllSensorCardsGrey(idsToAdd);
		this.setSensorIdsCopy(newIds);
		if(!idsToRemove.isEmpty()) {
			view.showChart(this.getDatasetMap(),this.getChartBounds());
		}
		if(this.getDateRange()==DateRange.CUSTOM && (view.getStartingDate()==null || view.getEndingDate()==null)) {
			return;
		}
		this.valueRequestForSensorList(idsToAdd, this.getDateRange(), view.getStartingDate(), view.getEndingDate());
	}
	
	public void setAllSensorCardsGrey(List<Integer> ids) {
		ids.forEach(id -> this.setCardColor(id, "#a0a0a0"));
	}
	
	public void setCardColor(int sensorId, String color) {
		this.getListManager().setSelectedSensorItemsColor(sensorId, color);
	}
	
	public void removeDataset(Integer sensorId) {
		if(!this.datasetMap.containsKey(sensorId)) {
			return;
		}
		ColorManager.getInstance().removeFromUsedColors(sensorId);
		if(minMaxHandler.removeValues(sensorId)) {
			recalculateMinMax();
		}
		this.datasetMap.remove(sensorId);
	}
	
	public void recalculateMinMax() {
		this.minValue = minMaxHandler.getMin().getNumberValue();
		this.maxValue = minMaxHandler.getMax().getNumberValue();
		this.minTimestamp = minMaxHandler.getEarliest().getTimestamp();
		this.maxTimestamp = minMaxHandler.getLatest().getTimestamp();
		view.setChartAxisY(minValue, maxValue);
		view.setChartAxisX(minTimestamp, maxTimestamp);		
	}
	
	/**
	 * @param sensors the sensors to set
	 */
	public void setSensorIds(List<Integer> sensorIds) {
		this.sensorIds = sensorIds;
	}

	/**
	 * @param sensors the sensors to set
	 */
	public void setSensorIdsCopy(List<Integer> ids) {
		this.sensorIds = new ArrayList<>(ids);
	}
	
	public List<Integer> getListDifference(List<Integer> a, List<Integer> b){
		List<Integer> diff = new LinkedList<>();
		for(Integer i : a) {
			if(!b.contains(i)) {
				diff.add(i);
			}
		}
		return diff;
	}
	
	public List<Integer> getListIntersection(List<Integer> a, List<Integer> b){
		List<Integer> diff = new LinkedList<>();
		for(Integer i : a) {
			if(b.contains(i)) {
				diff.add(i);
			}
		}
		return diff;
	}
	
	public boolean updateNeeded(List<Integer> ids) {
		if(((ids==null) || ids.isEmpty())) {
			return !this.sensorIds.isEmpty();
		}
		for(Integer id : ids) {
			if(!this.sensorIds.contains(id)) {
				return true;
			}
		}
		for(Integer id : this.datasetMap.keySet()) {
			if(!ids.contains(id)) {
				return true;
			}
		}
		return false;
	}
	
	public void addSensorValues(Integer sensorId, List<Value> values) {
		if((values == null) || values.isEmpty()) {
			return;
		}
		this.removeDataset(sensorId);
		ValueHandler valueHandler = new ValueHandler(values);

		minMaxHandler.addValueForId(sensorId, valueHandler.getEarliest());
		minMaxHandler.addValueForId(sensorId, valueHandler.getLatest());
		this.minTimestamp = minMaxHandler.getEarliest().getTimestamp();
		this.maxTimestamp = minMaxHandler.getLatest().getTimestamp();

		LineDataset dataset = this.createCrunchedDataset(values);
		DataPoint minDP = ValueHandler.getMinOfDataset(dataset);
		minMaxHandler.addValueForId(sensorId, new Value(minDP.getT(),minDP.getY()));
		DataPoint maxDP = ValueHandler.getMaxOfDataset(dataset);
		minMaxHandler.addValueForId(sensorId, new Value(maxDP.getT(),maxDP.getY()));
		this.minValue = minMaxHandler.getMin().getNumberValue();
		this.maxValue = minMaxHandler.getMax().getNumberValue();
		this.datasetMap.put(sensorId, dataset);
		this.setLineDatasetStyle(dataset, sensorId);
		view.updateDatasets(new ArrayList<>(datasetMap.values()));
	}
	
	public Integer datasetsContainId(Integer id) {
		for(Integer sensorId : this.datasetMap.keySet()) {
			if(sensorId==id) {
				return sensorId;
			}
		}
		return null;
	}
	
	public void setLineDatasetStyle(LineDataset dataset, int sensorId) {
		String color = ColorManager.getInstance().getNewColor(sensorId);
		dataset.setBorderColor(color);
		this.setCardColor(sensorId, color);
		this.listManager.setSelectedSensorItemsColor(sensorId, color);
		dataset.setPointBackgroundColor(color);
		dataset.setFill(Fill.nofill);
		dataset.setLabel(""+sensorId);
	}
	
	public LineDataset createCrunchedDataset(List<Value> values) {
		LineDataset dataset = view.getChart().newDataset();
		ArrayList<DataPoint> pointsList = new ArrayList<>();
		int step = (values.size()<MAX_POINTS? MAX_POINTS : values.size())/MAX_POINTS;
		for(int i=0;i<values.size();i+=step) {
			Double numberValueAvg = 0.0;
			long timestampAvg = 0;
			int divideBy = 0;
			for(int j=i;(j<(i+step)) && (j<values.size());j++) {
				Value value = values.get(j);
				numberValueAvg += value.getNumberValue();
				timestampAvg += value.getTimestamp().getTime();
				divideBy++;
			}
			if(divideBy==0) {
				continue;
			}
			DataPoint p = new DataPoint();
			p.setT(new Date(timestampAvg/divideBy));
			p.setY(numberValueAvg/divideBy);
			pointsList.add(p);
		}
		DataPoint[] points = new DataPoint[pointsList.size()];
		points = pointsList.toArray(points);
		dataset.setDataPoints(points);
		return dataset;
	}
	
	public void valueRequestForSensorList(List<Integer> sensorIds, DateRange dateRange, Date minDate, Date maxDate) {
		sensorIds.forEach(sensorId -> buildValueRequestAndSend(sensorId, dateRange, minDate, maxDate));
	}
	
	public void buildValueRequestAndSend(Integer id, DateRange dateRange, Date minDate, Date maxDate) {
		final RequestBuilder requestBuilder = new RequestBuilder(ResultType.VALUE_AGGREGATED, true);
		requestBuilder.setIds(new LinkedList<Integer>());
		requestBuilder.addId(id);
		requestBuilder.setDateRange(dateRange);
		if(minDate != null && dateRange==DateRange.CUSTOM) requestBuilder.addParameter(ParamType.MIN_TIMESTAMP, minDate.toGMTString().replace(" ", "%20"));
		if(maxDate != null && dateRange==DateRange.CUSTOM) requestBuilder.addParameter(ParamType.MAX_TIMESTAMP, maxDate.toGMTString().replace(" ", "%20"));
		requestBuilder.getRequest().getParameters().forEach(param -> GWT.log("RequestParam: " + param.getKey() + " " + param.getValue()));
		sendRequest(requestBuilder.getRequest());
	}
	
	private void sendRequest(final Request request) {
		requestMap.put(request.getIds().get(0),request.hashCode());
		GeneralService.Util.getInstance().getDataFromRequest(request, new DefaultAsyncCallback<Response>(result -> {
			if(result != null && result.getResultType() != null && request.getRequestType().equals(result.getResultType()) && result.getValues() != null && result.getSensors() != null) {
				if(requestMap.get(request.getIds().get(0))!=request.hashCode()) {
					return;
				}
				requestMap.remove(request.getIds().get(0));
				UnitMapper.getInstance().putUnit(result.getSensors().get(0).getSensorId(), result.getSensors().get(0).getUnit());
				this.addSensorValues(result.getSensors().get(0).getSensorId(), result.getValues());
				view.showChart(this.getDatasetMap(),this.getChartBounds());
			}else {
				LOGGER.log(Level.WARNING, "Result is null or did not match the expected ResultType.");
			}
		},caught -> {
			LOGGER.log(Level.WARNING, "Failure requesting the values.");
			view.hideLoadingIndicator();
		}, false));
	}
	
	private ChartBounds getChartBounds() {
		return new ChartBounds(minValue, maxValue, minTimestamp, maxTimestamp);
	}

	public void updateFavoriteList() {
		this.view.getListManager().updateFavoriteList();
	}

	/**
	 * @return the listManager
	 */
	public ListManager getListManager() {
		return this.listManager;
	}

	/**
	 * @param listManager the listManager to set
	 */
	public void setListManager(ListManager listManager) {
		this.listManager = listManager;
	}
	
	/**
	 * @return the dateRange
	 */
	public DateRange getDateRange() {
		return this.dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	
	/**
	 * @return the defaultRange
	 */
	public DateRange getDefaultRange() {
		return DEFAULT_RANGE;
	}

	@Override
	public List<Integer> getsensorIds() {
		return this.sensorIds;
	}

	@Override
	public Map<Integer, LineDataset> getDatasetMap() {
		return this.datasetMap;
	}
}
