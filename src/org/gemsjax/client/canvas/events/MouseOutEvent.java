package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.MouseOutable;

/**
 * The mouse out event is sent to an element when the mouse pointer leaves the object
 * @author Hannes Dorfmann
 *
 */
public class MouseOutEvent {
	
	private double x;
	private double y;
	
	private int screenX;
	private int screenY;
	
	private MouseOutable source;

	
	public MouseOutEvent(MouseOutable source, double x, double y, int screenX, int screenY)
	{
		this.x = x;
		this.y = y;
		this.screenX = screenX;
		this.screenY = screenY;
		this.source = source;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public int getScreenX() {
		return screenX;
	}


	public int getScreenY() {
		return screenY;
	}


	public MouseOutable getSource() {
		return source;
	}
	
}
