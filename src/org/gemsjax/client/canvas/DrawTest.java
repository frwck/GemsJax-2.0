package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * A little test class 
 * @author Hannes Dorfmann
 *
 */
public class DrawTest implements Drawable{

	
	private double x, y,z;
	private double width = 100, height = 200;
	private String color;
	
	public DrawTest(double x, double y,  String color) {
		 this.x = x;
		 this.y = y;
		 this.color = color;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public void setX(double x) {
		this.x = x;
		
	}

	@Override
	public void setY(double y) {
		this.y=y;
	}

	@Override
	public void setZ(double z) {
		this.z=z;
	}

	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean isCoordinateOfThis(double x, double y) {
		return (isBetween(this.x, this.x+width, x) && isBetween(this.y, this.y+height,y));
	}

	@Override
	public void draw(Context2d context) {
		
		
		context.setFillStyle(color);
		context.fillRect(x, y, width, height);
		
	}

	@Override
	public void drawOnMouseOver(Context2d context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawOnSelected(Context2d context) {
		// TODO Auto-generated method stub
		
	}

}
