package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.Color;
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
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ValueHandler;
import com.opensense.dashboard.client.utils.VisSensorItemCard;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.MeasurandType;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Value;

import gwt.material.design.client.constants.DatePickerLanguage;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialToast;

public class VisualisationsViewImpl extends DataPanelPageView implements VisualisationsView {
	
	@UiTemplate("VisualisationsView.ui.xml")
	interface VisualisationsViewUiBinder extends UiBinder<Widget, VisualisationsViewImpl> {
	}
	
	@UiField
	Div noDataIndicator;
	
	@UiField
	Div container;
	
	@UiField
	MaterialPreLoader viewSpinner;
	
	@UiField
	Div chartContainer;
	
	@UiField
	MaterialButton addToListButton;
	
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
	Div sensorContainer;
	
	@UiField
	MaterialButton showOnMapButton;
	
	@UiField
	MaterialButton showInSearchButton;
	
	@UiField
	MaterialButton selectAllButton;
	
//	@UiField
//	MaterialTimePicker startingTime;
//	
//	@UiField
//	MaterialTimePicker endingTime;
	
	private static VisualisationsViewUiBinder uiBinder = GWT.create(VisualisationsViewUiBinder.class);

	protected Presenter presenter;
	
	private List<Sensor> sensors;
	private Map<Integer, VisSensorItemCard> sensorMap = new HashMap<>();
	private Map<Sensor, LineDataset> datasetMap = new HashMap<>();
	
	private static final DateRange DEFAULT_RANGE = DateRange.PAST_WEEK;
	private DateRange dateRange = DEFAULT_RANGE;
	
	private static final int MAX_POINTS = 100;
	
	private LineChart chart;
	
	private Date maxTimestamp = null;
	private Date minTimestamp = null;
	
	private Double minValue = Double.POSITIVE_INFINITY;
	private Double maxValue = Double.NEGATIVE_INFINITY;
	
	private List<Integer> unselectedSensors = new ArrayList<>();
	private List<Integer> selectedSensors = new ArrayList<>();
	
	private HashMap<Integer,Color> colors = new HashMap<>();
	
	public VisualisationsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView() {
		createChart();
		setDatePickerOptions();
	}
	
	@UiHandler("customRange")
	public void onCustomRangeButtonClicked(ClickEvent e) {
		if(startingDate.getDate()==null || endingDate.getDate()==null) return;
		resetChart();
		showAllLoadingIndicators();
		setDateRange(DateRange.CUSTOM);
		presenter.valueRequestForSensorList(selectedSensors, DateRange.CUSTOM, startingDate.getDate(), endingDate.getDate());
	}
	
	@UiHandler("pastYear")
	public void onPastYearButtonClicked(ClickEvent e) {
		resetChart();
		showAllLoadingIndicators();
		setDateRange(DateRange.PAST_YEAR);
		presenter.valueRequestForSensorList(selectedSensors, DateRange.PAST_YEAR, null, null);
	}
	
	@UiHandler("pastMonth")
	public void onPastMonthButtonClicked(ClickEvent e) {
		resetChart();
		showAllLoadingIndicators();
		setDateRange(DateRange.PAST_MONTH);
		presenter.valueRequestForSensorList(selectedSensors, DateRange.PAST_MONTH, null, null);
	}
	
	@UiHandler("pastWeek")
	public void onPastWeekButtonClicked(ClickEvent e) {
		resetChart();
		showAllLoadingIndicators();
		setDateRange(DateRange.PAST_WEEK);
		presenter.valueRequestForSensorList(selectedSensors, DateRange.PAST_WEEK, null, null);
	}
	
