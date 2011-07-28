package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.Resizeable;
import org.gemsjax.client.canvas.handler.ResizeHandler;

/**
 * Inform the {@link ResizeHandler} that the width and height of a {@link Drawable} should be resized to the given width and height
 * @author Hannes Dorfmann
 *
 */
public class ResizeEvent {
	
	private double width;
	private double height;
	private double x;
	private double y;
	private ResizeArea resizeArea;
	private Resizeable source;
	
	private double xDifference;
	private double yDifference;
	
	
	private double startX;
	private double startY;
	private double endY;
	private double endX;
	
	public ResizeEvent(Resizeable source, double xDifference, double yDifference)
	{
		this.source = source;
		this.xDifference = xDifference;
		this.yDifference = yDifference;
	}
	
	public ResizeEvent(Resizeable source, double width, double height, double x, double y, ResizeArea r)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.resizeArea = r;
		this.source = source;
	}
	
	public ResizeEvent(Resizeable source, double startX, double startY, double endX, double endY)
	{
		this.source = source;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		//this.resizeArea = r;
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
	
	public Resizeable getSource()
	{
		return source;
	}

	public double getEndY() {
		return endY;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getEndX() {
		return endX;
	}

}
