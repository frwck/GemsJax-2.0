package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.taskdefs.Move;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
/**
 * A little test class 
 * @author Hannes Dorfmann
 *
 */
public class DrawTest implements Drawable, ResizeHandler, MoveHandler, MouseOverHandler{

	
	private double x, y,z;
	private double width = 100, height = 200, minWidth = 30, minHeight = 30;
	private String color;
	private boolean canBeMoved;
	private boolean canBeResized;
	private boolean selected;
	private boolean mouseOver;
	
	private List<ResizeArea> resizeAreas;
	
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List<MouseOverHandler> mouseOverHandlers;
	
	
	public DrawTest(double x, double y,  String color) {
		 this.x = x;
		 this.y = y;
		 this.color = color;
		 canBeMoved = true;
		 selected = false;
		 mouseOver = false;
		 
		 resizeAreas = new LinkedList<ResizeArea>();
		 
		 moveHandlers = new LinkedList<MoveHandler>();
		 resizeHandlers = new LinkedList<ResizeHandler>();
		 mouseOverHandlers = new LinkedList<MouseOverHandler>();
		 
		 
		 resizeAreas.add(new ResizeArea(x+width-6, y+height-6, 6, 6));
		 
		 
		 this.addMouseOverHandler(this);
		 this.addMoveHandler(this);
		 this.addResizeHandler(this);
		 
		 
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
	public boolean isMoveable() {
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
	public boolean isResizeable() {
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
	public void onMove(MoveEvent e) {
		
		double oldX = getX();
		double oldY = getY();
		
		this.setX(getX()+e.getX() - e.getStartX());
		this.setY(getY()+e.getY() - e.getStartY());
		
		// Set the Position of the ResizeAreas
		for (ResizeArea ra : resizeAreas)
		{
			ra.setX(ra.getX() + (getX()-oldX));
			ra.setY(ra.getY() + (getY()-oldY));
		}
		
	}

	@Override
	public void addResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.add(resizeHandler);
	}

	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}

	@Override
	public void addMoveHandler(MoveHandler moveHandler) {
		moveHandlers.add(moveHandler);
	}

	@Override
	public void removeMoveHandler(MoveHandler moveHandler) {
		moveHandlers.remove(moveHandler);
	}

	@Override
	public List<MoveHandler> getMoveHandlers() {
		return moveHandlers;
	}

	@Override
	public List<ResizeHandler> getResizeHandlers() {
		return resizeHandlers;
	}

	@Override
	public boolean isMouseOver() {
		return mouseOver;
	}

	@Override
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	@Override
	public void addMouseOverHandler(MouseOverHandler mouseOverHandler) {
		mouseOverHandlers.add(mouseOverHandler);
	}

	@Override
	public void removeMouseOverHandler(MouseOverHandler mouseOverHandler) {
		mouseOverHandlers.remove(mouseOverHandler);
	}

	@Override
	public List<MouseOverHandler> getMouseOverHandlers() {
		return mouseOverHandlers;
	}

	@Override
	public void onMouseOver(double x, double y) {
		// TODO What to do when mouse is over
	}


}