	@UiHandler("past24Hours")
	public void onPast24HoursButtonClicked(ClickEvent e) {
		resetChart();
		showAllLoadingIndicators();
		setDateRange(DateRange.PAST_24HOURS);
		presenter.valueRequestForSensorList(selectedSensors, DateRange.PAST_24HOURS, null, null);
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
		addSensor(sensor);
		if(sensor == null || values == null || values.isEmpty()) return;
		ValueHandler valueHandler = new ValueHandler(values);
		List<Value> filteredValues = valueHandler.getValues();
		
		Date earliest = valueHandler.getEarliest().getTimestamp();
		Date latest = valueHandler.getLatest().getTimestamp();
		Double lowest = valueHandler.getMin().getNumberValue();
		Double highest = valueHandler.getMax().getNumberValue();
		if(minTimestamp==null || minTimestamp.compareTo(earliest)>0) minTimestamp = earliest;
		if(maxTimestamp==null || maxTimestamp.compareTo(latest)<0) maxTimestamp = latest;
		if(minValue > lowest) minValue = lowest;
		if(maxValue < highest) maxValue = highest;
		
		LineDataset dataset = createCrunchedDataset(filteredValues);
		Sensor oldSensor = datasetsContainId(sensor.getSensorId());
		if(oldSensor != null) datasetMap.remove(oldSensor);
		datasetMap.put(sensor, dataset);
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
	public List<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * @param sensors the sensors to set
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	public void addSensor(Sensor sensor) {
		if(sensors==null) setSensors(new LinkedList<>());
		if(!sensors.contains(sensor)) {
			if(containsSensorWithId(sensor.getSensorId())) {
				sensors.removeIf(s -> s.getSensorId()==sensor.getSensorId());
			}
			sensors.add(sensor);
			setSensorCard(sensor);
		}
	}
	
	public boolean containsSensorWithId(Integer id) {
		for(Sensor s : sensors) {
			if(s!= null && s.getSensorId()==id) return true;
		}
		return false;
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
	}
	
	public boolean showChart() {
		if(sensors==null || sensors.isEmpty()) return false;
		showNoDataIndicator(false);
		chart.update();
		CartesianTimeAxis xAxis = new CartesianTimeAxis(chart, CartesianAxisType.x);
		xAxis.setDistribution(ScaleDistribution.linear);
		xAxis.setBounds(ScaleBounds.ticks);
		xAxis.getTime().setMin(minTimestamp);
		xAxis.getTime().setMax(maxTimestamp);
		xAxis.getTime().setUnit(calculateTimeUnit());
		xAxis.getTime().setStepSize(1);
		CartesianLinearAxis yAxis = new CartesianLinearAxis(chart, CartesianAxisType.y);
		yAxis.getTicks().setMin(Math.floor(minValue));
		yAxis.getTicks().setMax(Math.ceil(maxValue));
		chart.getOptions().getScales().setXAxes(xAxis);
		chart.getOptions().getScales().setYAxes(yAxis);
		chartContainer.clear();
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
		chart.update();
	}
	
	public void setLineDatasetStyle(LineDataset dataset, int sensorId) {
		Color color = getNewColor();
		colors.put(sensorId,color);
		dataset.setBorderColor(color);
		dataset.setPointBackgroundColor(color);
		dataset.setFill(Fill.nofill);
		dataset.setLabel(""+sensorId);
	}
	
	public void firstD3Chart() {
		Selection s = D3.select(Document.get().getBody());
		SVG svg = D3.svg();
		Axis x = svg.axis();
		s.call(x);
	}
	
	public void setSensorCard(Sensor sensor) {
		final VisSensorItemCard card = sensorMap.get(sensor.getSensorId());
		card.setIcon(getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
		card.setIconTitle(sensor.getMeasurand().getDisplayName());
		card.getMiddleHeader().clear();
		card.getMiddleHeader().add(new Span("Messgroesse: " + sensor.getMeasurand().getDisplayName()+","));
		card.getMiddleHeader().add(new Span("Genauigkeit: " + sensor.getAccuracy()+","));
		card.getMiddleHeader().add(new Span(sensor.getAttributionText()));
		card.hideLoadingIndicator();
	}
	
	public void removeSensorCard(Integer id) {
		sensorContainer.remove(sensorMap.get(id));
	}
	
	private String getIconUrlFromType(MeasurandType measurandType) {
		switch(measurandType) {
		case AIR_PRESSURE:
			return GUIImageBundle.INSTANCE.pressureIconSvg().getSafeUri().asString();
		case BRIGHTNESS:
			return GUIImageBundle.INSTANCE.sunnyIconSvg().getSafeUri().asString();
		case CLOUDINESS:
			return GUIImageBundle.INSTANCE.cloudsIconSvg().getSafeUri().asString();
		case HUMIDITY:
			return GUIImageBundle.INSTANCE.humidityIconSvg().getSafeUri().asString();
		case NOISE:
			return GUIImageBundle.INSTANCE.noiseIconSvg().getSafeUri().asString();
		case PM10:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PM2_5:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PRECIPITATION_AMOUNT:
			return GUIImageBundle.INSTANCE.precipitaionIconSvg().getSafeUri().asString();
		case PRECIPITATION_TYPE:
			return GUIImageBundle.INSTANCE.precipitationTypeIconSvg().getSafeUri().asString();
		case TEMPERATURE:
			return GUIImageBundle.INSTANCE.tempIconSvg().getSafeUri().asString();
		case WIND_DIRECTION:
			return GUIImageBundle.INSTANCE.windDirectionIconSvg().getSafeUri().asString();
		case WIND_SPEED:
			return GUIImageBundle.INSTANCE.windSpeedIconSvg().getSafeUri().asString();
		default:
			return GUIImageBundle.INSTANCE.questionIconSvg().getSafeUri().asString();
		}
	}
	
	@UiHandler("showOnMapButton")
	public void onShowOnMapButtonClicked(ClickEvent e) {
		if(!selectedSensors.isEmpty()) {
			presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, selectedSensors));
		}
	}
	
