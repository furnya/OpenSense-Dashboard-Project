package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.CartesianAxisType;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.client.options.scales.CartesianTimeAxis;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.client.plugins.impl.ChartBackgroundColor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.ListManagerOptions;
import com.opensense.dashboard.client.utils.PagerSize;
import com.opensense.dashboard.client.utils.ValueHandler;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Value;

import gwt.material.design.client.constants.DatePickerLanguage;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPreLoader;

public class VisualisationsViewImpl extends DataPanelPageView implements VisualisationsView {

	@UiTemplate("VisualisationsView.ui.xml")
	interface VisualisationsViewUiBinder extends UiBinder<Widget, VisualisationsViewImpl> {
	}

	@UiField
	Div container;

	@UiField
	MaterialPreLoader viewSpinner;

	@UiField
	Div chartContainer;

	@UiField
	MaterialButton customRange;

	@UiField
	MaterialButton pastYear;

	@UiField
	MaterialButton pastMonth;

	@UiField
	MaterialButton pastWeek;

	@UiField
	MaterialButton past24Hours;

	@UiField
	MaterialDatePicker startingDate;

	@UiField
	MaterialDatePicker endingDate;

	@UiField
	MaterialLabel noDatasetsLabel;

	@UiField
	Div listContainer;

	private static VisualisationsViewUiBinder uiBinder = GWT.create(VisualisationsViewUiBinder.class);

	protected Presenter presenter;

	private List<Integer> sensorIds = new LinkedList<>();
	private Map<Integer, LineDataset> datasetMap = new HashMap<>();

	private static final DateRange DEFAULT_RANGE = DateRange.PAST_WEEK;
	private DateRange dateRange = DEFAULT_RANGE;

	private static final int MAX_POINTS = 100;

	private LineChart chart;

	private Date maxTimestamp = null;
	private Date minTimestamp = null;

	private Double minValue = Double.POSITIVE_INFINITY;
	private Double maxValue = Double.NEGATIVE_INFINITY;

	private String[] colors = {"#5899DA","#E8743B","#19A979","#ED4A7B","#945ECF","#13A4B4","#525DF4","#BF399E","#6C8893","#EE6868","#2F6497"};
	private Map<Integer,String> usedColors = new HashMap<>();
	private int nextColor = 0;

	private CartesianTimeAxis xAxis;
	private CartesianLinearAxis yAxis;

	private ListManager listManager;

