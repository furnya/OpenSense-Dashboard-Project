package com.opensense.dashboard.client.gui;

import org.vectomatic.dom.svg.ui.SVGResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface GUIImageBundle extends ClientBundle {

	public static final GUIImageBundle INSTANCE =  GWT.create(GUIImageBundle.class);

	//DataPanelPages
	@Source("icons/home.png")
	ImageResource homeIcon();

	@Source("icons/home.svg")
	SVGResource homeIconSvg();

	@Source("icons/search.png")
	ImageResource searchIcon();

	@Source("icons/search.svg")
	SVGResource searchIconSvg();

	@Source("icons/diagram.png")
	ImageResource diagramIcon();

	@Source("icons/diagram.svg")
	SVGResource diagramIconSvg();

	@Source("icons/list.png")
	ImageResource listIcon();

	@Source("icons/list.svg")
	SVGResource listIconSvg();

	@Source("icons/user.png")
	ImageResource userIcon();

	@Source("icons/user.svg")
	SVGResource userIconSvg();

	@Source("icons/user-active.svg")
	SVGResource userIconActive();

	@Source("icons/map.png")
	ImageResource mapIcon();

	@Source("icons/map.svg")
	SVGResource mapIconSvg();

	//Sensor type icons
	@Source("icons/temp.svg")
	SVGResource tempIconSvg();

	@Source("icons/wind-speed.svg")
	SVGResource windSpeedIconSvg();

	@Source("icons/wind-direction.svg")
	SVGResource windDirectionIconSvg();

	@Source("icons/sunny.svg")
	SVGResource sunnyIconSvg();

	@Source("icons/pressure.svg")
	SVGResource pressureIconSvg();

	@Source("icons/pracipitation.svg")
	SVGResource precipitaionIconSvg();

	@Source("icons/pracipitation-type.svg")
	SVGResource precipitationTypeIconSvg();

	@Source("icons/particulars.svg")
	SVGResource particularsIconSvg();

	@Source("icons/noise.svg")
	SVGResource noiseIconSvg();

	@Source("icons/humidity.svg")
	SVGResource humidityIconSvg();

	@Source("icons/clouds.svg")
	SVGResource cloudsIconSvg();

	@Source("icons/temp-marker.svg")
	SVGResource tempIconMarkerSvg();

	@Source("icons/wind-speed-marker.svg")
	SVGResource windSpeedIconMarkerSvg();

	@Source("icons/wind-direction-marker.svg")
	SVGResource windDirectionIconMarkerSvg();

	@Source("icons/sunny-marker.svg")
	SVGResource sunnyIconMarkerSvg();

	@Source("icons/pressure-marker.svg")
	SVGResource pressureIconMarkerSvg();

	@Source("icons/pracipitation-marker.svg")
	SVGResource precipitaionIconMarkerSvg();

	@Source("icons/pracipitation-type-marker.svg")
	SVGResource precipitationTypeIconMarkerSvg();

	@Source("icons/particulars-marker.svg")
	SVGResource particularsIconMarkerSvg();

	@Source("icons/noise-marker.svg")
	SVGResource noiseIconMarkerSvg();

	@Source("icons/humidity-marker.svg")
	SVGResource humidityIconMarkerSvg();

	@Source("icons/clouds-marker.svg")
	SVGResource cloudsIconMarkerSvg();

	@Source("icons/question.svg")
	SVGResource questionIconSvg();

	//Paging
	@Source("icons/paging/backwards.svg")
	SVGResource backwards();

	@Source("icons/paging/forwards.svg")
	SVGResource forwards();

	@Source("icons/paging/backwards_step_by_step.svg")
	SVGResource backwardsStepbyStep();

	@Source("icons/paging/forwards_step_by_step.svg")
	SVGResource forwardsStepByStep();

	@Source("icons/favorite.svg")
	SVGResource favorite();

	@Source("icons/favorite_red.svg")
	SVGResource favoriteRed();

	@Source("icons/recenter.svg")
	SVGResource recenter();

	@Source("icons/m1.png")
	ImageResource m1();

	@Source("icons/m2.png")
	ImageResource m2();

	@Source("icons/m3.png")
	ImageResource m3();

	@Source("icons/m4.png")
	ImageResource m4();

	@Source("icons/m5.png")
	ImageResource m5();

	@Source("icons/m1_plus.svg")
	SVGResource m1Plus();

	@Source("icons/plus_icon.svg")
	SVGResource plus();

	@Source("icons/trash.svg")
	SVGResource trash();

	@Source("icons/mysensor.svg")
	SVGResource mySesnors();

	@Source("icons/select-all.svg")
	SVGResource selectAll();

	@Source("icons/check.svg")
	SVGResource check();

	@Source("icons/information.svg")
	SVGResource info();

	@Source("icons/dashboard-icon.svg")
	SVGResource dashboardIcon();

	@Source("icons/english-flag.svg")
	SVGResource englishFlag();

	@Source("icons/german-flag.svg")
	SVGResource germanFlag();

	@Source("icons/spanish-flag.svg")
	SVGResource spanishFlag();

	@Source("icons/cross.svg")
	SVGResource cross();

	@Source("icons/question-marker.svg")
	SVGResource questionMarker();
}
