package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.handler.MoveHandler;
import com.google.gwt.canvas.dom.client.Context2d;


/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class AnchorPoint implements Drawable{
	
	private double x;
	private double y;
	private double width = 6;
	private double height = 6;
	private String borderColor = "black";
	private String backgroundColor = "white";
	private double borderWeight = 1; 
	
	private AnchorPointDestination destination;
	
	private AnchorPoint nextAnchorPoint;
	
	public AnchorPoint(double x, double y, AnchorPointDestination destination)
	{
		this.x = x;
		this.y = y;
		this.setDestination(destination);
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

	/**
	 * Set the destination
	 * @param destination
	 */
	public void setDestination(AnchorPointDestination destination) {
		this.destination = destination;
	}

	public AnchorPointDestination getDestination() {
		return destination;
	}

	public AnchorPoint getNextAnchorPoint() {
		return nextAnchorPoint;
	}

	public void setNextAnchorPoint(AnchorPoint nextAnchorPoint) {
		this.nextAnchorPoint = nextAnchorPoint;
	}

	@Override
	public Object getDataObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getZIndex() {
		//used?
		return 0;
	}

	@Override
	public boolean isSelected() {
		// TODO used?
		return false;
	}
	
	

}
