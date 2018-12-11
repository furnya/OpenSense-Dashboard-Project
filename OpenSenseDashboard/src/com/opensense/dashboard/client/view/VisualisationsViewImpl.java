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

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.SVG;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.ListManagerOptions;
import com.opensense.dashboard.client.utils.PagerSize;
import com.opensense.dashboard.client.utils.ValueHandler;
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.MeasurandType;
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
	
//	@UiField
//	DateBox newdp;
	
//	@UiField
//	MaterialTimePicker startingTime;
//	
//	@UiField
//	MaterialTimePicker endingTime;
	
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
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView(Runnable runnable) {
		createChart();
		setDatePickerOptions();
		highlightDateRange();
		listContainer.clear();
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(), this.listContainer);
		listManagerOptions.setEditingActive(true);
		listManagerOptions.setPagerSize(PagerSize.SMALL);
		listManagerOptions.setShowMapButton(true);
		listManagerOptions.setShowVisualizationButton(false);
		listManagerOptions.setShowSearchButton(true);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> {
			presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.VISUALISATIONS, true, event.getSelectedIds()));
		});
	}
	
	@UiHandler("customRange")
	public void onCustomRangeButtonClicked(ClickEvent e) {
		if(startingDate.getDate()==null || endingDate.getDate()==null) return;
		dateRangeButtonClicked(DateRange.CUSTOM, startingDate.getDate(), endingDate.getDate());
	}
	
	@UiHandler("pastYear")
	public void onPastYearButtonClicked(ClickEvent e) {
		dateRangeButtonClicked(DateRange.PAST_YEAR, null, null);
	}
	
	@UiHandler("pastMonth")
	public void onPastMonthButtonClicked(ClickEvent e) {
		dateRangeButtonClicked(DateRange.PAST_MONTH, null, null);
	}
	
	@UiHandler("pastWeek")
	public void onPastWeekButtonClicked(ClickEvent e) {
		dateRangeButtonClicked(DateRange.PAST_WEEK, null, null);
	}
	
	@UiHandler("past24Hours")
	public void onPast24HoursButtonClicked(ClickEvent e) {
		dateRangeButtonClicked(DateRange.PAST_24HOURS, null, null);
	}
	
	public void dateRangeButtonClicked(DateRange dr, Date minT, Date maxT) {
		resetColors();
		resetChart();
		resetDatasets();
		setDateRange(dr);
		highlightDateRange();
		presenter.valueRequestForSensorList(sensorIds, dr, minT, maxT);
	}
	
	@Override
	public void showLoadingIndicator() {
		viewSpinner.getElement().getStyle().clearDisplay();
	}
	
	@Override
	public void hideLoadingIndicator() {
		viewSpinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	@Override
	public void addSensorValues(Sensor sensor, List<Value> values) {
		if(sensor == null || values == null || values.isEmpty()) return;
		ValueHandler valueHandler = new ValueHandler(values);
		List<Value> filteredValues = valueHandler.getValues();
		
		Date earliest = valueHandler.getEarliest().getTimestamp();
		Date latest = valueHandler.getLatest().getTimestamp();
		if(minTimestamp==null || minTimestamp.compareTo(earliest)>0) minTimestamp = earliest;
		if(maxTimestamp==null || maxTimestamp.compareTo(latest)<0) maxTimestamp = latest;
		
		LineDataset dataset = createCrunchedDataset(filteredValues);
		Double lowest = ValueHandler.getMinOfDataset(dataset);
		Double highest = ValueHandler.getMaxOfDataset(dataset);
		if(minValue > lowest) minValue = lowest;
		if(maxValue < highest) maxValue = highest;
		Integer oldSensorId = datasetsContainId(sensor.getSensorId());
		if(oldSensorId != null) datasetMap.remove(oldSensorId);
		datasetMap.put(sensor.getSensorId(), dataset);
		setLineDatasetStyle(dataset, sensor.getSensorId());
		addDatasetToChart(dataset);
	}
	
	public LineDataset createNormalDataset(List<Value> values) {
		LineDataset dataset = chart.newDataset();
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
		LineDataset dataset = chart.newDataset();
		ArrayList<DataPoint> pointsList = new ArrayList<>();
		int step = (values.size()<MAX_POINTS? MAX_POINTS : values.size())/MAX_POINTS;
		for(int i=0;i<values.size();i+=step) {
			Double numberValueAvg = 0.0;
			long timestampAvg = 0;
			int divideBy = 0;
			for(int j=i;j<i+step && j<values.size();j++) {
				Value value = values.get(j);
				numberValueAvg += value.getNumberValue();
				timestampAvg += value.getTimestamp().getTime();
				divideBy++;
			}
			if(divideBy==0) continue;
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
	public List<Integer> getSensorIds() {
		return sensorIds;
	}

	/**
	 * @param sensors the sensors to set
	 */
	public void setSensorIds(List<Integer> sensorIds) {
		this.sensorIds = sensorIds;
	}

	/**
	 * @return the defaultRange
	 */
	public DateRange getDefaultRange() {
		return DEFAULT_RANGE;
	}
	
	public void createChart() {
		chart = new LineChart();
		chart.getOptions().setShowLines(true);
		try {
			Defaults.getPlugins().register(new ChartBackgroundColor());
		} catch (InvalidPluginIdException e) {
			GWT.log(e.toString());
		}
		xAxis = new CartesianTimeAxis(chart, CartesianAxisType.x);
		xAxis.setDistribution(ScaleDistribution.linear);
		xAxis.setBounds(ScaleBounds.ticks);
		xAxis.getTime().setStepSize(1);
		yAxis = new CartesianLinearAxis(chart, CartesianAxisType.y);
		chart.getOptions().getScales().setXAxes(xAxis);
		chart.getOptions().getScales().setYAxes(yAxis);
	}
	
	public boolean showChart() {
		hideLoadingIndicator();
		chartContainer.clear();
		if(sensorIds==null || sensorIds.isEmpty() || datasetMap==null || datasetMap.isEmpty()) {
			resetMinMax();
			showNoDatasetsIndicator(true);
			return false;
		}
		showNoDatasetsIndicator(false);
		TimeUnit tu = calculateTimeUnit();
		xAxis.getTime().setMin(minTimestamp);
		xAxis.getTime().setMax(maxTimestamp);
		xAxis.getTime().setUnit(tu);
		xAxis.getTime().setTooltipFormat("DD MMM YYYY, HH:mm");
		xAxis.getTime().getDisplayFormats().setDisplayFormat(tu, getDisplayFormat(tu));
		yAxis.getTicks().setMin(Math.floor(minValue-(maxValue-minValue)*0.1));
		yAxis.getTicks().setMax(Math.ceil(maxValue+(maxValue-minValue)*0.1));
		chart.getOptions().getScales().setXAxes(xAxis);
		chart.getOptions().getScales().setYAxes(yAxis);
		chartContainer.add(chart);
		return true;
	}
	
	public void resetChart() {
		chartContainer.clear();
		createChart();
		minTimestamp = null;
		maxTimestamp = null;
		minValue = Double.POSITIVE_INFINITY;
		maxValue = Double.NEGATIVE_INFINITY;
	}
	
	public void addDatasetToChart(Dataset dataset) {
		ArrayList<Dataset> datasets = new ArrayList<>();
		chart.getData().getDatasets().forEach(datasets::add);
		datasets.add(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		chart.getData().setDatasets(newDatasets);
	}
	
	public void setLineDatasetStyle(LineDataset dataset, int sensorId) {
		String color = getNewColor(sensorId);
		dataset.setBorderColor(color);
		setCardColor(color,sensorId);
		dataset.setPointBackgroundColor(color);
		dataset.setFill(Fill.nofill);
		dataset.setLabel(""+sensorId);
	}
	
	private void setCardColor(String color, int sensorId) {
		BasicSensorItemCard card = this.listManager.getView().getSensorCardMap(-1).get(sensorId);
		card.getElement().removeClassName("card-active");
		card.getElement().getStyle().setBackgroundColor(color);
	}

	public void firstD3Chart() {
		Selection s = D3.select(Document.get().getBody());
		SVG svg = D3.svg();
		Axis x = svg.axis();
		s.call(x);
	}
	
	public void showNoDatasetsIndicator(boolean show) {
		if(show) {
			noDatasetsLabel.getElement().getStyle().clearDisplay();
		}else {
			noDatasetsLabel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	public void removeSensorDatasetFromChart(Integer sensorId) {
		usedColors.remove(sensorId);
		LineDataset dataset = datasetMap.remove(sensorId);
		ArrayList<Dataset> datasets = new ArrayList<>();
		chart.getData().getDatasets().forEach(datasets::add);
		datasets.remove(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		chart.getData().setDatasets(newDatasets);
		recalculateMinMax();
		showChart();
	}
	
	public void addSensorDatasetToChart(Integer sensorId) {
		presenter.buildValueRequestAndSend(sensorId, getDateRange(), startingDate.getDate(), endingDate.getDate());
	}

	/**
	 * @return the dateRange
	 */
	public DateRange getDateRange() {
		return dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	
	public Integer datasetsContainId(Integer id) {
		for(Integer sensorId : datasetMap.keySet()) {
			if(sensorId==id) return sensorId;
		}
		return null;
	}
	
	public TimeUnit calculateTimeUnit() {
		TimeUnit tu = TimeUnit.hour;
		if(maxTimestamp==null || minTimestamp==null) return tu;
		long timeDifference = maxTimestamp.getTime()-minTimestamp.getTime();
		if(172800000.0 < timeDifference && timeDifference < 5184000000.0) {
			tu = TimeUnit.day;
		}else if(5184000000.0 < timeDifference && timeDifference < 63072000000.0) {
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
		if(usedColors.size()!=colors.length) {
			for(int i=0;i<colors.length;i++) {
				if(!usedColors.values().contains(colors[i])) {
					nextColor = i;
				}
			}
		}
		String color = colors[nextColor];
		usedColors.put(sensorId,color);
		nextColor = (nextColor+1)%colors.length;
		return color;
	}
	
	public void setDatePickerOptions() {
		startingDate.setDateMax(new Date());
		endingDate.setDateMax(new Date());
		CloseHandler<MaterialDatePicker> ch = event -> customRange.setFocus(true);
		startingDate.addCloseHandler(ch);
		endingDate.addCloseHandler(ch);
		DatePickerLanguage lang = Languages.isGerman()? DatePickerLanguage.DE : DatePickerLanguage.EN;
		startingDate.setLanguage(lang);
		endingDate.setLanguage(lang);
	}
	
	public void resetDatasets() {
		datasetMap = new HashMap<>();
		Dataset[] emptyDatasetArray = new Dataset[0];
		chart.getData().setDatasets(emptyDatasetArray);
	}
	
	
	public void highlightDateRange() {
		customRange.getElement().removeClassName("active");
		past24Hours.getElement().removeClassName("active");
		pastMonth.getElement().removeClassName("active");
		pastWeek.getElement().removeClassName("active");
		pastYear.getElement().removeClassName("active");
		switch(dateRange) {
		case CUSTOM:
			customRange.getElement().addClassName("active");
			break;
		case PAST_24HOURS:
			past24Hours.getElement().addClassName("active");
			break;
		case PAST_MONTH:
			pastMonth.getElement().addClassName("active");
			break;
		case PAST_WEEK:
			pastWeek.getElement().addClassName("active");
			break;
		case PAST_YEAR:
			pastYear.getElement().addClassName("active");
			break;
		default:
			break;
		
		}
	}
	
	public void resetMinMax() {
		minTimestamp = null;
		maxTimestamp = null;
	}
	
	public void recalculateMinMax() {
		LinkedList<LineDataset> datasets = new LinkedList<>(datasetMap.values());
		minValue = ValueHandler.getMinOfDatasets(datasets);
		maxValue = ValueHandler.getMaxOfDatasets(datasets);
		yAxis.getTicks().setMin(Math.floor(minValue-(maxValue-minValue)*0.1));
		yAxis.getTicks().setMax(Math.ceil(maxValue+(maxValue-minValue)*0.1));
		chart.getOptions().getScales().setYAxes(yAxis);
	}
	
	public void resetColors() {
		usedColors.clear();
		nextColor = 0;
	}

	public ListManager getListManager() {
		return listManager;
	}
	
	public Date getStartingDate() {
		return startingDate.getDate();
	}
	
	public Date getEndingDate() {
		return endingDate.getDate();
	}

	@Override
	public boolean updateNeeded(List<Integer> ids) {
		for(Integer id : ids) {
			if(!sensorIds.contains(id)) {
				return true;
			}
		}
		for(Integer id : datasetMap.keySet()) {
			if(!ids.contains(id)) {
				return true;
			}
		}
		return false;
	}
}
