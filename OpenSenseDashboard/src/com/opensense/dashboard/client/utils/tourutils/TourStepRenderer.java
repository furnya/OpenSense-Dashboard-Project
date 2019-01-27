package com.opensense.dashboard.client.utils.tourutils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.Placement;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Span;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.event.CloseTourEvent;
import com.opensense.dashboard.client.utils.Languages;
import com.opensense.dashboard.client.utils.Rectangle;

import gwt.material.design.client.ui.MaterialButton;

public class TourStepRenderer extends Composite {

	@UiTemplate("TourStepRenderer.ui.xml")
	interface TourStepRendererUiBinder extends UiBinder<Widget, TourStepRenderer> { }

	private static TourStepRendererUiBinder uiBinder = GWT.create(TourStepRendererUiBinder.class);

	private static final Logger LOGGER = Logger.getLogger(TourStepRenderer.class.getName());

	@UiField
	Div content;

	@UiField
	Div overlayContainer;

	@UiField
	Div messageContainer;

	@UiField
	Image closeButton;

	@UiField
	Span messageSpan;

	@UiField
	MaterialButton nextButton;

	@UiField
	MaterialButton closeTour;

	@UiField
	CheckBox tourCheckBox;

	@UiField
	Span headerText;

	private static TourStepRenderer instance;
	private final HandlerManager eventBus;

	private static final String TOUR_OVERLAY = "tour-overlay";
	private static final String MIN_WIDTH = "min-width-420";
	private static final int TRIES_TO_RENDER = 150; // TRIES_TO_RENDER * 0.1s = the actual time in seconds till the ElementNotRenderedException gets thrown

	private HasWidgets tourContainer = RootPanel.get("tour-container");
	private Element element;
	private HandlerRegistration windowResizeHandler;
	private HandlerRegistration keyUpHandler;
	private HandlerRegistration messageContainerHandler;
	private JavaScriptObject javaSriptHandler;
	private Rectangle containerRectangle = new Rectangle(0,0,0,0);
	private Rectangle addingToRectangle;
	private Placement placement;
	private Runnable callbackRunnable;
	private TourEventType tourEventType;
	private Timer renderTimer;
	private int triesToRender = 0;

	private TourStepRenderer(HandlerManager eventBus) {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
	}

	public static TourStepRenderer getInstance(HandlerManager eventBus) {
		if(instance == null) {
			instance = new TourStepRenderer(eventBus);
		}
		return instance;
	}

	@UiHandler("closeTour")
	public void onCloseTourButtonClicked(ClickEvent e) {
		this.fireCloseEvent();
	}

	@UiHandler("closeButton")
	public void onCloseButtonClicked(ClickEvent e) {
		this.fireCloseEvent();
	}

	@UiHandler("nextButton")
	public void onNextButtonClicked(ClickEvent e) {
		if(e != null) {
			e.stopPropagation();
		}
		this.runCallbackRunnable();
	}