	@UiHandler("showInSearchButton")
	public void onShowInSearchButtonClicked(ClickEvent e) {
		if(!selectedSensors.isEmpty()) {
			presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, selectedSensors));
		}
	}
	
	@UiHandler("selectAllButton")
	public void onSelectAllButtonClicked(ClickEvent e) {
		if(Languages.selectAllSensors().equals(selectAllButton.getText())) {
			while(!unselectedSensors.isEmpty()) {
				sensorMap.get(unselectedSensors.get(0)).setActive(true);
				selectedSensors.add(unselectedSensors.get(0));
				unselectedSensors.remove(0);
			}
			selectAllButton.setText(Languages.deselectAllSensors());
			addAllDatasets();
		}else {
			while(!selectedSensors.isEmpty()) {
				sensorMap.get(selectedSensors.get(0)).setActive(false);
				unselectedSensors.add(selectedSensors.get(0));
				selectedSensors.remove(0);
			}
			selectAllButton.setText(Languages.selectAllSensors());
			removeAllDatasets();
		}
	}
	
	public void showNoDataIndicator(boolean show) {
		if(show) {
			noDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			noDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	public void removeSensorDatasetFromChart(Integer sensorId) {
		Sensor sensor = getSensorFromId(sensorId);
		LineDataset dataset = datasetMap.remove(sensor);
		colors.remove(sensorId);
		ArrayList<Dataset> datasets = new ArrayList<>();
		chart.getData().getDatasets().forEach(datasets::add);
		datasets.remove(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		chart.getData().setDatasets(newDatasets);
		chart.update();
	}
	
	public void addSensorDatasetToChart(Integer sensorId) {
		sensorMap.get(sensorId).showLoadingIndicator();
		presenter.buildValueRequestAndSend(sensorId, getDateRange(), minTimestamp, maxTimestamp);
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
	
	public Sensor datasetsContainId(Integer id) {
		for(Sensor s : datasetMap.keySet()) {
			if(s.getSensorId()==id) return s;
		}
		return null;
	}
	
	public void removeAllDatasets() {
		if(datasetMap==null || datasetMap.isEmpty()) return;
		for(Sensor sensor : sensors) {
			removeSensorDatasetFromChart(sensor.getSensorId());
		}
	}
	
	public void addAllDatasets() {
		if(sensors==null || sensors.isEmpty()) return;
		sensors.forEach(sensor -> addSensorDatasetToChart(sensor.getSensorId()));
	}
	
	public Sensor getSensorFromId(Integer id) {
		for(Sensor s : sensors) {
			if(s.getSensorId()==id) return s;
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
	
	public Color getNewColor() {
		if(colors.isEmpty()) return new Color(23,130,200);
		int r=0,g=0,b=0,rdmax=0,gdmax=0,bdmax=0;
		for(int i=0;i<256;i++) {
			int rdm=256,gdm=256,bdm=256;
			for(Color c : colors.values()) {
				if(Math.abs(i-c.getRed())<rdm) {rdm = Math.abs(i-c.getRed());}
				if(Math.abs(i-c.getGreen())<gdm) {gdm = Math.abs(i-c.getGreen());}
				if(Math.abs(i-c.getBlue())<bdm) {bdm = Math.abs(i-c.getBlue());}
			}
			if(rdm>rdmax) {r=i;rdmax=rdm;}
			if(gdm>gdmax) {g=i;gdmax=gdm;}
			if(bdm>bdmax) {b=i;bdmax=bdm;}
		}
		return new Color(r,g,b);
	}
	
	@Override
	public void addEmptySensorItemCard(Integer sensorId) {
		if(sensorMap.containsKey(sensorId)) return;
		final VisSensorItemCard card = new VisSensorItemCard();
		card.setHeader(Languages.sensorId() + sensorId);
		selectedSensors.add(sensorId);
		selectedSensors.sort((a,b) -> (a-b));
		sensorMap.put(sensorId, card);
		card.addValueChangeHandler(event -> {
			if(event.getValue()) {
				unselectedSensors.remove(sensorId);
				selectedSensors.add(sensorId);
				if(unselectedSensors.isEmpty()) selectAllButton.setText(Languages.deselectAllSensors());
				addSensorDatasetToChart(sensorId);
			}else {
				unselectedSensors.add(sensorId);
				selectedSensors.remove(sensorId);
				if(selectedSensors.isEmpty()) selectAllButton.setText(Languages.selectAllSensors());
				removeSensorDatasetFromChart(sensorId);
			}
		});
		card.showLoadingIndicator();
		sensorContainer.insert(card, selectedSensors.indexOf(sensorId));
	}
	
	public void showAllLoadingIndicators() {
		for(Integer id : selectedSensors) {
			sensorMap.get(id).showLoadingIndicator();
		}
	}
	
	public void setDatePickerOptions() {
		startingDate.setDateMax(new Date());
		endingDate.setDateMax(new Date());
		CloseHandler<MaterialDatePicker> ch = event -> selectAllButton.setFocus(true);
		startingDate.addCloseHandler(ch);
		endingDate.addCloseHandler(ch);
		DatePickerLanguage lang = Languages.isGerman()? DatePickerLanguage.DE : DatePickerLanguage.EN;
		startingDate.setLanguage(lang);
		endingDate.setLanguage(lang);
	}
}
