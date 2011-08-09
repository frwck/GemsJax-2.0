package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;

import com.google.gwt.canvas.dom.client.Context2d;

public class AnchorPoint {
	
	private double x;
	private double y;
	private double width = 6;
	private double height = 6;
	private String borderColor = "black";
	private String backgroundColor = "white";
	private double borderWeight = 1; 
	
	public AnchorPoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void draw(Context2d context) {
		
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		
		context.setFillStyle(backgroundColor);
		context.fillRect(x+borderWeight, y+borderWeight, width-2*borderWeight, height-2*borderWeight);
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



	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}


	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	

}
