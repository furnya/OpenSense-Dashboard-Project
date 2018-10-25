package com.opensense.dashboard.client.view;

import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.presenter.TestPresenter;

public class TestViewImpl extends Composite implements TestView {
	
	@UiTemplate("TestView.ui.xml")
	interface TestViewUiBinder extends UiBinder<Widget, TestViewImpl> {
	}
	
	@UiField
	Div test;

	private static TestViewUiBinder uiBinder = GWT.create(TestViewUiBinder.class);

	protected Presenter presenter;
	
	public TestViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		showThisTest();
	}

	@Override
	public void setPresenter(TestPresenter testPresenter) {
		this.presenter = testPresenter;
	}

	public void showThisTest() {
		test.add(new Span("addasd"));
	}
}
