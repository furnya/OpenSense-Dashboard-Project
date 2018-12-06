package com.opensense.dashboard.client.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.HasWidgets;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.presenter.IPresenter;
import com.opensense.dashboard.client.services.GeneralService;
import com.opensense.dashboard.client.utils.CookieManager;
import com.opensense.dashboard.client.utils.DefaultAsyncCallback;

public class ListManagerPresenter implements IPresenter, ListManagerView.Presenter{

	private final ListManagerView view = new ListManagerViewImpl(this);

	private AppController appController;

	int ids = 0;

	public ListManagerPresenter(AppController appController) {
		this.appController = appController;
	}


	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.view.asWidget());
	}

	public void updateLists() {
		this.view.addFavoriteSensors(CookieManager.getFavoriteList());
		if(!this.appController.isGuest()) {
			GeneralService.Util.getInstance().getUserLists(new DefaultAsyncCallback<Map<Integer, List<Integer>>>(result -> {

			}));
		}
	}

	public void createNewList() {
		this.view.addNewListItem(this.ids++);
	}

	public void deleteList(final int listId) {
		// TODO Auto-generated method stub
		this.view.removeListItem(listId);
	}
}
