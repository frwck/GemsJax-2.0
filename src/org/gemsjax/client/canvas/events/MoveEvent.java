package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.BufferedCanvas;

/**
 * This is an Event, that will be thrown, when a Drawable has been selected an will be moved (position) on the {@link BufferedCanvas}.
 * @author Hannes Dorfmann
 *
 */
public class MoveEvent {
	
	private double startX;
	private double startY;
	private double x;
	private double y;
	private int screenX;
	private int screenY;
	private boolean leftMouseDown;
	private double distanceToTopLeftX;
	private double distanceToTopLeftY;
	
	
	
	
	public MoveEvent(double startX, double startY, double x, double y,  double dTopLeftX, double dTopLeftY, int screenX, int screenY, boolean leftMouse)
	{
		this.startX = startX;
		this.startY = startY;
		this.x = x;
		this.y = y; 
		this.screenX =screenX;
		this.screenY = screenY;
		this.leftMouseDown = leftMouse;
		this.distanceToTopLeftX = dTopLeftX;
		this.distanceToTopLeftY = dTopLeftY;
		
		
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


	public boolean isLeftMouseDown() {
		return leftMouseDown;
	}


	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	
	public double getDistanceToTopLeftX() {
		return distanceToTopLeftX;
	}

	public double getDistanceToTopLeftY() {
		return distanceToTopLeftY;
	}
	

}
