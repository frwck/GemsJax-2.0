package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

public class ResizeArea {
	
	private double xRelativToParent, yRelativToParent;
	private double width, height;
	private String backgroundColor;
	private String borderColor;
	private int borderWeight;
	
	

	public ResizeArea(double xRelativeToParent, double yRelativeToParent, double width, double height) {
		this.xRelativToParent = xRelativeToParent;
		this.yRelativToParent = yRelativeToParent;
		
		this.width = width;
		this.height = height;
		backgroundColor = "white";
		borderColor = "black";
		borderWeight = 2;
	}
	
	
	public void draw(Context2d context)
	{
		context.setFillStyle(borderColor);
		context.fillRect(xRelativToParent, yRelativToParent, width, height);
		
		context.setFillStyle(backgroundColor);
		context.fillRect(xRelativToParent+borderWeight, yRelativToParent+borderWeight, width-borderWeight, height-borderWeight);
		
	}
	
	public int getBorderWeight() {
		return borderWeight;
	}


	public void setBorderWeight(int borderWeight) {
		this.borderWeight = borderWeight;
	}

	
	
	public double getXRelativToParent() {
		return xRelativToParent;
	}

	public void setXRelativToParent(double x) {
		this.xRelativToParent = x;
	}

	public double getYRelativToParent() {
		return yRelativToParent;
	}

	public void setYRelativToParent(double y) {
		this.yRelativToParent = y;
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

	
	

}
