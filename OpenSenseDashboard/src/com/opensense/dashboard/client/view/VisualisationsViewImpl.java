package com.opensense.dashboard.client.view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.gwtbootstrap3.client.ui.html.Div;
import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.ScatterChart;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.enums.CartesianAxisType;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.client.options.scales.CartesianTimeAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.utils.ValueHandler;
import com.opensense.dashboard.shared.DateRange;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.Sensor;
import com.opensense.dashboard.shared.Value;

import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.constants.DatePickerLanguage;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialToast;

public class VisualisationsViewImpl extends DataPanelPageView implements VisualisationsView {
	
	@UiTemplate("VisualisationsView.ui.xml")
	interface VisualisationsViewUiBinder extends UiBinder<Widget, VisualisationsViewImpl> {
	}
	
	@UiField
	Div container;
	
	@UiField
	MaterialPreLoader spinner;
	
	@UiField
	Div chartContainer;
	
	@UiField
	Div mdContainer;
	
	@UiField
	MaterialLink customRange;
	
	@UiField
	MaterialLink pastYear;
	
	@UiField
	MaterialLink pastMonth;
	
	@UiField
	MaterialLink pastWeek;
	
	@UiField
	MaterialLink past24Hours;
	
	@UiField
	MaterialDatePicker startingDate;
	
	@UiField
	MaterialDatePicker endingDate;
	
//	@UiField
//	MaterialTimePicker startingTime;
//	
//	@UiField
//	MaterialTimePicker endingTime;
	
	private static VisualisationsViewUiBinder uiBinder = GWT.create(VisualisationsViewUiBinder.class);

	protected Presenter presenter;
	
	private List<Sensor> sensors;
	
	public VisualisationsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView() {
//		showLoadingIndicator();
//		presenter.buildValueRequestAndSend();
	}
	
	@UiHandler("customRange")
	public void onCustomRangeButtonClicked(ClickEvent e) {
		presenter.buildValueRequestAndSend(DateRange.CUSTOM, startingDate.getDate(), endingDate.getDate());
	}
	
	@UiHandler("pastYear")
	public void onPastYearButtonClicked(ClickEvent e) {
		presenter.buildValueRequestAndSend(DateRange.PAST_YEAR, null, null);
	}
	
	@UiHandler("pastMonth")
	public void onPastMonthButtonClicked(ClickEvent e) {
		presenter.buildValueRequestAndSend(DateRange.PAST_MONTH, null, null);
	}
	
	@UiHandler("pastWeek")
	public void onPastWeekButtonClicked(ClickEvent e) {
		presenter.buildValueRequestAndSend(DateRange.PAST_WEEK, null, null);
	}
	
	@UiHandler("past24Hours")
	public void onPast24HoursButtonClicked(ClickEvent e) {
		presenter.buildValueRequestAndSend(DateRange.PAST_24HOURS, null, null);
	}
	
	@Override
	public void showLoadingIndicator() {
		spinner.getElement().getStyle().clearDisplay();
	}
	
	@Override
	public void hideLoadingIndicator() {
		spinner.getElement().getStyle().setDisplay(Display.NONE);
	}

	@Override
	public void showValuesInChart(List<Value> values) {
		if(values == null || values.isEmpty()) return;
		ValueHandler valueHandler = new ValueHandler(values);
		List<Value> filteredValues = valueHandler.getValues();
		LineChart chart = new LineChart();
		LineDataset dataset = chart.newDataset();
		DataPoint[] points = new DataPoint[filteredValues.size()];
		for(int i=0;i<filteredValues.size();i++) {
			Value value = filteredValues.get(i);
			DataPoint p = new DataPoint();
			p.setT(value.getTimestamp());
			p.setY(value.getNumberValue());
			points[i] = p;
		}
		dataset.setDataPoints(points);
		CartesianTimeAxis xAxis = new CartesianTimeAxis(chart, CartesianAxisType.x);
		xAxis.setDistribution(ScaleDistribution.linear);
		xAxis.setBounds(ScaleBounds.ticks);
		xAxis.getTime().setMin(filteredValues.get(0).getTimestamp());
		xAxis.getTime().setMax(filteredValues.get(filteredValues.size()-1).getTimestamp());
		CartesianLinearAxis yAxis = new CartesianLinearAxis(chart, CartesianAxisType.y);
		yAxis.getTicks().setMin(valueHandler.getMin().getNumberValue());
		yAxis.getTicks().setMax(valueHandler.getMax().getNumberValue());
		chart.getOptions().getScales().setXAxes(xAxis);
		chart.getOptions().getScales().setYAxes(yAxis);
		chart.getOptions().setShowLines(true);
		chart.getData().setDatasets(dataset);
		chartContainer.clear();
		hideLoadingIndicator();
		chartContainer.add(chart);
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
}
