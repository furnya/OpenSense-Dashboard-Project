package com.opensense.dashboard.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;
import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.callbacks.TooltipLabelColor;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.CartesianAxisType;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.items.TooltipItem;
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
import com.opensense.dashboard.client.model.Size;
import com.opensense.dashboard.client.utils.ChartBounds;
import com.opensense.dashboard.client.utils.ColorManager;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.ListManager;
import com.opensense.dashboard.client.utils.ListManagerOptions;
import com.opensense.dashboard.shared.DateRange;

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

	private LineChart chart;

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
		this.highlightDateRange(presenter.getDateRange());
		this.listContainer.clear();
		ListManagerOptions listManagerOptions = ListManagerOptions.getInstance(this.presenter.getEventBus(), this.listContainer);
		listManagerOptions.setEditingActive(false);
		listManagerOptions.setPagerSize(Size.SMALL);
		listManagerOptions.setSpinnerSize(Size.SMALL);
		listManagerOptions.setShowMapButton(true);
		listManagerOptions.setShowVisualizationButton(false);
		listManagerOptions.setShowSearchButton(true);
		this.listManager = ListManager.getInstance(listManagerOptions);
		this.listManager.waitUntilViewInit(runnable);
		this.listManager.addSelectedSensorsChangeHandler(event -> {
			presenter.onSelectedSensorsChange(event.getSelectedIds());
		});
		presenter.setListManager(this.listManager);
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
		ColorManager.getInstance().resetColors();
		this.presenter.onDateRangeButtonClicked(dr, minT, maxT);
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
	public void createChart() {
		Defaults.getGlobal().getAnimation().setDuration(0);
		this.chart = new LineChart();
		this.chart.getOptions().setShowLines(true);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		try {
			Defaults.getPlugins().register(new ChartBackgroundColor());
		} catch (InvalidPluginIdException e) {
			GWT.log(e.toString());
		}
		this.xAxis = new CartesianTimeAxis(this.chart, CartesianAxisType.x);
		this.xAxis.setDistribution(ScaleDistribution.linear);
		this.xAxis.setBounds(ScaleBounds.ticks);
		this.xAxis.getTime().setStepSize(1);
		this.xAxis.getTime().setTooltipFormat("DD MMM YYYY, HH:mm");
		this.yAxis = new CartesianLinearAxis(this.chart, CartesianAxisType.y);
		this.chart.getOptions().getScales().setXAxes(this.xAxis);
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
	}

	@Override
	public boolean showChart(Map<Integer, LineDataset> datasetMap, ChartBounds chartBounds) {
		this.hideLoadingIndicator();
		this.chartContainer.clear();
		if((datasetMap==null) || datasetMap.isEmpty()) {
			this.showNoDatasetsIndicator(true);
			return false;
		}
		this.showNoDatasetsIndicator(false);
		this.setChartAxisX(chartBounds.getMinTimestamp(), chartBounds.getMaxTimestamp());
		this.setChartAxisY(chartBounds.getMinValue(), chartBounds.getMaxValue());
		this.chartContainer.add(this.chart);
		return true;
	}

	public void addDatasetToChart(Dataset dataset) {
		ArrayList<Dataset> datasets = new ArrayList<>();
		this.chart.getData().getDatasets().forEach(datasets::add);
		datasets.add(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		this.chart.getData().setDatasets(newDatasets);
	}

	public void showNoDatasetsIndicator(boolean show) {
		if(show) {
			this.noDatasetsLabel.getElement().getStyle().clearDisplay();
		}else {
			this.noDatasetsLabel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}


	public TimeUnit calculateTimeUnit(ChartBounds chartBounds) {
		TimeUnit tu = TimeUnit.hour;
		if((chartBounds.getMaxTimestamp()==null) || (chartBounds.getMinTimestamp()==null)) {
			return tu;
		}
		long timeDifference = chartBounds.getMaxTimestamp().getTime()-chartBounds.getMinTimestamp().getTime();
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
	public void highlightDateRange(DateRange dateRange) {
		String active = "active";
		this.customRange.getElement().removeClassName(active);
		this.past24Hours.getElement().removeClassName(active);
		this.pastMonth.getElement().removeClassName(active);
		this.pastWeek.getElement().removeClassName(active);
		this.pastYear.getElement().removeClassName(active);
		switch(dateRange) {
		case CUSTOM:
			this.customRange.getElement().addClassName(active);
			break;
		case PAST_24HOURS:
			this.past24Hours.getElement().addClassName(active);
			break;
		case PAST_MONTH:
			this.pastMonth.getElement().addClassName(active);
			break;
		case PAST_WEEK:
			this.pastWeek.getElement().addClassName(active);
			break;
		case PAST_YEAR:
			this.pastYear.getElement().addClassName(active);
			break;
		default:
			break;

		}
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
	public ListManager getListManager() {
		return this.listManager;
	}

	@Override
	public void removeDatasetFromChart(LineDataset dataset) {
		ArrayList<Dataset> datasets = new ArrayList<>();
		this.chart.getData().getDatasets().forEach(datasets::add);
		datasets.remove(dataset);
		Dataset[] newDatasets = new Dataset[datasets.size()];
		newDatasets = datasets.toArray(newDatasets);
		this.chart.getData().setDatasets(newDatasets);
	}
	
	@Override
	public void setChartDatasets(LineDataset[] datasets) {
		this.chart.getData().setDatasets(datasets);
	}

	@Override
	public void setChartAxisY(Double minValue, Double maxValue) {
		this.yAxis.getTicks().setMin(Math.floor(minValue-((maxValue-minValue)*0.1)));
		this.yAxis.getTicks().setMax(Math.ceil(maxValue+((maxValue-minValue)*0.1)));
		this.yAxis.getScaleLabel().setDisplay(true);
		this.yAxis.getScaleLabel().setLabelString("Temperature");
		this.chart.getOptions().getScales().setYAxes(this.yAxis);
	}
	
	@Override
	public void setChartAxisX(Date minTimestamp, Date maxTimestamp) {
		TimeUnit tu = this.calculateTimeUnit(new ChartBounds(0.0, 0.0, minTimestamp, maxTimestamp));
		this.xAxis.getTime().setMin(minTimestamp);
		this.xAxis.getTime().setMax(maxTimestamp);
		this.xAxis.getTime().setUnit(tu);
		this.xAxis.getTime().getDisplayFormats().setDisplayFormat(tu, this.getDisplayFormat(tu));
		this.chart.getOptions().getScales().setXAxes(this.xAxis);
	}

	@Override
	public LineChart getChart() {
		return this.chart;
	}
}