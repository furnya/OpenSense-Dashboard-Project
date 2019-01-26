package com.opensense.dashboard.client.utils.tourutils;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.opensense.dashboard.client.AppController;
import com.opensense.dashboard.client.utils.Languages;

public class TourBuilder {

	private static final Logger LOGGER = Logger.getLogger(TourBuilder.class.getName());

	private static TourBuilder instance;

	private TourStepRenderer tsr;
	private LinkedList<TourStepData> tourData;
	private Runnable cleanRunnable;

	private int beginningSize;

	private TourBuilder(HandlerManager eventBus) {
		if(this.tsr == null) {
			this.tsr = TourStepRenderer.getInstance(eventBus);
		}
	}

	public static TourBuilder getInstance(HandlerManager eventBus) {
		if(instance == null) {
			instance = new TourBuilder(eventBus);
		}
		return instance;
	}

	/**
	 * After the tour finished preparing the runnable gets called and the {@link #tsr} tries to render the tour with the given TourData
	 * If the tour is finished, closed or something went wrong while rendering the tour will always be cleared and the {@link Tours#cleanUp()} will be called
	 * @param tour
	 * @param userStartedTour if true the checkBox to never show a tour again will be shown
	 */
	public void startTour(Tours tour, boolean userStartedTour) {
		tour.prepare(() -> {
			this.tourData = (LinkedList<TourStepData>) tour.getTourData();
			this.beginningSize = this.tourData.size();
			this.cleanRunnable = tour::cleanUp;
			this.tsr.setTourCheckBoxVisible(!userStartedTour);
			createTourStep();
		});
	}

	public void closeTour() {
		try {
			this.tsr.clearTourStep();
			if(this.cleanRunnable != null) {
				this.cleanRunnable.run();
			}
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

	/**
	 * If a exception will be thrown not all will caught in this catch case
	 */
	private void createTourStep() {
		try {
			this.tsr.renderTourStep(this.tourData.pop(), this.beginningSize - this.tourData.size(), this.beginningSize, () -> {
				if(!this.tourData.isEmpty()) {
					createTourStep();
				}else {
					this.cleanRunnable.run();
				}
			});
		} catch (Exception e) {
			this.tsr.clearTourStep();
			AppController.showError(Languages.tourError());
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}


}
