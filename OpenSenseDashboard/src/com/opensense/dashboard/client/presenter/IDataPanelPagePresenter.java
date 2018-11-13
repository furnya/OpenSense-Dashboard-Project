package com.opensense.dashboard.client.presenter;

import java.util.List;
import java.util.Map;

import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.model.ParamType;

public  interface IDataPanelPagePresenter extends IPresenter {

	AppController getAppController();
	public abstract void initIfNeeded(Runnable runnable);
	public abstract void onPageReturn();
	public abstract void onPageLeave();
	public abstract void waitUntilViewInit(Runnable runnable);
	public abstract void handleParamters(Map<ParamType, String> parameters);
	public abstract void handleIds(List<Integer> ids);
}
