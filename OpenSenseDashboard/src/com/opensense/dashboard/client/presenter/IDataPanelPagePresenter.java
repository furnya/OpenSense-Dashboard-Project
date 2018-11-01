package com.opensense.dashboard.client.presenter;

import com.opensense.dashboard.client.AppController;

public  interface IDataPanelPagePresenter extends IPresenter {

	AppController getAppController();
	public abstract void initIfNeeded();
	public abstract void onPageReturn();
	public abstract void onPageLeave();
	public abstract void initView();
}
