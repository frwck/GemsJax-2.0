package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.sun.corba.se.pept.transport.Connection;

/**
 * This Class is used to display a box with the {@link MetaConnectionImpl} name and attributes on the {@link MetaModelCanvas}
 * @author Hannes Dorfmann
 *
 */
public  class MetaConnectionBoxDrawable implements Drawable, Moveable, Resizeable, Focusable {

	/**
	 * The connection, which name will be displayed with this {@link MetaConnectionBoxDrawable}
	 */
	private MetaConnection connection;
	
	/**
	 * The space between a dot and the next dot
	 */
	private double dotSpace = 3;
	/**
	 * The with of a dot. if with is more than one, than small lines will be painted instead of dotes
	 */
	private double dotWidth = 3;


	/**
	 * The space (in pixel) between the name text and the top border line
	 */
	private double nameTopSpace = 3;
	

	/**
	 * The space (in pixel) between the name text and the left border line
	 */
	private double nameLeftSpace = 3;
	

	/**
	 * The space (in pixel) between the name text and the right border line
	 */
	private double nameRightSpace = 3;
	
	/**
	 * The space (in pixel) between the name text and the bottom border line
	 */
	private double nameBottomSpace = 3;
	
	
	private List<ResizeArea> resizeAreas;
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List <FocusHandler> focusHandlers;
	 

	/**
	 * The minimum width. Its not allowed to resize to a width less then this value
	 */
	private double minWidth = 30;
	
	/**
	 * The minimum height. Its not allowed to resize to a height less then this value
	 */
	private double minHeight = 30;
	
	/**
	 * If the {@link MetaConnectionBoxDrawable} is resized and this flag is set to true,
	 * than the {@link MetaConnectionImpl#setSourceConnectionBoxRelativeX(double)}, {@link MetaConnectionImpl#setSourceConnectionBoxRelativeY(double)}, {@link MetaConnectionImpl#setTargetConnectionBoxRelativeX(double)}
	 * and {@link MetaConnectionImpl#setTargetConnectionBoxRelativeY(double)} will be set according to the previous (before the resizing) 
	 * percental ratio.
	 */
	private boolean autoAdjustNameBoxRatio = true;
	
	/**
	 * The current x coordinate of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxX()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this x value is set and changed permanently, but the original x value of the 
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this x value is the same as the original {@link MetaConnection#getConnectionBoxX()()} value, or vice versa.
	 * @see MoveEvent
	 * @see MoveEventType
	 * @see MoveEventType#MOVE_FINISHED
	 * @see MoveEventType#TEMP_MOVE
	 */
	private double x;
	/**
	 * The current y coordinate of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxY()},
	 * because this value is used to draw Move events / animations.
	 * So during an animation this y value is set and changed permanently, but the original y value of the {@link MetaConnection} object
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this y value is the same as the original {@link MetaConnection#getConnectionBoxY()} value and vice versa.
	 * @see MoveEvent
	 * @see MoveEventType
	 * @see MoveEventType#MOVE_FINISHED
	 * @see MoveEventType#TEMP_MOVE
	 */
	private double y;
	
	
	/**
	 * The current width of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxWidth()},
	 * because this value is used to draw resize events / animations.
	 * So during an animation this width value is set and changed permanently, but the original width value of the {@link MetaConnection} object
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this width value is the same as the original {@link MetaConnection#getConnectionBoxWidth()} value and vice versa.
	 * @see ResizeEvent
	 * @see ResizeEventType
	 * @see ResizeEventType#TEMP_RESIZE
	 * @see ResizeEventType#RESIZE_FINISHED
	 */
	private double width;
	
	/**
	 * The current height of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxHeight()},
	 * because this value is used to draw resize events / animations.
	 * So during an animation this height value is set and changed permanently, but the original width value of the {@link MetaConnection} object
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this width value is the same as the original {@link MetaConnection#getConnectionBoxHeight()} value and vice versa.
	 * @see ResizeEvent
	 * @see ResizeEventType
	 * @see ResizeEventType#TEMP_RESIZE
	 * @see ResizeEventType#RESIZE_FINISHED
	 */
	private double height;
	
	
	
	 
	
