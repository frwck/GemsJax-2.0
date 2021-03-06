package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Clickable;
import org.gemsjax.client.canvas.handler.ClickHandler;

/**
 * To inform the {@link ClickHandler}s
 * @author Hannes Dorfmann
 *
 */
public class ClickEvent {
	
	public enum MouseButton{
		LEFT,
		MIDDLE,
		RIGHT
	}
	
	
	private Clickable clickedDrawable;
	private double x;
	private double y;
	private int screenX;
	private int screenY;
	private MouseButton mouseButton;
	
	
	public ClickEvent(Clickable clickedDrawable, double x, double y, int screenX, int screenY, MouseButton mouseButton)
	{
		this.clickedDrawable  = clickedDrawable;
		this.x = x;
		this.y = y;
		this.screenX = screenX;
		this.screenY = screenY;
		this.mouseButton = mouseButton;
	}


	public Clickable getSource() {
		return clickedDrawable;
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


	public MouseButton getMouseButton() {
		return mouseButton;
	}
	

}
