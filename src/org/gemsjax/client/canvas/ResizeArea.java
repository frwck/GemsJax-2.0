package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

public class ResizeArea {
	
	// TODO maybe a circle would be nicer
	
	
	private double x, y;
	private double width, height;
	private String backgroundColor;
	private String borderColor;
	private int borderWeight;
	
	

	public ResizeArea(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		backgroundColor = "white";
		borderColor = "black";
		borderWeight = 1;
	}
	
	
	public void draw(Context2d context)
	{
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		
		context.setFillStyle(backgroundColor);
		context.fillRect(x+borderWeight, y+borderWeight, width-2*borderWeight, height-2*borderWeight);
		
	}
	
	public int getBorderWeight() {
		return borderWeight;
	}


	public void setBorderWeight(int borderWeight) {
		this.borderWeight = borderWeight;
	}

	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	
	public String getBackgroundColor() {
		return backgroundColor;
	}


	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


	public String getBorderColor() {
		return borderColor;
	}


	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	public boolean hasCoordinate(double x, double y)
	{
		if (isBetween(this.x, this.x+width,x) && isBetween(this.y, this.y+height,y))
			return true;
		else
			return false;
	}

	
	

}
