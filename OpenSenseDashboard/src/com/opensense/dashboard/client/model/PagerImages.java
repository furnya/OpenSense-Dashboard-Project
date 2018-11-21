package com.opensense.dashboard.client.model;

import com.opensense.dashboard.client.gui.GUIImageBundle;

public enum PagerImages {
	FORWARDS_BUTTON_ACTIVE {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwardsBlue().getSafeUri().asString();}}, 
	FORWARDS_BUTTON_HOVER {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwardsHover().getSafeUri().asString();}}, 
	FORWARDSBUTTON_DISABLED {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwards().getSafeUri().asString();}},
	FORWARDSSTEPBYSTEP_BUTTON_ACTIVE {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwardsStepByStepBlue().getSafeUri().asString();}},  
	FORWARDSSTEPBYSTEP_BUTTON_HOVER {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwardsStepByStepHover().getSafeUri().asString();}},  
	FORWARDSSTEPBYSTEP_BUTTON_DISABLED {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.forwardsStepByStep().getSafeUri().asString();}}, 
	BACKWARDS_BUTTON_ACTIVE {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwardsBlue().getSafeUri().asString();}},  
	BACKWARDS_BUTTON_HOVER {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwardsHover().getSafeUri().asString();}},  
	BACKWARDS_BUTTON_DISABLED {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwards().getSafeUri().asString();}}, 
	BACKWARDSSTEPBYSTEP_BUTTON_ACTIVE {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwardsStepbyStepBlue().getSafeUri().asString();}},  
	BACKWARDSSTEPBYSTEP_BUTTON_HOVER {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwardsStepbyStepHover().getSafeUri().asString();}},  
	BACKWARDSSTEPBYSTEP_BUTTON_DISABLED {@Override public String getImageUrl() {return GUIImageBundle.INSTANCE.backwardsStepbyStep().getSafeUri().asString();}};
	
	public abstract String getImageUrl();
}