	public VisualisationsViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView(Runnable runnable) {
		this.createChart();
		this.setDatePickerOptions();
		this.highlightDateRange();
		this.listContainer.clear();
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(), this.listContainer);
		listManagerOptions.setEditingActive(true);
		listManagerOptions.setPagerSize(PagerSize.SMALL);
		listManagerOptions.setShowMapButton(true);
		listManagerOptions.setShowVisualizationButton(false);
		listManagerOptions.setShowSearchButton(true);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> {
			if(!this.updateNeeded(event.getSelectedIds())) {
				this.setSensorIdsCopy(event.getSelectedIds());
				return;
			}
			this.resetDatasets();
			this.setSensorIdsCopy(event.getSelectedIds());
			this.setAllSensorCardsGrey();
			if((event.getSelectedIds()==null) || event.getSelectedIds().isEmpty()) {
				this.showChart();
			}
			this.presenter.valueRequestForSensorList(event.getSelectedIds(), this.getDateRange(), this.getStartingDate(), this.getEndingDate());
		});
	}

	@UiHandler("customRange")
	public void onCustomRangeButtonClicked(ClickEvent e) {
		if((this.startingDate.getDate()==null) || (this.endingDate.getDate()==null)) {
			return;
		}
		this.dateRangeButtonClicked(DateRange.CUSTOM, this.startingDate.getDate(), this.endingDate.getDate());
	}

	@UiHandler("pastYear")
	public void onPastYearButtonClicked(ClickEvent e) {
		this.dateRangeButtonClicked(DateRange.PAST_YEAR, null, null);
	}

	@UiHandler("pastMonth")
	public void onPastMonthButtonClicked(ClickEvent e) {
		this.dateRangeButtonClicked(DateRange.PAST_MONTH, null, null);
	}

	@UiHandler("pastWeek")
	public void onPastWeekButtonClicked(ClickEvent e) {
		this.dateRangeButtonClicked(DateRange.PAST_WEEK, null, null);
	}

	@UiHandler("past24Hours")
	public void onPast24HoursButtonClicked(ClickEvent e) {
		this.dateRangeButtonClicked(DateRange.PAST_24HOURS, null, null);
	}

	public void dateRangeButtonClicked(DateRange dr, Date minT, Date maxT) {
		this.resetColors();
		this.resetChart();
		this.resetDatasets();
		this.setDateRange(dr);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.sensorIds, dr, minT, maxT);
	}

	@Override
	public void showLoadingIndicator() {
		this.viewSpinner.getElement().getStyle().clearDisplay();
	}

	@Override
	public void hideLoadingIndicator() {
		this.viewSpinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	@Override
	public void addSensorValues(Sensor sensor, List<Value> values) {
		if((sensor == null) || (values == null) || values.isEmpty()) {
			return;
		}
		ValueHandler valueHandler = new ValueHandler(values);
		List<Value> filteredValues = valueHandler.getValues();

		Date earliest = valueHandler.getEarliest().getTimestamp();
		Date latest = valueHandler.getLatest().getTimestamp();
		if((this.minTimestamp==null) || (this.minTimestamp.compareTo(earliest)>0)) {
			this.minTimestamp = earliest;
		}
		if((this.maxTimestamp==null) || (this.maxTimestamp.compareTo(latest)<0)) {
			this.maxTimestamp = latest;
		}

		LineDataset dataset = this.createCrunchedDataset(filteredValues);
		Double lowest = ValueHandler.getMinOfDataset(dataset);
		Double highest = ValueHandler.getMaxOfDataset(dataset);
		if(this.minValue > lowest) {
			this.minValue = lowest;
		}
		if(this.maxValue < highest) {
			this.maxValue = highest;
		}
		Integer oldSensorId = this.datasetsContainId(sensor.getSensorId());
		if(oldSensorId != null) {
			this.datasetMap.remove(oldSensorId);
		}
		this.datasetMap.put(sensor.getSensorId(), dataset);
		this.setLineDatasetStyle(dataset, sensor.getSensorId());
		this.addDatasetToChart(dataset);
	}

	public LineDataset createNormalDataset(List<Value> values) {
		LineDataset dataset = this.chart.newDataset();
		ArrayList<DataPoint> pointsList = new ArrayList<>();
		for(int i=0;i<values.size();i++) {
			Value value = values.get(i);
			DataPoint p = new DataPoint();
			p.setT(value.getTimestamp());
			p.setY(value.getNumberValue());
			pointsList.add(p);
		}
		DataPoint[] points = new DataPoint[pointsList.size()];
		points = pointsList.toArray(points);
		dataset.setDataPoints(points);
		return dataset;
	}

	public LineDataset createCrunchedDataset(List<Value> values) {
		LineDataset dataset = this.chart.newDataset();
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

	/**
	 * @return the sensors
	 */
	@Override
	public List<Integer> getSensorIds() {
		return this.sensorIds;
	}

	/**
	 * @param sensors the sensors to set
	 */
	@Override
	public void setSensorIds(List<Integer> sensorIds) {
		this.sensorIds = sensorIds;
	}

	/**
	 * @param sensors the sensors to set
	 */
	public void setSensorIdsCopy(List<Integer> ids) {
		this.sensorIds = new ArrayList<Integer>(ids);
	}

	/**
	 * @return the defaultRange
	 */
	@Override
	public DateRange getDefaultRange() {
		return DEFAULT_RANGE;
	}

	@Override
	public void createChart() {
		this.chart = new LineChart();
		this.chart.getOptions().setShowLines(true);
		try {
			Defaults.getPlugins().register(new ChartBackgroundColor());
		} catch (InvalidPluginIdException e) {
			GWT.log(e.toString());
		}
		this.xAxis = new CartesianTimeAxis(this.chart, CartesianAxisType.x);
		this.xAxis.setDistribution(ScaleDistribution.linear);
		this.xAxis.setBounds(ScaleBounds.ticks);
		this.xAxis.getTime().setStepSize(1);
		this.yAxis = new CartesianLinearAxis(this.chart, CartesianAxisType.y);
		this.chart.getOptions().getScales().setXAxes(this.xAxis);
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
	}

	@Override
	public boolean showChart() {
		this.hideLoadingIndicator();
		this.chartContainer.clear();
		if((this.sensorIds==null) || this.sensorIds.isEmpty() || (this.datasetMap==null) || this.datasetMap.isEmpty()) {
			this.resetMinMax();
			this.showNoDatasetsIndicator(true);
			return false;
		}
		this.showNoDatasetsIndicator(false);
		TimeUnit tu = this.calculateTimeUnit();
		this.xAxis.getTime().setMin(this.minTimestamp);
		this.xAxis.getTime().setMax(this.maxTimestamp);
		this.xAxis.getTime().setUnit(tu);
		this.xAxis.getTime().setTooltipFormat("DD MMM YYYY, HH:mm");
		this.xAxis.getTime().getDisplayFormats().setDisplayFormat(tu, this.getDisplayFormat(tu));
		this.yAxis.getTicks().setMin(Math.floor(this.minValue-((this.maxValue-this.minValue)*0.1)));
		this.yAxis.getTicks().setMax(Math.ceil(this.maxValue+((this.maxValue-this.minValue)*0.1)));
		this.chart.getOptions().getScales().setXAxes(this.xAxis);
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
		this.chartContainer.add(this.chart);
		return true;
	}

	public void resetChart() {
		this.chartContainer.clear();
		this.createChart();
		this.minTimestamp = null;
		this.maxTimestamp = null;
		this.minValue = Double.POSITIVE_INFINITY;
		this.maxValue = Double.NEGATIVE_INFINITY;
	}

	public void addDatasetToChart(Dataset dataset) {
		ArrayList<Dataset> datasets = new ArrayList<>();
		this.chart.getData().getDatasets().forEach(datasets::add);
		datasets.add(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		this.chart.getData().setDatasets(newDatasets);
	}

	public void setLineDatasetStyle(LineDataset dataset, int sensorId) {
		String color = this.getNewColor(sensorId);
		dataset.setBorderColor(color);
		this.setCardColor(sensorId, color);
		this.listManager.setSelectedSensorItemsColor(sensorId, color);
		dataset.setPointBackgroundColor(color);
		dataset.setFill(Fill.nofill);
		dataset.setLabel(""+sensorId);
	}
	
	public void setCardColor(int sensorId, String color) {
		this.listManager.setSelectedSensorItemsColor(sensorId, color);
	}
	
	public void setAllSensorCardsGrey() {
		sensorIds.forEach(id -> this.setCardColor(id, "#a0a0a0"));
	}

	public void showNoDatasetsIndicator(boolean show) {
		if(show) {
			this.noDatasetsLabel.getElement().getStyle().clearDisplay();
		}else {
			this.noDatasetsLabel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void removeSensorDatasetFromChart(Integer sensorId) {
		this.usedColors.remove(sensorId);
		LineDataset dataset = this.datasetMap.remove(sensorId);
		ArrayList<Dataset> datasets = new ArrayList<>();
		this.chart.getData().getDatasets().forEach(datasets::add);
		datasets.remove(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		this.chart.getData().setDatasets(newDatasets);
		this.recalculateMinMax();
		this.showChart();
	}

	public void addSensorDatasetToChart(Integer sensorId) {
		this.presenter.buildValueRequestAndSend(sensorId, this.getDateRange(), this.startingDate.getDate(), this.endingDate.getDate());
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

	public Integer datasetsContainId(Integer id) {
		for(Integer sensorId : this.datasetMap.keySet()) {
			if(sensorId==id) {
				return sensorId;
			}
		}
		return null;
	}

	public TimeUnit calculateTimeUnit() {
		TimeUnit tu = TimeUnit.hour;
		if((this.maxTimestamp==null) || (this.minTimestamp==null)) {
			return tu;
		}
		long timeDifference = this.maxTimestamp.getTime()-this.minTimestamp.getTime();
		if((172800000.0 < timeDifference) && (timeDifference < 5184000000.0)) {
			tu = TimeUnit.day;
		}else if((5184000000.0 < timeDifference) && (timeDifference < 63072000000.0)) {
			tu = TimeUnit.month;
		}else if(timeDifference > 63072000000.0) {
			tu = TimeUnit.year;
		}
		return tu;
	}

	public String getDisplayFormat(TimeUnit tu) {
		switch(tu) {
		case day:
			return "DD MMM";
		case hour:
			return "HH:mm";
		case month:
			return "MMM YYYY";
		case year:
			return "YYYY";
		default:
			return "DD MMM";
		}
	}

	public String getNewColor(int sensorId) {
		if(this.usedColors.size()!=this.colors.length) {
			for(int i=0;i<this.colors.length;i++) {
				if(!this.usedColors.values().contains(this.colors[i])) {
					this.nextColor = i;
				}
			}
		}
		String color = this.colors[this.nextColor];
		this.usedColors.put(sensorId,color);
		this.nextColor = (this.nextColor+1)%this.colors.length;
		return color;
	}

	public void setDatePickerOptions() {
		this.startingDate.setDateMax(new Date());
		this.endingDate.setDateMax(new Date());
		CloseHandler<MaterialDatePicker> ch = event -> this.customRange.setFocus(true);
		this.startingDate.addCloseHandler(ch);
		this.endingDate.addCloseHandler(ch);
		DatePickerLanguage lang = Languages.isGerman()? DatePickerLanguage.DE : DatePickerLanguage.EN;
		this.startingDate.setLanguage(lang);
		this.endingDate.setLanguage(lang);
	}

	@Override
	public void resetDatasets() {
		this.resetMinMax();
		this.datasetMap = new HashMap<>();
		Dataset[] emptyDatasetArray = new Dataset[0];
		this.chart.getData().setDatasets(emptyDatasetArray);
	}


	public void highlightDateRange() {
		this.customRange.getElement().removeClassName("active");
		this.past24Hours.getElement().removeClassName("active");
		this.pastMonth.getElement().removeClassName("active");
		this.pastWeek.getElement().removeClassName("active");
		this.pastYear.getElement().removeClassName("active");
		switch(this.dateRange) {
		case CUSTOM:
			this.customRange.getElement().addClassName("active");
			break;
		case PAST_24HOURS:
			this.past24Hours.getElement().addClassName("active");
			break;
		case PAST_MONTH:
			this.pastMonth.getElement().addClassName("active");
			break;
		case PAST_WEEK:
			this.pastWeek.getElement().addClassName("active");
			break;
		case PAST_YEAR:
			this.pastYear.getElement().addClassName("active");
			break;
		default:
			break;

		}
	}

	public void resetMinMax() {
		this.minTimestamp = null;
		this.maxTimestamp = null;
		this.minValue = Double.POSITIVE_INFINITY;
		this.maxValue = Double.NEGATIVE_INFINITY;
	}

	public void recalculateMinMax() {
		LinkedList<LineDataset> datasets = new LinkedList<>(this.datasetMap.values());
		this.minValue = ValueHandler.getMinOfDatasets(datasets);
		this.maxValue = ValueHandler.getMaxOfDatasets(datasets);
		this.yAxis.getTicks().setMin(Math.floor(this.minValue-((this.maxValue-this.minValue)*0.1)));
		this.yAxis.getTicks().setMax(Math.ceil(this.maxValue+((this.maxValue-this.minValue)*0.1)));
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
	}

	public void resetColors() {
		this.usedColors.clear();
		this.nextColor = 0;
	}

	@Override
	public Date getStartingDate() {
		return this.startingDate.getDate();
	}

	@Override
	public Date getEndingDate() {
		return this.endingDate.getDate();
	}

	@Override
	public boolean updateNeeded(List<Integer> ids) {
		if(((ids==null) || ids.isEmpty())) {
			if(this.sensorIds.isEmpty()) {
				return false;
			}
			return true;
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

	@Override
	public ListManager getListManager() {
		return this.listManager;
	}
}