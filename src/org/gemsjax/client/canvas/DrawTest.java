package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * A little test class 
 * @author Hannes Dorfmann
 *
 */
public class DrawTest implements Drawable{

	
	private double x, y,z;
	private double width = 100, height = 200, minWidth = 30, minHeight = 30;
	private String color;
	private boolean canBeMoved;
	
	private List<ResizeArea> resizeAreas;
	
	
	public DrawTest(double x, double y,  String color) {
		 this.x = x;
		 this.y = y;
		 this.color = color;
		 canBeMoved = true;
		 resizeAreas = new LinkedList<ResizeArea>();
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

	@Override
	public boolean canBeMoved() {
		return canBeMoved;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean canBeResized() {
			return true;
	}

	@Override
	public void setMinWidth(double minWidth) {
		this.minWidth = minWidth;
	}

	@Override
	public double getMinWidth() {
		
		return minWidth;
	}

	@Override
	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	@Override
	public double getMinHeight() {
		return minHeight;
	}

}