	public MetaConnectionBoxDrawable(MetaConnection connection)
	{
		this.connection = connection;
		
		this.x = connection.getConnectionBoxX();
		this.y = connection.getConnectionBoxY();
		this.width = connection.getConnectionBoxWidth();
		this.height = connection.getConnectionBoxHeight();
		
		resizeAreas = new LinkedList<ResizeArea>();
		moveHandlers = new LinkedList<MoveHandler>();
		resizeHandlers = new LinkedList<ResizeHandler>();
		focusHandlers = new LinkedList<FocusHandler>();
		
		resizeAreas.add(new ResizeArea(getX()+ getWidth()-6, getY()+ getHeight()-6, 6, 6));
		
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		CanvasGradient gradient = context.createLinearGradient(getX(), getY(),getX()+getWidth(), getY()+getHeight());
		
		gradient.addColorStop(0,connection.getGradientStartColor());
		gradient.addColorStop(0.7, connection.getGradientEndColor());
		
		drawDottedRect(context,getX(), getY(), getWidth(), getHeight());
		
		
		context.setFillStyle(gradient);
		context.fillRect(getX(), getY(), getWidth(), getHeight());
		
		drawName(context);
		
		if (connection.isSelected())
			drawOnSelected(context);
	}
	
	
	/**
	 * Implement how the Drawable should be drawn, when the Drawable has been selected (for example when this drawable has the focus)
	 * @param context
	 */
	public void drawOnSelected(Context2d context) {
		
		// Draw the ResizeAreas 
		
		for (ResizeArea ra : resizeAreas)
			ra.draw(context);
				
	}
	
	
	
	private void drawName(Context2d context)
	{
		String txt = getWidth()-nameLeftSpace > (connection.getName().length()*connection.getNameFontCharWidth()) 
		? connection.getName() : connection.getName().subSequence(0, (int) ((getWidth()- nameLeftSpace)/connection.getNameFontCharWidth() - 3))+"...";

		context.setFillStyle(connection.getFontColor());
		context.setFont(connection.getNameFontSize()+"px "+connection.getFontFamily());
		
		context.setTextAlign("left");
		context.fillText(txt, getX()+nameLeftSpace, getY()+nameTopSpace+connection.getNameFontSize());

	}
	
	
	
	private void drawDottedRect(Context2d context, double ox, double oy, double width, double height)
	{
		context.setFillStyle(connection.getLineColor());
		
		int dotsW = (int)(width/(dotWidth+dotSpace));
		int dotsH = (int)(height/(dotWidth+dotSpace));
		
		double  x = ox, y = oy;
		
		context.beginPath();
		
		// draw horizontal doted lines
		for (int i =0; i<dotsW; i++)
		{
			context.moveTo(x, y);
			context.lineTo(x+dotWidth, oy);
			context.moveTo(x,oy+height);
			context.lineTo(x+dotWidth, oy+height);
			
			x+=dotWidth+dotSpace;
		}
		
		x = ox;
		y = oy;
		
		for (int i =0; i<dotsH; i++)
		{
			context.moveTo(x, y);
			context.lineTo(ox, y+dotWidth);
			context.moveTo(x+width,y);
			context.lineTo(x+width, y+dotWidth);
			
			y+=dotWidth+dotSpace;
		}
		
		context.stroke();
		
	}

	@Override
	public Object getDataObject() {
		return connection;
	}

