package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This Class is used to display a box with the {@link MetaConnectionImpl} name and attributes on the {@link MetaModelCanvas}
 * @author Hannes Dorfmann
 *
 */
public  class MetaConnectionBoxDrawable implements Drawable, Moveable, Resizeable, Focusable, ResizeHandler, MoveHandler, FocusHandler{

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
	 
	
	public MetaConnectionBoxDrawable(MetaConnection connection)
	{
		this.connection = connection;
		
		resizeAreas = new LinkedList<ResizeArea>();
		moveHandlers = new LinkedList<MoveHandler>();
		resizeHandlers = new LinkedList<ResizeHandler>();
		focusHandlers = new LinkedList<FocusHandler>();
		
		resizeAreas.add(new ResizeArea(connection.getConnectionBoxX()+ connection.getConnectionBoxWidth()-6, connection.getConnectionBoxY()+ connection.getConnectionBoxHeight()-6, 6, 6));

		this.addMoveHandler(this);
		this.addResizeHandler(this);
		this.addFocusHandler(this);
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		CanvasGradient gradient = context.createLinearGradient(connection.getConnectionBoxX(), connection.getConnectionBoxY(),connection.getConnectionBoxX()+connection.getConnectionBoxWidth(), connection.getConnectionBoxY()+connection.getConnectionBoxHeight());
		
		gradient.addColorStop(0,connection.getGradientStartColor());
		gradient.addColorStop(0.7, connection.getGradientEndColor());
		
		drawDottedRect(context,connection.getConnectionBoxX(), connection.getConnectionBoxY(), connection.getConnectionBoxWidth(), connection.getConnectionBoxHeight());
		
		
		context.setFillStyle(gradient);
		context.fillRect(connection.getConnectionBoxX(), connection.getConnectionBoxY(), connection.getConnectionBoxWidth(), connection.getConnectionBoxHeight());
		
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
		String txt = connection.getConnectionBoxWidth()-nameLeftSpace > (connection.getName().length()*connection.getNameFontCharWidth()) 
		? connection.getName() : connection.getName().subSequence(0, (int) ((connection.getConnectionBoxWidth()- nameLeftSpace)/connection.getNameFontCharWidth() - 3))+"...";

		context.setFillStyle(connection.getFontColor());
		context.setFont(connection.getNameFontSize()+"px "+connection.getFontFamily());
		
		context.setTextAlign("left");
		context.fillText(txt, connection.getConnectionBoxX()+nameLeftSpace, connection.getConnectionBoxY()+nameTopSpace+connection.getNameFontSize());

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
		return (isBetween(connection.getConnectionBoxX(), connection.getConnectionBoxX() + connection.getConnectionBoxWidth(), x) && isBetween(connection.getConnectionBoxY(), connection.getConnectionBoxY() + connection.getConnectionBoxHeight(),y));
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


	@Override
	public double getX() {
		return connection.getConnectionBoxX();
	}


	@Override
	public double getY() {
		return connection.getConnectionBoxY();
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




	@Override
	public double getHeight() {
		return connection.getConnectionBoxHeight();
	}




	@Override
	public double getWidth() {
		return connection.getConnectionBoxWidth();
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



	@Override
	public void onResize(ResizeEvent event) {
		
		if (event.getWidth()>getMinWidth() && event.getHeight()>getMinHeight())
		{
			
			for (ResizeArea r : resizeAreas)
			{
				r.setX(r.getX()+ (event.getWidth()-this.getWidth()));
				r.setY(r.getY()+ (event.getHeight()-this.getHeight()));
			}
			
			connection.setConnectionBoxWidth(event.getWidth());
			connection.setConnectionBoxHeight(event.getHeight());
			

			// Adjust connections coordinate
			if (connection.getSourceConnectionBoxRelativeX()>event.getWidth())
				connection.setSourceConnectionBoxRelativeX(event.getWidth());
			
			if (connection.getSourceConnectionBoxRelativeY()>event.getHeight())
				connection.setSourceConnectionBoxRelativeY(event.getHeight());
			
			if (connection.getTargetConnectionBoxRelativeX()>event.getWidth())
				connection.setTargetConnectionBoxRelativeX(event.getWidth());
			
			if (connection.getTargetConnectionBoxRelativeY()>event.getHeight())
				connection.setTargetConnectionBoxRelativeY(event.getHeight());
		
			/* TODO needed?
			if (autoAdjustNameBoxRatio)
			{
				if (connection.getANameBoxRelativeX()>event.getWidth())
					connection.setANameBoxRelativeX(event.getWidth());
				
				if (connection.getANameBoxRelativeY()>event.getHeight())
					connection.setANameBoxRelativeY(event.getHeight());
				
				if (connection.getBNameBoxRelativeX()>event.getWidth())
					connection.setBNameBoxRelativeX(event.getWidth());
				
				if (connection.getBNameBoxRelativeY()>event.getHeight())
					connection.setBNameBoxRelativeY(event.getHeight());
			}
		*/
		}
		
	}




	@Override
	public void onMove(MoveEvent e) {
		double oldX = getX();
		double oldY = getY();
		
		setX(e.getX()-e.getDistanceToTopLeftX());
		setY(e.getY()-e.getDistanceToTopLeftY());
		
		// Set the Position of the ResizeAreas
		for (ResizeArea ra : resizeAreas)
		{
			ra.setX(ra.getX() + (getX()-oldX));
			ra.setY(ra.getY() + (getY()-oldY));
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




	@Override
	public void onFocusEvent(FocusEvent event) {
		if (event.getType()==FocusEventType.GOT_FOCUS)
			connection.setSelected(true);
		else
			connection.setSelected(false);
	}




	@Override
	public void setX(double x) {

		connection.setConnectionBoxX(x);
	}




	@Override
	public void setY(double y) {
		connection.setConnectionBoxY(y);
	}




	@Override
	public void setHeight(double height) {
		connection.setConnectionBoxHeight(height);
	}




	@Override
	public void setWidth(double width) {
		connection.setConnectionBoxWidth(width);
	}
	
	
	

}