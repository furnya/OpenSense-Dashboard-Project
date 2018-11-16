package com.opensense.dashboard.client.view;

import java.util.Date;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.shared.Value;

import gwt.material.design.client.ui.MaterialPreLoader;

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
	
	private static VisualisationsViewUiBinder uiBinder = GWT.create(VisualisationsViewUiBinder.class);

	protected Presenter presenter;
	
	public VisualisationsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void initView() {
		showLoadingIndicator();
		presenter.buildValueRequestAndSend();
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
		LineChart chart = new LineChart();
		LineDataset dataset = chart.newDataset();
		DataPoint[] points = new DataPoint[values.size()];
		for(int i=0;i<values.size();i++) {
			Value value = values.get(i);
			DataPoint p = new DataPoint();
			p.setT(value.getTimestamp());
			p.setY(value.getNumberValue());
			points[i] = p;
		}
		dataset.setDataPoints(points);
		CartesianTimeAxis xAxis = new CartesianTimeAxis(chart, CartesianAxisType.x);
		xAxis.setDistribution(ScaleDistribution.linear);
		xAxis.setBounds(ScaleBounds.ticks);
		xAxis.getTime().setMin(values.get(0).getTimestamp());
		xAxis.getTime().setMax(values.get(values.size()-1).getTimestamp());
		CartesianLinearAxis yAxis = new CartesianLinearAxis(chart, CartesianAxisType.y);
		yAxis.getTicks().setMin(0);
		yAxis.getTicks().setMax(10);
		chart.getOptions().getScales().setXAxes(xAxis);
		chart.getOptions().getScales().setYAxes(yAxis);
		chart.getOptions().setShowLines(true);
		chart.getData().setDatasets(dataset);
		chartContainer.clear();
		hideLoadingIndicator();
		chartContainer.add(chart);
	}
}
