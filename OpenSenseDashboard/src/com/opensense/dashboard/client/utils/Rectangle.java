package com.opensense.dashboard.client.utils;

public class Rectangle {

	private double left;
	private double top;
	private double width;
	private double height;

	public Rectangle(double left, double top, double width, double height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return this.width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return this.height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getLeft() {
		return this.left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getTop() {
		return this.top;
	}
	public void setTop(double top) {
		this.top = top;
	}

	public void addValues(Rectangle addingToRectangle) {
		this.left += addingToRectangle.getLeft();
		this.top += addingToRectangle.getTop();
		this.width += addingToRectangle.getWidth();
		this.height += addingToRectangle.getHeight();
	}
}
