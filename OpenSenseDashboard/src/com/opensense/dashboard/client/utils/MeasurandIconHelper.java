package com.opensense.dashboard.client.utils;

import com.opensense.dashboard.client.gui.GUIImageBundle;
import com.opensense.dashboard.shared.MeasurandType;

/**
 * Returns the icon to the {@link MeasurandType}
 * @author carlr
 *
 */
public class MeasurandIconHelper {

	private MeasurandIconHelper() {
	}

	public static String getIconUrlFromType(MeasurandType measurandType) {
		switch(measurandType) {
		case AIR_PRESSURE:
			return GUIImageBundle.INSTANCE.pressureIconSvg().getSafeUri().asString();
		case BRIGHTNESS:
			return GUIImageBundle.INSTANCE.sunnyIconSvg().getSafeUri().asString();
		case CLOUDINESS:
			return GUIImageBundle.INSTANCE.cloudsIconSvg().getSafeUri().asString();
		case HUMIDITY:
			return GUIImageBundle.INSTANCE.humidityIconSvg().getSafeUri().asString();
		case NOISE:
			return GUIImageBundle.INSTANCE.noiseIconSvg().getSafeUri().asString();
		case PM10:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PM2_5:
			return GUIImageBundle.INSTANCE.particularsIconSvg().getSafeUri().asString();
		case PRECIPITATION_AMOUNT:
			return GUIImageBundle.INSTANCE.precipitaionIconSvg().getSafeUri().asString();
		case PRECIPITATION_TYPE:
			return GUIImageBundle.INSTANCE.precipitationTypeIconSvg().getSafeUri().asString();
		case TEMPERATURE:
			return GUIImageBundle.INSTANCE.tempIconSvg().getSafeUri().asString();
		case WIND_DIRECTION:
			return GUIImageBundle.INSTANCE.windDirectionIconSvg().getSafeUri().asString();
		case WIND_SPEED:
			return GUIImageBundle.INSTANCE.windSpeedIconSvg().getSafeUri().asString();
		default:
			return GUIImageBundle.INSTANCE.questionIconSvg().getSafeUri().asString();
		}
	}

	public static String getIconMarkerUrlFromType(MeasurandType measurandType) {
		switch(measurandType) {
		case AIR_PRESSURE:
			return GUIImageBundle.INSTANCE.pressureIconMarkerSvg().getSafeUri().asString();
		case BRIGHTNESS:
			return GUIImageBundle.INSTANCE.sunnyIconMarkerSvg().getSafeUri().asString();
		case CLOUDINESS:
			return GUIImageBundle.INSTANCE.cloudsIconMarkerSvg().getSafeUri().asString();
		case HUMIDITY:
			return GUIImageBundle.INSTANCE.humidityIconMarkerSvg().getSafeUri().asString();
		case NOISE:
			return GUIImageBundle.INSTANCE.noiseIconMarkerSvg().getSafeUri().asString();
		case PM10:
			return GUIImageBundle.INSTANCE.particularsIconMarkerSvg().getSafeUri().asString();
		case PM2_5:
			return GUIImageBundle.INSTANCE.particularsIconMarkerSvg().getSafeUri().asString();
		case PRECIPITATION_AMOUNT:
			return GUIImageBundle.INSTANCE.precipitaionIconMarkerSvg().getSafeUri().asString();
		case PRECIPITATION_TYPE:
			return GUIImageBundle.INSTANCE.precipitationTypeIconMarkerSvg().getSafeUri().asString();
		case TEMPERATURE:
			return GUIImageBundle.INSTANCE.tempIconMarkerSvg().getSafeUri().asString();
		case WIND_DIRECTION:
			return GUIImageBundle.INSTANCE.windDirectionIconMarkerSvg().getSafeUri().asString();
		case WIND_SPEED:
			return GUIImageBundle.INSTANCE.windSpeedIconMarkerSvg().getSafeUri().asString();
		default:
			return GUIImageBundle.INSTANCE.questionMarker().getSafeUri().asString();
		}
	}
}
