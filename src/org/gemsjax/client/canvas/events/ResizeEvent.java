package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.ResizeArea;

public class ResizeEvent {
	
	private double width;
	private double height;
	private double x;
	private double y;
	private ResizeArea resizeArea;
	
	
	public ResizeEvent(double width, double height, double x, double y, ResizeArea r)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.resizeArea = r;
	}


	public double getWidth() {
		return width;
	}


	public double getHeight() {
		return height;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public ResizeArea getResizeArea() {
		return resizeArea;
	}

}
