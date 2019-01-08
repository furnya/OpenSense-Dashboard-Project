package com.opensense.dashboard.client.utils;

import java.util.HashMap;
import java.util.Map;

import com.opensense.dashboard.server.util.ClientRequestHandler;

public class ColorManager {

	private static ColorManager instance;
	
	private String[] colors = {"#5899DA","#E8743B","#19A979","#ED4A7B","#945ECF","#13A4B4","#525DF4","#BF399E","#6C8893","#EE6868","#2F6497"};
	private Map<Integer,String> usedColors = new HashMap<>();
	private int nextColor = 0;
	
	private ColorManager() {}
	
	public static ColorManager getInstance() {
		if(instance == null) {
			instance = new ColorManager();
		}
		return instance;
	}
	
	public void removeFromUsedColors(Integer id) {
		this.usedColors.remove(id);
	}
	
	public String getNewColor(int id) {
		if(this.usedColors.size()!=this.colors.length) {
			for(int i=0;i<this.colors.length;i++) {
				if(!this.usedColors.values().contains(this.colors[i])) {
					this.nextColor = i;
				}
			}
		}
		String color = this.colors[this.nextColor];
		this.usedColors.put(id,color);
		this.nextColor = (this.nextColor+1)%this.colors.length;
		return color;
	}
	
	public void resetColors() {
		this.usedColors.clear();
		this.nextColor = 0;
	}
}
