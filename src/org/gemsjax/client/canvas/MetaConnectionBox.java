package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This class is used to display a box with the {@link MetaConnection} name and attributes on the {@link MetaModelCanvas}.
 * The class {@link MetaConnectionDrawable} draws the connection lines and icons for the {@link MetaConnection} and calls the
 * {@link #draw(Context2d)} method of this class, since it's not wise to let this class also implement the {@link Drawable} interface.
 * @author Hannes Dorfmann
 *
 */
public  class MetaConnectionBox {

	/**
	 * The connection, which name will be displayed with this {@link MetaConnectionBox}
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

	
	private List<ResizeArea> resizeAreas;
	 

	/**
	 * The minimum width. Its not allowed to resize to a width less then this value
	 */
	private double minWidth = 30;
	
	/**
	 * The minimum height. Its not allowed to resize to a height less then this value
	 */
	private double minHeight = 30;
	
	
	/**
	 * The current x coordinate of this {@link MetaConnectionBox}.
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
	 * The current y coordinate of this {@link MetaConnectionBox}.
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
	 * The current width of this {@link MetaConnectionBox}.
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
	 * The current height of this {@link MetaConnectionBox}.
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
	
	
	private  MetaConnectionDrawable connectionDrawable;
	 
	
	public MetaConnectionBox(MetaConnection connection, MetaConnectionDrawable connectionDrawable)
	{
		this.connection = connection;
		
		this.x = connection.getConnectionBoxX();
		this.y = connection.getConnectionBoxY();
		this.width = connection.getConnectionBoxWidth();
		this.height = connection.getConnectionBoxHeight();
		
		this.connectionDrawable = connectionDrawable;
		
		resizeAreas = new LinkedList<ResizeArea>();
		
		resizeAreas.add(new ResizeArea(getX()+ getWidth()-6, getY()+ getHeight()-6, 6, 6));
		
	}
	
	
	
	
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
	private void drawOnSelected(Context2d context) {
		
		// Draw the ResizeAreas 
		for (ResizeArea ra : resizeAreas)
			ra.draw(context);
				
	}
	
	
	
	private void drawName(Context2d context)
	{
		String txt = getWidth() - connection.getNameLeftSpace() > (connection.getName().length()*connection.getNameFontCharWidth()) 
		? connection.getName() : connection.getName().subSequence(0, (int) ((getWidth()- connection.getNameLeftSpace())/connection.getNameFontCharWidth() - 3))+"...";

		context.setFillStyle(connection.getFontColor());
		context.setFont(connection.getNameFontSize()+"px "+connection.getFontFamily());
		
		context.setTextAlign("left");
		context.fillText(txt, getX()+connection.getNameLeftSpace(), getY()+connection.getNameTopSpace()+connection.getNameFontSize());

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

	
	/**
	 * Get the Drawable which draws the connection lines
	 * @return
	 */
	public MetaConnectionDrawable getMetaConnectionDrawable()
	{
		return connectionDrawable;
	}

	
	public boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	


	/**
	 * The current x coordinate of this {@link MetaConnectionBox}.
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
	public double getX() {
		return x;
	}

	/**
	 * The current y coordinate of this {@link MetaConnectionBox}.
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
	public double getY() {
		return y;
	}







	/**
	 * The current height of this {@link MetaConnectionBox}.
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

	public double getHeight() {
		return this.height;
	}



	/**
	 * The current width of this {@link MetaConnectionBox}.
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
	public double getWidth() {
		return this.width;
	}



	public ResizeArea isResizerAreaAt(double x, double y) {
		for (ResizeArea r :resizeAreas)
			if (r.hasCoordinate(x, y))
				return r;
		
		return null;
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





	/**
	 * @see #getX()
	 */
	
	public void setX(double x) {

		this.x = x;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getY()
	 */
	
	public void setY(double y) {
		this.y = y;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getHeight()
	 */

	public void setHeight(double height) {
		this.height = height;
		autoSetResizeAreaPosition();
	}



	/**
	 * @see #getWidth()
	 */

	public void setWidth(double width) {
		this.width = width;
		autoSetResizeAreaPosition();
	}

	
	public boolean hasCoordinate(double x, double y) {
		return (isBetween(getX(), getX() +getWidth(), x) && isBetween(getY(), getY() + getHeight(),y));
	}



}
