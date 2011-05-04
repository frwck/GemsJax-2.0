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
	private boolean selected;
	
	private List<ResizeArea> resizeAreas;
	
	
	public DrawTest(double x, double y,  String color) {
		 this.x = x;
		 this.y = y;
		 this.color = color;
		 canBeMoved = true;
		 resizeAreas = new LinkedList<ResizeArea>();
		 selected = false;
		 
		 
		 resizeAreas.add(new ResizeArea(x+width-6, y+height-6, 6, 6));
		 
		 
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
		
		draw(context);
		
		for (ResizeArea ra : resizeAreas)
			ra.draw(context);
		
		
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

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void onResize(double newWidth, double newHeight) {
		
		this.setWidth(newWidth);
		this.setHeight(newHeight);
		
	}

	@Override
	public void onMove(double newX, double newY) {
		this.setX(newX);
		this.setY(newY);
	}

}
