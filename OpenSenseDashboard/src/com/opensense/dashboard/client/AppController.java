package com.opensense.dashboard.client;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.opensense.dashboard.client.event.OpenDataPanelPageEvent;
import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.client.model.DataPanelPage;
import com.opensense.dashboard.client.model.ParamType;
import com.opensense.dashboard.client.presenter.DataPanelPresenter;
import com.opensense.dashboard.client.presenter.FooterPresenter;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.presenter.NavigationPanelPresenter;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.view.DataPanelViewImpl;
import com.opensense.dashboard.client.view.FooterViewImpl;
import com.opensense.dashboard.client.view.NavigationPanelViewImpl;
import com.opensense.dashboard.shared.Parameter;

public class AppController implements IPresenter, ValueChangeHandler<String> {
	
	private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());
	 
	/**
	 * Final reference to this object so the appController's functions can be used in event handlers
	 */
	@SuppressWarnings("unused")
	private final AppController instance = this;
	 
	 /**
	  * Handler manager mechanism for passing events and registering to be
	  * notified of some of these events.
	  */
	 private final HandlerManager eventBus;
	 
	 /**
	  * GUI Image Bundle
	  */
	 protected GUIImageBundle gui = GWT.create(GUIImageBundle.class);
	 
	 /**
	  *  Presenter
	  */
	 private DataPanelPresenter dataPanelPresenter = null;
	 private NavigationPanelPresenter navigationPanelPresenter = null;
	 private FooterPresenter footerPresenter = null;
	 
	 /**
	  * Views
	  */
	 private DataPanelViewImpl dataPanelView = null;
	 private NavigationPanelViewImpl navigationPanelView = null;
	 private FooterViewImpl footerView = null;
	 
	 /**
	 * Constructs the application controller (main presenter).
	 * 
	 * @param eventBus
	 */
	 public AppController(final HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindHandler();
		setLanguageFromCookies();
		go(RootPanel.get());
		handleStart();
	}
	
	private void handleStart() {
		if(History.getToken() != null && History.getToken().isEmpty()) {
			History.newItem(DataPanelPage.HOME.name(), true);
		}else {
			History.replaceItem(History.getToken(), true);
		}
	}

	private void bindHandler() {
		History.addValueChangeHandler(this);
		
		/**
		 * opens the page with the given parameters and fires the event
		 */
		eventBus.addHandler(OpenDataPanelPageEvent.TYPE, event -> {
			if(event.getParameters() != null) {
				History.newItem(event.getDataPanelPage().name() + getParamterAsString(event.getParameters()), event.isFireEvent());
			}else if(event.getIds() != null) {
				History.newItem(event.getDataPanelPage().name() + getIdAsString(event.getIds()), event.isFireEvent());
			}else {
				History.newItem(event.getDataPanelPage().name(), event.isFireEvent());
			}
		});
	}
	

	@Override
	public void go(HasWidgets container) {
		HasWidgets controlPanel = RootPanel.get("control-panel");
		controlPanel.clear();
		if (navigationPanelView == null) {
			navigationPanelView = new NavigationPanelViewImpl();
		}
		navigationPanelPresenter = new NavigationPanelPresenter(eventBus, this, navigationPanelView);
		navigationPanelPresenter.go(controlPanel);
		
		HasWidgets dataPanelContainer = RootPanel.get("data-panel");
		dataPanelContainer.clear();
		if (dataPanelView == null)
			dataPanelView = new DataPanelViewImpl();
		dataPanelPresenter = new DataPanelPresenter(eventBus, this, dataPanelView);
		dataPanelPresenter.go(dataPanelContainer);
		
		HasWidgets footerContainer = RootPanel.get("footer");
		footerContainer.clear();
		if (footerView == null)
			footerView = new FooterViewImpl();
		footerPresenter = new FooterPresenter(eventBus, this, footerView);
		footerPresenter.go(footerContainer);
	}

	/**
	 * gets called if the history get changed and the event gets fired
	 * tries to parse the eventValue and open the given page with given parameters
	 * @logs invalid page or parameters in console and opens the homePage on default 
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (dataPanelPresenter == null) {
			LOGGER.log(Level.WARNING, "NAVIGATION: The dataPanelPresenter is null.");
			return;
		}
		if(event.getValue() == null && !event.getValue().isEmpty()) {
			return;
		}
		String pageString = event.getValue();
		DataPanelPage page = null;
		Map<ParamType, String> parameters = null;
		List<Integer> ids = null;
		try {
			if(event.getValue().contains("?")) {
				page = DataPanelPage.valueOf(pageString.substring(0, pageString.indexOf('?')));
				parameters = getParameterAsMap(pageString.substring(pageString.indexOf('?'), pageString.length()));
			}else if(event.getValue().contains("/")) {
				page = DataPanelPage.valueOf(pageString.substring(0, pageString.indexOf('/')));
				ids = getIdsAsList(pageString.substring(pageString.indexOf('/'), pageString.length()));
			}else {
				page = DataPanelPage.valueOf(pageString);
			}
		}catch(Exception e) {
			page = DataPanelPage.HOME;
			LOGGER.log(Level.WARNING, "Invalid navigation page or invalid parameters.");
		}
		
		dataPanelPresenter.navigateTo(page, parameters, ids);
		navigationPanelPresenter.setActiveDataPanelPage(page);
	}
	
	/**
	 * 
	 * @param parameterAsString
	 * @returns the parameterString as Map<ParamType, String>
	 */
	private Map<ParamType, String> getParameterAsMap(String parameterAsString){
		parameterAsString = parameterAsString.replace("?", "");
		final Map<ParamType, String> validParameters = new EnumMap<>(ParamType.class);
		String[] params;
		if(parameterAsString.contains("&")) {
			params = parameterAsString.split("&");
		}else {
			params = new String[]{parameterAsString};
		}
		for (String parameter : params) {
			if(!parameter.isEmpty() && parameter.contains("=")) {
				String[] keyValue = parameter.split("=");
				if(keyValue != null && keyValue[0] != null && keyValue[1] != null) {
					boolean isValid = false;
					for (ParamType type : ParamType.values()) {
						if(type.getValue().equalsIgnoreCase(keyValue[0])) {
							isValid = true;
							validParameters.put(type, keyValue[1]);
							break;
						}
					}
					if(!isValid) {
						LOGGER.log(Level.WARNING, () -> "Parameter key is not valid: "+ keyValue[0]);
					}
				}
			}else {
				LOGGER.log(Level.WARNING, "Paramter is not valid, do not cotains \"=\"");
			}
		}
		return validParameters;
	}
	
	private List<Integer> getIdsAsList(String idsAsString){
		idsAsString = idsAsString.replace("/", "");
		final List<Integer> idsAsList = new ArrayList<>();
		String[] ids;
		if(idsAsString.contains(",")) {
			ids = idsAsString.split(",");
		}else {
			ids = new String[]{idsAsString};
		}
		for (String id : ids) {
			//only numbers will be accepted and parsed
			if(id.matches("^[0-9]*$")) {
				idsAsList.add(Integer.valueOf(id));
			}else{
				LOGGER.log(Level.WARNING, "The id contains not a number, will be removed");
			}
		}
		return idsAsList;
		
	}
	
	/**
	 * @param parameterList
	 * @returns the given parameterMap as String 
	 */
	public String getParamterAsString(List<Parameter> parameterList) {
		StringBuilder parameters = new StringBuilder();
		if(!parameterList.isEmpty()) {
			parameters.append('?');
		}
		parameterList.forEach(param -> {
			parameters.append(param.getKey());
			parameters.append('=');
			parameters.append(param.getValue());
			parameters.append('&');
		});
		parameters.deleteCharAt(parameters.length() - 1);
		return parameters.toString();
	}
	
	private String getIdAsString(List<Integer> idsList) {
		StringBuilder idsAsString = new StringBuilder();
		if(!idsList.isEmpty()) {
			idsAsString.append('/');
		}
		idsList.forEach(id -> {
			idsAsString.append(id);
			idsAsString.append(',');
		});
		idsAsString.deleteCharAt(idsAsString.length() - 1);
		return idsAsString.toString();
	}
	
	public void switchLanguage() {
		if(Languages.isGerman()) {
			Languages.setEnglish();
		}else {
			Languages.setGerman();
		}
		CookieManager.writeLanguageCookie(Languages.getActualLanguageString());
		Window.Location.reload();
	}
	
	private void setLanguageFromCookies() {
		if (CookieManager.getLanguage() != null && "en".equals(CookieManager.getLanguage())) {
			Languages.setEnglish();
		}else { 
			Languages.setGerman();
		}
		// Sets the correct serverLanguage.
		GeneralService.Util.getInstance().setServerLanguage(Languages.getActualLanguageString(), new DefaultAsyncCallback<Void>(result -> {}));
	}
}