	/**
	 * Renders the tour step in the DOM Window, if the element is not existing throws {@link #ElementNotFoundException}
	 * Tries to render the cloned element on the real element about {@link #TRIES_TO_RENDER} * 0.1 seconds.
	 * If the element has after expiration still the values top = 0, left = 0, width = 0 and height = 0 throws {@link #ElementNotRenderedException}
	 * @param tourData
	 * @param tourStep index which will be shown
	 * @param tourSteps -> The number of tourSteps that should be shown
	 * @param runnable gets called after the cloned element event is fired and the tourStep closed
	 * @throws ElementNotFoundException
	 * @throws ElementNotRenderedException
	 */
	public void renderTourStep(final TourStepData tourData, final int tourStep, final int tourSteps, final Runnable runnable) throws Exception{
		this.element = DOM.getElementById(tourData.getId());
		if(this.element == null) {
			throw new Exception("The element with the id = \"" + tourData.getId() + "\" can't found in the DOM.");
		}
		this.callbackRunnable = runnable;
		this.tourEventType = tourData.getTourEventType();
		this.addingToRectangle = tourData.getAddingRectangleStates();
		this.content.getElement().getStyle().setDisplay(Display.NONE);
		this.messageContainer.getElement().getStyle().setDisplay(Display.NONE);
		this.nextButton.getElement().getStyle().setDisplay(Display.NONE);
		this.overlayContainer.clear();
		this.placement = tourData.getPlacement();

		this.bindHandler(tourStep == tourSteps);
		if(tourData.getMessage() != null) {
			this.headerText.setText(Languages.tourHeader(tourStep, tourSteps));
			this.messageSpan.setText(tourData.getMessage());
		}
		this.tourContainer.add(this.content);
		this.triesToRender = 0;
		this.renderTimer = new Timer() {@Override public void run() {
			try {
				TourStepRenderer.this.renderIfTheElementHasAPosition();
			} catch (Exception e) {
				TourStepRenderer.this.clearTourStep();
				this.cancel();
				AppController.showError(Languages.tourError());
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}};
		this.renderIfTheElementHasAPosition();
	}

	/**
	 * Binds the handler on the element or shows the nextButton as default
	 * @param isLastStep if true the hint and hint_try_out next button wont be shown
	 * TYPE.INPUT after this tourStep there should always be another one
	 */
	private void bindHandler(final boolean isLastStep) {

		this.windowResizeHandler = Window.addResizeHandler(event -> this.rerenderTourStep());

		this.keyUpHandler = RootPanel.get().addDomHandler(event -> {
			if(event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
				this.eventBus.fireEvent(new CloseTourEvent(this.tourCheckBox.getValue()));
			}
		}, KeyDownEvent.getType());

		switch (this.tourEventType) {
		case CLICK:
			this.javaSriptHandler = this.bindClickListener(this.element, false);
			break;
		case HINT_TRY_OUT:
			if(!isLastStep) {
				this.messageContainer.addStyleName(MIN_WIDTH);
				this.nextButton.setEnabled(true);
				this.nextButton.getElement().getStyle().clearDisplay();
			}
			break;
		case HINT:
			if(!isLastStep) {
				this.messageContainer.addStyleName(MIN_WIDTH);
				this.nextButton.getElement().getStyle().clearDisplay();
			}
			this.nextButton.setEnabled(true);
			if(this.messageContainerHandler == null) {
				this.messageContainerHandler = this.preventDefaultAndStopPropagation(this.messageContainer);
			}
			this.javaSriptHandler = this.bindClickListener(this.element, true);
			break;
		case INPUT:
			this.messageContainer.addStyleName(MIN_WIDTH);
			this.nextButton.getElement().getStyle().clearDisplay();
			this.nextButton.setEnabled(false);
			if(!this.element.toString().contains("Input")) {
				Element emptyNode = new Div().getElement();
				this.element.getChild(0).appendChild(emptyNode);
				this.javaSriptHandler = this.bindInputListener(this.element.getChild(0).getChild(0).getParentElement());
				this.element.getChild(0).getChild(0).getParentElement().focus();
				emptyNode.removeFromParent();
			}else {
				this.javaSriptHandler = this.bindInputListener(this.element);
				this.element.focus();
			}
			break;
		default:
			LOGGER.log(Level.WARNING, "The tourEventType can not be unset, use as defautlt Type.HINT");
			break;
		}
	}

	private HandlerRegistration preventDefaultAndStopPropagation(Div container) {
		return container.addDomHandler(event -> {
			event.preventDefault();
			event.stopPropagation();
		}, ClickEvent.getType());
	}

	/**
	 * Binds a clickHandler on the element
	 * @param elem
	 * @param close = true the Tour will be closed by clicking this element (For the TourEventType HINT)
	 * 		  close = false the nextTour step will be rendered by clicking the element
	 * @return the function as JavaScriptObject that will be removed in the clear method
	 */
	private native JavaScriptObject bindClickListener(Element elem, boolean close) /*-{
		var closeStep = close;
		var that = this;
		var func = function() {
			if(closeStep){
				that.@com.opensense.dashboard.client.utils.tourutils.TourStepRenderer::fireCloseEvent(*)();
			}else {
				that.@com.opensense.dashboard.client.utils.tourutils.TourStepRenderer::runCallbackRunnable(*)();
			}
		};
		elem.addEventListener('click', func);
		return func
	}-*/;

	/**
	 * Binds a keyUpHandler on the element. Set the nextButtonEnabled if the value is not empty and disables the nextButton if the value is empty
	 * @param elem
	 * @return the function as JavaScriptObject that will be removed in the clear method
	 */
	private native JavaScriptObject bindInputListener(Element elem) /*-{
		var that = this;
		var func = function(event) {
			event.stopPropagation();
			if(elem.value != ""){
				if(event.key == "Enter"){
					that.@com.opensense.dashboard.client.utils.tourutils.TourStepRenderer::onNextButtonClicked(*)(null);
				}else{
					that.@com.opensense.dashboard.client.utils.tourutils.TourStepRenderer::setNextButtonEnabled(*)(true);
				}
			}else {
				that.@com.opensense.dashboard.client.utils.tourutils.TourStepRenderer::setNextButtonEnabled(*)(false);
			}
		};
		$wnd.addEventListener("keyup", func, true);
		return func;
	}-*/;

	private native void removeListener(Element elem, String eventType, JavaScriptObject handle) /*-{
		var func = handle;
		if(eventType == "keyup"){
			$wnd.removeEventListener(eventType, func, true);
			elem.blur();
		}else{
			elem.removeEventListener(eventType, func);
		}
	}-*/;

	/**
	 * The method gets called from the javaScript listener and if the next button was clicked
	 */
	private void runCallbackRunnable() {
		this.clearTourStep();
		if(this.callbackRunnable != null) {
			this.callbackRunnable.run();
		}
	}

	/**
	 * The method gets called from the javaScript listener
	 * Checks if the TourCheckBox is shown, if true the neverShowToursAgain will be set to the value
	 */
	private void fireCloseEvent() {
		if(this.tourCheckBox.isVisible()) {
			this.eventBus.fireEvent(new CloseTourEvent(this.tourCheckBox.getValue()));
		}else {
			this.eventBus.fireEvent(new CloseTourEvent());
		}
	}

	/**
	 * Tries {@link #TRIES_TO_RENDER} * 0.1s to detect that the element has not anymore the default (0,0,0,0) position and size
	 * If the element or rather the {@link #containerRectangle} has a position in the DOM, {@link #renderAtPosition()} gets called
	 * @throws ElementNotRenderedException
	 */
	private void renderIfTheElementHasAPosition() throws Exception {
		String[] values = this.getPositionAndSize(this.element).split(";");
		Rectangle oldRectangle = this.containerRectangle;
		if((values != null) && (values.length == 4)) {
			this.containerRectangle = new Rectangle(Double.valueOf(values[0]), Double.valueOf(values[1]), Double.valueOf(values[2]), Double.valueOf(values[3]));
		}
		if(this.positionIsDefault() || !this.positionIsEqual(oldRectangle)) {
			this.triesToRender++;
			if(this.triesToRender < TRIES_TO_RENDER) {
				this.renderTimer.schedule(100);
			}else {
				this.clearTourStep();
				AppController.showError(Languages.tourError());
				throw new Exception("The element with the id = \"" + this.element.getId() + "\" is not rendered.");
			}
		}else {
			this.renderTimer.cancel();
			this.renderAtPosition();
		}
	}

	private void rerenderTourStep() {
		try {
			this.overlayContainer.clear();
			String[] values = this.getPositionAndSize(this.element).split(";");
			if((values != null) && (values.length == 4)) {
				this.containerRectangle = new Rectangle(Double.valueOf(values[0]), Double.valueOf(values[1]), Double.valueOf(values[2]), Double.valueOf(values[3]));
			}
		} catch (Exception e) {
			this.clearTourStep();
			AppController.showError(Languages.tourError());
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		this.renderAtPosition();
	}

	private native String getPositionAndSize(Element element) /*-{
		var rect = element.getBoundingClientRect();
		return rect.left + ";" + rect.top + ";" + rect.width + ";" + rect.height;
	}-*/;


	private boolean positionIsDefault() {
		return (this.containerRectangle.getHeight() == 0) && (this.containerRectangle.getWidth() == 0) && (this.containerRectangle.getLeft() == 0) && (this.containerRectangle.getTop() == 0);
	}

	private boolean positionIsEqual(Rectangle oldRectangle) {
		return (this.containerRectangle.getLeft() == oldRectangle.getLeft()) && (this.containerRectangle.getTop() == oldRectangle.getTop()) &&
				(this.containerRectangle.getWidth() == oldRectangle.getWidth()) && (this.containerRectangle.getHeight() == oldRectangle.getHeight());
	}

	/**
	 * This method renders the four container with the correct position into the {@link #overlayContainer}.
	 * The {@link #containerRectangle} represents the elements values which will be not covered
	 * A bottom and right shadow which are white blurred Divs will be rendered near the element
	 * {@link #preventDefaultAndStopPropagation()} is needed that the containers do not trigger actions in background
	 * Calls {@link #showTourStep()}
	 */
	private void renderAtPosition() {
		if(this.addingToRectangle != null) {
			this.containerRectangle.addValues(this.addingToRectangle);
		}

		int windowWidth = Window.getClientWidth();
		int windowHeight = Window.getClientHeight();

		Div shadowBottom = new Div();
		this.preventDefaultAndStopPropagation(shadowBottom);
		shadowBottom.addStyleName("tour-element-shadow");
		shadowBottom.getElement().getStyle().setLeft(this.containerRectangle.getLeft()  + 5, Unit.PX);
		shadowBottom.getElement().getStyle().setTop(this.containerRectangle.getTop() + this.containerRectangle.getHeight(), Unit.PX);
		shadowBottom.getElement().getStyle().setWidth(this.containerRectangle.getWidth(), Unit.PX);
		shadowBottom.getElement().getStyle().setHeight(5, Unit.PX);
		this.overlayContainer.add(shadowBottom);

		Div shadowRight = new Div();
		this.preventDefaultAndStopPropagation(shadowRight);
		shadowRight.addStyleName("tour-element-shadow");
		shadowRight.getElement().getStyle().setLeft(this.containerRectangle.getLeft() + this.containerRectangle.getWidth(), Unit.PX);
		shadowRight.getElement().getStyle().setTop(this.containerRectangle.getTop() + 5, Unit.PX);
		shadowRight.getElement().getStyle().setWidth(5, Unit.PX);
		shadowRight.getElement().getStyle().setHeight(this.containerRectangle.getHeight() - 3, Unit.PX);
		this.overlayContainer.add(shadowRight);

		Div upperCon = new Div();
		this.preventDefaultAndStopPropagation(upperCon);
		upperCon.addStyleName(TOUR_OVERLAY);
		upperCon.getElement().getStyle().setTop(0, Unit.PX);
		upperCon.getElement().getStyle().setWidth(100, Unit.PCT);
		upperCon.getElement().getStyle().setHeight(this.containerRectangle.getTop(), Unit.PX);
		this.overlayContainer.add(upperCon);

		Div lowerCon = new Div();
		this.preventDefaultAndStopPropagation(lowerCon);
		lowerCon.addStyleName(TOUR_OVERLAY);
		lowerCon.getElement().getStyle().setTop(this.containerRectangle.getTop() + this.containerRectangle.getHeight(), Unit.PX);
		lowerCon.getElement().getStyle().setWidth(100, Unit.PCT);
		lowerCon.getElement().getStyle().setHeight(windowHeight - (this.containerRectangle.getTop() + this.containerRectangle.getHeight()), Unit.PX);
		this.overlayContainer.add(lowerCon);

		Div leftCon = new Div();
		this.preventDefaultAndStopPropagation(leftCon);
		leftCon.addStyleName(TOUR_OVERLAY);
		leftCon.getElement().getStyle().setTop(this.containerRectangle.getTop(), Unit.PX);
		leftCon.getElement().getStyle().setLeft(0, Unit.PX);
		leftCon.getElement().getStyle().setWidth(this.containerRectangle.getLeft(), Unit.PX);
		leftCon.getElement().getStyle().setHeight(this.containerRectangle.getHeight(), Unit.PX);
		this.overlayContainer.add(leftCon);

		Div rightCon = new Div();
		this.preventDefaultAndStopPropagation(rightCon);
		rightCon.addStyleName(TOUR_OVERLAY);
		rightCon.getElement().getStyle().setTop(this.containerRectangle.getTop(), Unit.PX);
		rightCon.getElement().getStyle().setRight(0, Unit.PX);
		rightCon.getElement().getStyle().setWidth(windowWidth - (this.containerRectangle.getLeft() + this.containerRectangle.getWidth()), Unit.PX);
		rightCon.getElement().getStyle().setHeight(this.containerRectangle.getHeight(), Unit.PX);
		this.overlayContainer.add(rightCon);

		this.showTourStep();
	}


	/**
	 * Show the build tourStep and sets the position of the message container
	 */
	public void showTourStep() {
		this.content.getElement().getStyle().clearDisplay();
		if((this.messageContainer != null) && (this.placement != null)) {
			this.messageContainer.getElement().getStyle().clearDisplay();
			this.setMessagePlacement(this.placement);
		}
	}

	/**
	 * Sets the {@link #messageContainer} on the left, top, right or bottom dependent from the given element
	 * @param placement
	 */
	private void setMessagePlacement(Placement placement) {
		this.messageContainer.getElement().getStyle().setLeft(-1000, Unit.PX);
		this.messageContainer.getElement().getStyle().setTop(-1000, Unit.PX);
		switch(placement) {
		case RIGHT:
			this.messageContainer.getElement().getStyle().setLeft(this.containerRectangle.getLeft() + this.containerRectangle.getWidth() + 10, Unit.PX);
			this.messageContainer.getElement().getStyle().setTop((this.containerRectangle.getTop() + (this.containerRectangle.getHeight() / 2)) - (this.messageContainer.getElement().getOffsetHeight() / 2.0), Unit.PX);
			break;
		case LEFT:
			this.messageContainer.getElement().getStyle().setLeft(this.containerRectangle.getLeft() - this.messageContainer.getElement().getOffsetWidth() - 10, Unit.PX);
			this.messageContainer.getElement().getStyle().setTop((this.containerRectangle.getTop() + (this.containerRectangle.getHeight() / 2)) - (this.messageContainer.getElement().getOffsetHeight() / 2.0), Unit.PX);
			break;
		case BOTTOM:
			this.messageContainer.getElement().getStyle().setLeft((this.containerRectangle.getLeft() + (this.containerRectangle.getWidth() / 2)) - (this.messageContainer.getElement().getOffsetWidth() / 2.0), Unit.PX);
			this.messageContainer.getElement().getStyle().setTop(this.containerRectangle.getTop() + this.containerRectangle.getHeight() + 10, Unit.PX);
			break;
		case TOP:
			this.messageContainer.getElement().getStyle().setLeft((this.containerRectangle.getLeft() + (this.containerRectangle.getWidth() / 2)) - (this.messageContainer.getElement().getOffsetWidth() / 2.0), Unit.PX);
			this.messageContainer.getElement().getStyle().setTop(this.containerRectangle.getTop() - this.messageContainer.getElement().getOffsetHeight() - 10, Unit.PX);
			break;
		default:
			break;
		}
	}

	/**
	 * Clears all containers, removes all handler also the added javaSciptHandler
	 */
	public void clearTourStep() {
		if(this.keyUpHandler != null) {
			this.keyUpHandler.removeHandler();
		}
		if(this.windowResizeHandler != null) {
			this.windowResizeHandler.removeHandler();
		}
		if((this.element != null) && (this.javaSriptHandler != null)) {
			if(!this.element.toString().contains("Input")) {
				Element emptyNode = new Div().getElement();
				this.element.getChild(0).appendChild(emptyNode);
				this.removeListener(this.element.getChild(0).getChild(0).getParentElement(), TourEventType.CLICK.equals(this.tourEventType) ? "click" : "keyup", this.javaSriptHandler);
				this.element.getChild(0).getChild(0).getParentElement().blur();
				emptyNode.removeFromParent();
			}else {
				this.removeListener(this.element, TourEventType.CLICK.equals(this.tourEventType) ? "click" : "keyup", this.javaSriptHandler);
				this.element.blur();
			}

		}
		if(this.renderTimer != null) {
			this.renderTimer.cancel();
		}
		this.headerText.clear();
		this.messageSpan.clear();
		this.overlayContainer.clear();
		this.tourCheckBox.setValue(false);
		this.content.getElement().getStyle().setDisplay(Display.NONE);
		this.messageContainer.getElement().getStyle().setDisplay(Display.NONE);
		this.messageContainer.removeStyleName(MIN_WIDTH);
		this.nextButton.setEnabled(true);
		this.tourContainer.clear();
	}

	public void setTourCheckBoxVisible(boolean visible) {
		this.tourCheckBox.setVisible(visible);
	}

	/**
	 * The function gets called from the binded javaScript eventChange handler
	 * @param enabled
	 */
	public void setNextButtonEnabled(boolean enabled) {
		this.nextButton.setEnabled(enabled);
	}

}
