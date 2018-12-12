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
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.utils.BasicSensorItemCard;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ValueHandler;
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

	@UiField
	MaterialLabel noDatasetsLabel;

	//	@UiField
	//	MaterialTimePicker startingTime;
	//
	//	@UiField
	//	MaterialTimePicker endingTime;

	private static VisualisationsViewUiBinder uiBinder = GWT.create(VisualisationsViewUiBinder.class);

	protected Presenter presenter;

	private List<Sensor> sensors;
	private Map<Integer, BasicSensorItemCard> sensorCardMap = new HashMap<>();
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

	private String[] colors = {"#5899DA","#E8743B","#19A979","#ED4A7B","#945ECF","#13A4B4","#525DF4","#BF399E","#6C8893","#EE6868","#2F6497"};
	private int nextColor = 0;

	private CartesianTimeAxis xAxis;
	private CartesianLinearAxis yAxis;

	public VisualisationsViewImpl() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void initView() {
		this.createChart();
		this.setDatePickerOptions();
		this.highlightDateRange();
	}

	@UiHandler("customRange")
	public void onCustomRangeButtonClicked(ClickEvent e) {
		if((this.startingDate.getDate()==null) || (this.endingDate.getDate()==null)) {
			return;
		}
		this.resetChart();
		this.resetDatasets();
		this.showAllLoadingIndicators();
		this.setDateRange(DateRange.CUSTOM);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.selectedSensors, DateRange.CUSTOM, this.startingDate.getDate(), this.endingDate.getDate());
	}

	@UiHandler("pastYear")
	public void onPastYearButtonClicked(ClickEvent e) {
		this.resetChart();
		this.resetDatasets();
		this.showAllLoadingIndicators();
		this.setDateRange(DateRange.PAST_YEAR);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.selectedSensors, DateRange.PAST_YEAR, null, null);
	}

	@UiHandler("pastMonth")
	public void onPastMonthButtonClicked(ClickEvent e) {
		this.resetChart();
		this.resetDatasets();
		this.showAllLoadingIndicators();
		this.setDateRange(DateRange.PAST_MONTH);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.selectedSensors, DateRange.PAST_MONTH, null, null);
	}

	@UiHandler("pastWeek")
	public void onPastWeekButtonClicked(ClickEvent e) {
		this.resetChart();
		this.resetDatasets();
		this.showAllLoadingIndicators();
		this.setDateRange(DateRange.PAST_WEEK);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.selectedSensors, DateRange.PAST_WEEK, null, null);
	}

	@UiHandler("past24Hours")
	public void onPast24HoursButtonClicked(ClickEvent e) {
		this.resetChart();
		this.resetDatasets();
		this.showAllLoadingIndicators();
		this.setDateRange(DateRange.PAST_24HOURS);
		this.highlightDateRange();
		this.presenter.valueRequestForSensorList(this.selectedSensors, DateRange.PAST_24HOURS, null, null);
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
		this.addSensor(sensor);
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
		Sensor oldSensor = this.datasetsContainId(sensor.getSensorId());
		if(oldSensor != null) {
			this.datasetMap.remove(oldSensor);
		}
		this.datasetMap.put(sensor, dataset);
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
	public List<Sensor> getSensors() {
		return this.sensors;
	}

	/**
	 * @param sensors the sensors to set
	 */
	@Override
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public void addSensor(Sensor sensor) {
		if(this.sensors==null) {
			this.setSensors(new LinkedList<>());
		}
		if(!this.sensors.contains(sensor)) {
			if(this.containsSensorWithId(sensor.getSensorId())) {
				this.sensors.removeIf(s -> s.getSensorId()==sensor.getSensorId());
			}
			this.sensors.add(sensor);
			this.setSensorCard(sensor);
		}
	}

	public boolean containsSensorWithId(Integer id) {
		for(Sensor s : this.sensors) {
			if((s!= null) && (s.getSensorId()==id)) {
				return true;
			}
		}
		return false;
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
		if((this.sensors==null) || this.sensors.isEmpty() || (this.datasetMap==null) || this.datasetMap.isEmpty()) {
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
		this.yAxis.getTicks().setMin(Math.floor(this.minValue));
		this.yAxis.getTicks().setMax(Math.ceil(this.maxValue));
		this.chart.getOptions().getScales().setXAxes(this.xAxis);
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
		this.chartContainer.clear();
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
		this.chart.update();
	}

	public void setLineDatasetStyle(LineDataset dataset, int sensorId) {
		String color = this.getNewColor();
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
		final BasicSensorItemCard card = this.sensorCardMap.get(sensor.getSensorId());
		card.setIcon(this.getIconUrlFromType(sensor.getMeasurand().getMeasurandType()));
		card.setIconTitle(sensor.getMeasurand().getDisplayName());
		//		card.getMiddleHeader().clear();
		//		card.getMiddleHeader().add(new Span("Messgroesse: " + sensor.getMeasurand().getDisplayName()+","));
		//		card.getMiddleHeader().add(new Span("Genauigkeit: " + sensor.getAccuracy()+","));
		//		card.getMiddleHeader().add(new Span(sensor.getAttributionText()));
		card.hideLoadingIndicator();
	}

	public void removeSensorCard(Integer id) {
		this.sensorContainer.remove(this.sensorCardMap.get(id));
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
		if(!this.selectedSensors.isEmpty()) {
			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.MAP, true, this.selectedSensors));
		}
	}

	@UiHandler("showInSearchButton")
	public void onShowInSearchButtonClicked(ClickEvent e) {
		if(!this.selectedSensors.isEmpty()) {
			this.presenter.getEventBus().fireEvent(new OpenDataPanelPageEvent(DataPanelPage.SEARCH, true, this.selectedSensors));
		}
	}

	@UiHandler("selectAllButton")
	public void onSelectAllButtonClicked(ClickEvent e) {
		if(Languages.selectAllSensors().equals(this.selectAllButton.getText())) {
			while(!this.unselectedSensors.isEmpty()) {
				this.sensorCardMap.get(this.unselectedSensors.get(0)).setActive(true);
				this.selectedSensors.add(this.unselectedSensors.get(0));
				this.unselectedSensors.remove(0);
			}
			this.selectAllButton.setText(Languages.deselectAllSensors());
			this.addAllDatasets();
		}else {
			while(!this.selectedSensors.isEmpty()) {
				this.sensorCardMap.get(this.selectedSensors.get(0)).setActive(false);
				this.unselectedSensors.add(this.selectedSensors.get(0));
				this.selectedSensors.remove(0);
			}
			this.selectAllButton.setText(Languages.selectAllSensors());
			this.removeAllDatasets();
		}
	}

	@Override
	public void showNoDataIndicator(boolean show) {
		if(show) {
			this.noDataIndicator.getElement().getStyle().clearDisplay();
		}else {
			this.noDataIndicator.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void showNoDatasetsIndicator(boolean show) {
		if(show) {
			this.noDatasetsLabel.getElement().getStyle().clearDisplay();
		}else {
			this.noDatasetsLabel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void removeSensorDatasetFromChart(Integer sensorId) {
		Sensor sensor = this.getSensorFromId(sensorId);
		LineDataset dataset = this.datasetMap.remove(sensor);
		ArrayList<Dataset> datasets = new ArrayList<>();
		this.chart.getData().getDatasets().forEach(datasets::add);
		datasets.remove(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		this.chart.getData().setDatasets(newDatasets);
		this.chart.update();
	}

	public void addSensorDatasetToChart(Integer sensorId) {
		this.sensorCardMap.get(sensorId).showLoadingIndicator();
		this.presenter.buildValueRequestAndSend(sensorId, this.getDateRange(), this.minTimestamp, this.maxTimestamp);
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

	public Sensor datasetsContainId(Integer id) {
		for(Sensor s : this.datasetMap.keySet()) {
			if(s.getSensorId()==id) {
				return s;
			}
		}
		return null;
	}

	public void removeAllDatasets() {
		if((this.datasetMap==null) || this.datasetMap.isEmpty()) {
			return;
		}
		for(Sensor sensor : this.sensors) {
			this.removeSensorDatasetFromChart(sensor.getSensorId());
		}
	}

	public void addAllDatasets() {
		if((this.sensors==null) || this.sensors.isEmpty()) {
			return;
		}
		this.sensors.forEach(sensor -> this.addSensorDatasetToChart(sensor.getSensorId()));
	}

	public Sensor getSensorFromId(Integer id) {
		for(Sensor s : this.sensors) {
			if(s.getSensorId()==id) {
				return s;
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

	public String getNewColor() {
		String color = this.colors[this.nextColor];
		this.nextColor = (this.nextColor+1)%this.colors.length;
		return color;
	}

	@Override
	public void addEmptySensorItemCard(Integer sensorId) {
		if(this.sensorCardMap.containsKey(sensorId)) {
			return;
		}
		final BasicSensorItemCard card = new BasicSensorItemCard();
		card.setHeader(Languages.sensorId() + sensorId);
		this.selectedSensors.add(sensorId);
		this.selectedSensors.sort((a,b) -> (a-b));
		this.sensorCardMap.put(sensorId, card);
		card.addValueChangeHandler(event -> {
			if(event.getValue()) {
				this.unselectedSensors.remove(sensorId);
				this.selectedSensors.add(sensorId);
				if(this.unselectedSensors.isEmpty()) {
					this.selectAllButton.setText(Languages.deselectAllSensors());
				}
				this.addSensorDatasetToChart(sensorId);
			}else {
				this.unselectedSensors.add(sensorId);
				this.selectedSensors.remove(sensorId);
				if(this.selectedSensors.isEmpty()) {
					this.selectAllButton.setText(Languages.selectAllSensors());
				}
				this.removeSensorDatasetFromChart(sensorId);
			}
		});
		card.showLoadingIndicator();
		this.sensorContainer.insert(card, this.selectedSensors.indexOf(sensorId));
	}

	public void showAllLoadingIndicators() {
		for(Integer id : this.selectedSensors) {
			this.sensorCardMap.get(id).showLoadingIndicator();
		}
	}

	public void setDatePickerOptions() {
		this.startingDate.setDateMax(new Date());
		this.endingDate.setDateMax(new Date());
		CloseHandler<MaterialDatePicker> ch = event -> this.selectAllButton.setFocus(true);
		this.startingDate.addCloseHandler(ch);
		this.endingDate.addCloseHandler(ch);
		DatePickerLanguage lang = Languages.isGerman()? DatePickerLanguage.DE : DatePickerLanguage.EN;
		this.startingDate.setLanguage(lang);
		this.endingDate.setLanguage(lang);
		this.endingDate.addClickHandler(event -> this.endingDate.open());
	}

	@Override
	public void showSensorCardFailure(Integer sensorId) {
		BasicSensorItemCard card = this.sensorCardMap.get(sensorId);
		//		card.getMiddleHeader().add(new Span("Error"));
		card.hideLoadingIndicator();
	}

	public void resetDatasets() {
		this.datasetMap = new HashMap<>();
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
}