	@Override
	public double getZIndex() {
		return connection.getZIndex();
	}

	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		return (isBetween(getX(), getX() +getWidth(), x) && isBetween(getY(), getY() + getHeight(),y));
	}

	
	@Override
	public boolean isSelected()
	{
		return connection.isSelected();
	}

	@Override
	public void addMoveHandler(MoveHandler handler) {
		if (!moveHandlers.contains(handler))
			moveHandlers.add(handler);
	}




	@Override
	public boolean fireMoveEvent(MoveEvent event) {
		
		boolean delivered = false;
		
		for (MoveHandler h: moveHandlers)
		{
			h.onMove(event);
			delivered = true;
		}
		
		
		return delivered;
		
	}



	/**
	 * The current x coordinate of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxX()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this x value is set and changed permanently, but the original x value of the 
	 * {@link MetaClass#setX(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this x value is the same as the original {@link MetaConnection#getConnectionBoxX()()} value, or vice versa.
	 * @see MoveEvent
	 * @see MoveEventType
	 * @see MoveEventType#MOVE_FINISHED
	 * @see MoveEventType#TEMP_MOVE
	 * @return
	 */
	@Override
	public double getX() {
		return x;
	}

	/**
	 * The current y coordinate of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxY()},
	 * because this value is used to draw Move events / animations.
	 * So during an animation this y value is set and changed permanently, but the original y value of the 
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this y value is the same as the original {@link MetaConnection#getConnectionBoxY()()} value and vice versa.
	 * @see MoveEvent
	 * @see MoveEventType
	 * @see MoveEventType#MOVE_FINISHED
	 * @see MoveEventType#TEMP_MOVE
	 * @return
	 */
	@Override
	public double getY() {
		return y;
	}





	@Override
	public void removeMoveHandler(MoveHandler handler) {
		moveHandlers.remove(handler);
	}




	@Override
	public void addResizeHandler(ResizeHandler resizeHandler) {
		if (!resizeHandlers.contains(resizeHandler))
			resizeHandlers.add(resizeHandler);
	}




	@Override
	public boolean fireResizeEvent(ResizeEvent event) {
		boolean delivered = false;
		
		for (ResizeHandler h: resizeHandlers)
		{
			h.onResize(event);
			delivered = true;
		}
		
		return delivered;
	}



	/**
	 * The current height of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxHeight()},
	 * because this value is used to draw resize events / animations.
	 * So during an animation this height value is set and changed permanently, but the original width value of the {@link MetaConnection} object
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this width value is the same as the original {@link MetaConnection#getConnectionBoxHeight()} value and vice versa.
	 * @see ResizeEvent
	 * @see ResizeEventType
	 * @see ResizeEventType#TEMP_RESIZE
	 * @see ResizeEventType#RESIZE_FINISHED
	 * @return
	 */
	@Override
	public double getHeight() {
		return this.height;
	}



	/**
	 * The current width of this {@link MetaConnectionBoxDrawable}.
	 * This could be another value as the {@link MetaConnection#getConnectionBoxWidth()},
	 * because this value is used to draw resize events / animations.
	 * So during an animation this width value is set and changed permanently, but the original width value of the {@link MetaConnection} object
	 * is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this width value is the same as the original {@link MetaConnection#getConnectionBoxWidth()} value and vice versa.
	 * @see ResizeEvent
	 * @see ResizeEventType
	 * @see ResizeEventType#TEMP_RESIZE
	 * @see ResizeEventType#RESIZE_FINISHED
	 * @return
	 */
	@Override
	public double getWidth() {
		return this.width;
	}



	@Override
	public ResizeArea isResizerAreaAt(double x, double y) {
		for (ResizeArea r :resizeAreas)
			if (r.hasCoordinate(x, y))
				return r;
		
		return null;
	}




	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}

	
	public double getMinWidth()
	{
		return minWidth;
	}
	
	public double getMinHeight()
	{
		return minHeight;
	}

	
	
	/**
	 * Sets automatically the Position of the ResizeArea.
	 * This method should be called, whenever the width or height of this MetaClass drawable has been changed (except by a ResizeEvents)<br/>
	 * <b>Notice</b> {@link #autoSize()} will call this method automatically.
	 */
	private void autoSetResizeAreaPosition()
	{
		for (ResizeArea r : resizeAreas)
		{
			r.setX(getX()+getWidth()-r.getWidht());
			r.setY(getY()+getHeight()-r.getHeight());
		}
	}



	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			focusHandlers.add(handler);
	}




	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		
		boolean delivered = false;
		
		for (FocusHandler h: focusHandlers)
		{
			h.onFocusEvent(event);
			delivered = true;
		}
		
		return delivered;
	}




	@Override
	public void removeFocusHandler(FocusHandler handler) {
		focusHandlers.remove(handler);
	}


	/**
	 * @see #getX()
	 */
	@Override
	public void setX(double x) {

		this.x = x;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getY()
	 */
	@Override
	public void setY(double y) {
		this.y = y;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getHeight()
	 */
	@Override
	public void setHeight(double height) {
		this.height = height;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getWidth()
	 */
	@Override
	public void setWidth(double width) {
		this.width = width;
		autoSetResizeAreaPosition();
	}
	
	
	

}
