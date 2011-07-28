package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;

public class MouseOverEvent {
	
	private double x;
	private double y;
	private Drawable source;
	
	public MouseOverEvent(Drawable source, double x, double y)
	{
		this.x = x;
		this.y = y;
		this.source = source;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Drawable getSource() {
		return source;
	}

}
