package com.opensense.dashboard.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.view.SearchView;
import com.opensense.dashboard.shared.Parameter;
import com.opensense.dashboard.shared.RequestType;
import com.opensense.dashboard.shared.SearchRequest;

public class SearchPresenter extends DataPanelPagePresenter implements IPresenter, SearchView.Presenter{
	
	private final SearchView view;
	private static final int MAX_SENSOR_REQUEST = 20000;
	
	public SearchPresenter(HandlerManager eventBus, AppController appController, SearchView view) {
		super(view, eventBus, appController);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public SearchView getView() {
		return view;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void onPageReturn() {
		getSensorUnitsAndDispaly();
	}
	
	@Override
	public void onPageLeave() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void handleParamters(Map<String, String> parameters) {
		
	}

	@Override
	public void initView() {
		getSensorUnitsAndDispaly();
	}
	
	public void getSensorUnitsAndDispaly() {
//		GeneralService.Util.getInstance().getUnits(new SearchRequest(RequestType.UNIT), new DefaultAsyncCallback<Map<Integer, String> units>(result ->  {
//			view.setUnitList(result);
//		})); -> catch fall
		Map<Integer, String> units = new HashMap<>();
		units.put(1, "Celsius");
		units.put(2, "Decibel");
		units.put(3, "Percent");
		view.setUnitList(units);
	}

	@Override
	public void buildSensorRequestAndSend() {
		SearchRequest request = new SearchRequest(RequestType.SENSOR);
		List<Parameter> parameters = new ArrayList<>();
		if(view.getBounds() != null) {
			parameters.add(new Parameter("bounds", view.getBounds().toUrlValue(6)));
		}
		if(view.getMinAccuracy() != null && !view.getMinAccuracy().isEmpty()) {
			parameters.add(new Parameter("minAccuracy", view.getMinAccuracy()));
		}
		if(view.getMaxAccuracy() != null && !view.getMaxAccuracy().isEmpty()) {
			parameters.add(new Parameter("maxAccuracy", view.getMaxAccuracy()));
		}
		if(view.getUnits() != null && !view.getUnits().isEmpty()) {
			final StringBuilder units = new StringBuilder();
			view.getUnits().forEach(unit -> units.append(unit + ","));
			if(units.toString().contains(",") && units.charAt(units.length()-1) == ',') {
				units.deleteCharAt(units.length()-1);
			}
			parameters.add(new Parameter("units", units.toString()));
		}
		if(view.getMaxSensors() != null && !view.getMaxSensors().isEmpty()){
			parameters.add(new Parameter("maxSensor", view.getMaxSensors()));
		}else {
			parameters.add(new Parameter("maxSensor", MAX_SENSOR_REQUEST+""));
			view.setMaxSensors(MAX_SENSOR_REQUEST);
		}
		request.setParameters(parameters);
		parameters.forEach(param -> GWT.log(param.getKey() + " " + param.getValue()));
//		GeneralService.Util.getInstance().getSensorData(request, new DefaultAsyncCallback<List<Sensor>>(result -> view.showSensorData(s), 
//				caught -> view.showLoadSensorError(), false));
	}
}
