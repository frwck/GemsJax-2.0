package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MouseOverable;

public class MouseOverEvent {
	
	private double x;
	private double y;
	private MouseOverable source;
	
	public MouseOverEvent(MouseOverable source, double x, double y)
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

	public MouseOverable getSource() {
		return source;
	}

}
