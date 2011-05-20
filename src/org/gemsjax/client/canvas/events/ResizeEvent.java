package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
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
