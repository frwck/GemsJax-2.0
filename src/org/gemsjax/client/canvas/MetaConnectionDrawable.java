package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.form.validator.IsBooleanValidator;

/**
 * This class is a {@link Drawable} that displays a {@link MetaConnection} on the {@link MetaClassCanvas}.
 * A {@link MetaConnection} is displayed between two {@link MetaClassDrawable}s.<br /> <br />
 * This class implements the {@link ResizeHandler} and the {@link MoveHandler} interface and will be registered to the 
 * connected {@link MetaClassDrawable}s. So when a {@link MetaClassDrawable} was moved / resized, this {@link MetaConnectionDrawable}
 * will automatically adjust itself to the {@link MetaClassDrawable}s.
 * 
 * 
 * This class paints the lines and icons for a connection, while the {@link MetaConnectionBox} draws a box with the name 
 * and Attributes. But the {@link MetaConnectionBox} itself is not a real Drawable, its only part of this MetaConnectionDrawable.
 * So this {@link MetaConnectionDrawable} implements the {@link Moveable} and {@link Resizeable} interface. This corresponding functionality is wrapped
 * by this MetaConnectionDrawable, but will be delegated to the {@link MetaConnectionBox}.
 * So the functionality is splitted in two classes ( {@link MetaConnectionDrawable} and {@link MetaConnectionBox}) but on the {@link MetaModelCanvas}
 * appears only this {@link MetaConnectionDrawable}
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionDrawable implements Drawable, Moveable, Clickable, Resizeable, Focusable, ResizeHandler, HasPlaceable {
	
	/**
	 * The {@link MetaConnectionImpl} that is displayed with this Drawable
	 */
	private MetaConnection connection;
	
	/**
	 * The Connection connects {@link #source} with {@link #target}.
	 */
	private MetaClassDrawable source;

	/**
	 * The Connection connects {@link #source} with {@link #target}.
	 */
	private MetaClassDrawable target;
	
	
	private MetaConnectionBox connectionBox;

	// Handlers
	private List<FocusHandler> focusHandlers;
	private List<ClickHandler> clickHandlers;
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	
	private Anchor sourceAnchor;
	private Anchor sourceConnectionBoxAnchor;
	private Anchor targetAnchor;
	private Anchor targetConnectionBoxAnchor;
	
	/**
	 * The mapping between the AnchorPoint (model) and the Anchor (View)
	 */
	private HashMap<AnchorPoint, Anchor> anchorsMap;
	
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line 
	 */
	private double mouseOffSet = 7;
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line, but the line is not a linear function, 
	 * but a vertical line instead
	 */
	private double verticalLineXOffset=7;
	
	
	
	/**
	 * Creates {@link Drawable} that displays a {@link MetaConnectionImpl}.
	 * A {@link MetaConnectionImpl} is displayed between two {@link MetaClassImpl}es, 
	 * @param connection The {@link MetaConnectionImpl} that is displayed with this Drawable
	 * @param metaClassA The {@link MetaClassDrawable} where the connection starts.
	 * @param metaClassB The {@link MetaClassDrawable} where the connection ends.
	 */
	public MetaConnectionDrawable(MetaConnection connection, MetaClassDrawable metaClassA, MetaClassDrawable metaClassB)
	{
		this.connection = connection;
		this.source = metaClassA;
		this.target = metaClassB;
		
		this.connectionBox = new MetaConnectionBox(connection, this);
		
		// Handlers
		focusHandlers = new ArrayList<FocusHandler>();
		clickHandlers = new ArrayList<ClickHandler>();
		moveHandlers = new ArrayList<MoveHandler>();
		resizeHandlers = new ArrayList<ResizeHandler>();

		
		
		
		sourceAnchor = new Anchor(connection.getSourceRelativePoint(), source);
		sourceConnectionBoxAnchor = new Anchor(connection.getSourceConnectionBoxRelativePoint(), connectionBox);
		targetConnectionBoxAnchor = new Anchor(connection.getTargetConnectionBoxRelativePoint(), connectionBox);
		targetAnchor = new Anchor(connection.getTargetRelativePoint(), target);
		
		anchorsMap = new HashMap<AnchorPoint, Anchor>();
		
		anchorsMap.put(connection.getSourceRelativePoint(), sourceAnchor);
		anchorsMap.put(connection.getSourceConnectionBoxRelativePoint(), sourceConnectionBoxAnchor);
		anchorsMap.put(connection.getTargetConnectionBoxRelativePoint(), targetConnectionBoxAnchor);
		anchorsMap.put(connection.getTargetRelativePoint(), targetAnchor);
		
		// TODO remove test
		sourceAnchor.setNextAnchor(sourceConnectionBoxAnchor);
		targetConnectionBoxAnchor.setNextAnchor(targetAnchor);
		
		
		
		setSource(metaClassA);
		setTarget(metaClassB);
		
	}
	
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.beginPath();
		context.setFillStyle(connection.getLineColor());
		context.setLineWidth(connection.getLineSize());
		
		context.moveTo(source.getX() + sourceAnchor.getX(), source.getY() + sourceAnchor.getY());
		context.lineTo( connectionBox.getX()+ sourceConnectionBoxAnchor.getX(), connectionBox.getY()+ sourceConnectionBoxAnchor.getY());
		
		context.moveTo(target.getX() + targetAnchor.getX(), target.getY() + targetAnchor.getY());
		context.lineTo(connectionBox.getX() + targetConnectionBoxAnchor.getX(), connectionBox.getY() + targetConnectionBoxAnchor.getY());
	
		
		context.stroke();
		context.restore();
		
		connectionBox.draw(context);
		
		if (connection.isSelected())
			drawOnSelect(context);
		
	}
	
	
	private void drawOnSelect(Context2d context)
	{
		sourceAnchor.draw(context);
		sourceConnectionBoxAnchor.draw(context);
		targetAnchor.draw(context);
		targetConnectionBoxAnchor.draw(context);
		
		Anchor a = sourceAnchor.getNextAnchor();
		
		// Draw anchor points between the source and the connection box	
		while (a!=sourceConnectionBoxAnchor)
		{
			a.draw(context);
			a = a.getNextAnchor();
		}
		

		a=targetConnectionBoxAnchor.getNextAnchor();
		// Draw Anchot points between the connection box and the target
		while (a!=targetAnchor)
		{
			a.draw(context);
			a = a.getNextAnchor();
		}
		
	}
	
	
	@Override
	public boolean isSelected()
	{
		return connection.isSelected();
	}

	@Override
	public double getZIndex() {
		return connection.getZIndex();
	}


	
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		
		boolean box = connectionBox.hasCoordinate(x, y);
		
		if (box ) return true;
		
		
		double m =0, b = 0 , t = 0, currentX=0, currentY=0, nextX=0, nextY = 0;
		
		Anchor current = sourceAnchor;
		Anchor next = null;
		
		
		while (current != sourceConnectionBoxAnchor)
		{
			next = current.getNextAnchor();
			
			// if the current is the sourceAnchorPoint, which is the start point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (current == sourceAnchor)
			{
				currentX = source.getX() + sourceAnchor.getX();
				currentY = source.getY() + sourceAnchor.getY();
			}
			else
			{
				currentX = current.getX();
				currentY = current.getY();
			}
			
			
			
			// if the next is the sourceConnectionBoxAnchorPoint, which is the end point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (next == sourceConnectionBoxAnchor)
			{
				nextX = connectionBox.getX() + sourceConnectionBoxAnchor.getX();
				nextY = connectionBox.getY() + sourceConnectionBoxAnchor.getY();
			}
			else
			{
				nextX = next.getX();
				nextY = next.getY();
			}
			
			
			
			// special case that current and next have the same x coordinate, so there is a vertical line instead of a linear function
			if (Math.abs(currentX-x)<=verticalLineXOffset && Math.abs(nextX-x)<=verticalLineXOffset)
			{
				if (currentY<nextY && isBetween(currentY, nextY, y))
					return true;
				else
				if (currentY>=nextY && isBetween(nextY, currentY, y))
					return true;
			}
			
			
			// calculate the slopetriangle 
			m = (currentY - nextY) / (currentX - nextX);
			
			// calculate x axis deferral	b = y - m*x
			b = currentY - m * currentX;
			
			// temp variable to calculate  m * x + b = t
			t = m * x + b;
			
			if (Math.abs(t - y)<=mouseOffSet)
				return true;
			
			current = next;
		}
		
	
		current = targetConnectionBoxAnchor;
		next = null;
		
		
		while (current != targetAnchor)
		{
			next = current.getNextAnchor();
			
			// if the current is the targetConnectionBoxAnchorPoint, which is the start point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (current == targetConnectionBoxAnchor)
			{
				currentX = connectionBox.getX() + targetConnectionBoxAnchor.getX();
				currentY = connectionBox.getY() + targetConnectionBoxAnchor.getY();
			}
			else
			{
				currentX = current.getX();
				currentY = current.getY();
			}
			
			
			// if the next is the sourceConnectionBoxAnchorPoint, which is the end point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (next == targetAnchor)
			{
				nextX = target.getX() + targetAnchor.getX();
				nextY = target.getY() + targetAnchor.getY();
			}
			else
			{
				nextX = next.getX();
				nextY = next.getY();
			}
			
			
			
			// special case that current and next have the same x coordinate, so there is a vertical line instead of a linear function
			if (Math.abs(currentX-x)<=verticalLineXOffset && Math.abs(nextX-x)<=verticalLineXOffset)
			{
				if (currentY<nextY && isBetween(currentY, nextY, y))
					return true;
				else
				if (currentY>=nextY && isBetween(nextY, currentY, y))
					return true;
			}
			
			// calculate the slopetriangle 
			m = (currentY - nextY) / (currentX - nextX);
			
			// calculate x axis deferral	b = y - m*x
			b = currentY - m * currentX;
			
			// temp variable to calculate  m * x + b = t
			t = m * x + b;
			
			if (Math.abs(t - y)<=mouseOffSet)
				return true;
			
			current = next;
		}
		
		return false;
	}

	

	@Override
	public void addMoveHandler(MoveHandler handler) {
		if (!moveHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				moveHandlers.add(moveHandlers.size(), handler);
			else
				moveHandlers.add(0,handler);
	}

	@Override
	public boolean fireMoveEvent(MoveEvent event) {
		boolean delivered = false;
		
		for (MoveHandler h : moveHandlers)
		{
			h.onMove(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public double getX() {
		// Used by the Moveable
		return connectionBox.getX();
	}

	@Override
	public double getY() {
		return connectionBox.getY();
	}

	

	@Override
	public void removeMoveHandler(MoveHandler handler) {
		moveHandlers.remove(handler);
	}

	@Override
	public void addClickHandler(ClickHandler handler) {
		
		if (!clickHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should allways be the last in the list
				clickHandlers.add(clickHandlers.size(), handler);
			else
				clickHandlers.add(0,handler);
		
	}

	@Override
	public boolean fireClickEvent(ClickEvent event) {
		
		boolean delivered = false;
		
		for (ClickHandler h : clickHandlers)
		{
			h.onClick(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeClickHandler(ClickHandler handler) {
		clickHandlers.remove(handler);
	}

	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				focusHandlers.add(focusHandlers.size(), handler);
			else
				focusHandlers.add(0,handler);
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		
		boolean delivered = false;
		
		for (FocusHandler h : focusHandlers)
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
	public Object getDataObject() {
		return connection;
	}



	@Override
	public void onResize(ResizeEvent event) {
		
		/* Hanldle only TEMP_RESIZE Events, because the RESIZE_FINISHED should be caught by the MetaModelPresenter.  */
		if (event.getType()==ResizeEventType.TEMP_RESIZE || event.getType()==ResizeEventType.RESIZE_FINISHED )
			if (event.getSource() == this.source)
			{
				
				double x = sourceAnchor.getX();
				double y = sourceAnchor.getY();
				double oldWidth = source.getWidth();
				double oldHeight = source.getHeight();
				
				if (x==oldWidth && oldWidth!=event.getWidth())
					sourceAnchor.setX(event.getWidth());
				
				if (y == oldHeight && oldHeight!=event.getHeight())
					sourceAnchor.setY(event.getHeight());
				
			}
			else
			if (event.getSource() == this.target)
			{
				double x = targetAnchor.getX();
				double y = targetAnchor.getY();
				double oldWidth = target.getWidth();
				double oldHeight = target.getHeight();
				
				if (x==oldWidth && oldWidth!=event.getWidth())
					targetAnchor.setX(event.getWidth());
				
				if (y == oldHeight && oldHeight!=event.getHeight())
					targetAnchor.setY(event.getHeight());
			}
			
		
		
		
	}


	public MetaClassDrawable getSource() {
		return source;
	}




	/**
	 * Set the source (Drawable) of this {@link MetaConnectionDrawable} (named {@link #source}).
	 * This method register this {@link MetaConnectionDrawable} as an {@link ResizeHandler} for the
	 * new set source {@link MetaClassDrawable}.
	 * If the source was set previously, this call will unregister this {@link MetaConnectionDrawable} as a {@link ResizeHandler} to the old source.
	 * @param source
	 */
	public void setSource(MetaClassDrawable source) {
		
		if (this.source!=null)
		{
			this.source.removeResizeHandler(this);
			this.source.undockAnchor(sourceAnchor);
		}
		
		this.source = source;
		this.source.dockAnchor(sourceAnchor);
		
		this.source.addResizeHandler(this);
	}





	public MetaClassDrawable getTarget() {
		return target;
	}





	/**
	 * Set the target (Drawable) of this {@link MetaConnectionDrawable} (named {@link #source}).
	 * This method register this {@link MetaConnectionDrawable} as an {@link ResizeHandler} for the
	 * new set target {@link MetaClassDrawable}.
	 * If the target was set previously, this call will unregister this {@link MetaConnectionDrawable} as a {@link ResizeHandler} to the old target.
	 * @param target
	 */
	public void setTarget(MetaClassDrawable target) {
		
		if (this.target!=null)
		{
			this.target.removeResizeHandler(this);
			this.target.undockAnchor(targetAnchor);
		}
		
		this.target = target;
		this.target.dockAnchor(targetAnchor);
		
		this.target.addResizeHandler(this);
		
	}


	@Override
	public void setX(double x) {
		connectionBox.setX(x);
	}


	@Override
	public void setY(double y) {
		connectionBox.setY(y);
	}




	@Override
	public void addResizeHandler(ResizeHandler handler) {
		if (!resizeHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				resizeHandlers.add(resizeHandlers.size(), handler);
			else
				resizeHandlers.add(0,handler);
		
	}


	@Override
	public boolean fireResizeEvent(ResizeEvent event) {

		boolean delivered = false;
		
		for (ResizeHandler h : resizeHandlers)
		{
			h.onResize(event);
			delivered = true;
		}
		
		return delivered;
	}


	@Override
	public double getHeight() {
		return connectionBox.getHeight();
	}


	@Override
	public double getWidth() {
		return connectionBox.getWidth();
	}


	@Override
	public ResizeArea isResizerAreaAt(double x, double y) {
		return connectionBox.isResizerAreaAt(x, y);
	}


	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}


	@Override
	public void setHeight(double height) {
		connectionBox.setHeight(height);
	}


	@Override
	public void setWidth(double width) {
		connectionBox.setWidth(width);
	}





	@Override
	public double getMinHeight() {
		return connectionBox.getMinHeight();
	}





	@Override
	public double getMinWidth() {
		return connectionBox.getMinWidth();
	}
	
	
	/**
	 * Automatically adjust the coordinates of the {@link #sourceConnectionBoxAnchor} and {@link #targetConnectionBoxAnchor}.
	 * This must always be called, when the width or the height has been changed.
	 */
	public void adjustConnectionBoxAnchors()
	{
		
		if (sourceConnectionBoxAnchor.getY() > getHeight())
			sourceConnectionBoxAnchor.setY(getHeight());
		
		if (sourceConnectionBoxAnchor.getX()> getWidth())
			sourceConnectionBoxAnchor.setX(getWidth());
		
		
		if (sourceConnectionBoxAnchor.getY()<getHeight() && sourceConnectionBoxAnchor.getY() > 0)
			if (sourceConnectionBoxAnchor.getX()>0 && sourceConnectionBoxAnchor.getX()<getWidth())
				sourceConnectionBoxAnchor.setX(getWidth());


		if (sourceConnectionBoxAnchor.getX()<getWidth() && sourceConnectionBoxAnchor.getX()>0)
			if (sourceConnectionBoxAnchor.getY()>0 && sourceConnectionBoxAnchor.getY()<getHeight())
				sourceConnectionBoxAnchor.setY(getHeight());
		
		
		
		
		if (targetConnectionBoxAnchor.getY() > getHeight())
			targetConnectionBoxAnchor.setY(getHeight());
		
		if (targetConnectionBoxAnchor.getX()> getWidth())
			targetConnectionBoxAnchor.setX(getWidth());
		
		
		if (targetConnectionBoxAnchor.getY()<getHeight() && targetConnectionBoxAnchor.getY()>0)
			if (targetConnectionBoxAnchor.getX()>0 && targetConnectionBoxAnchor.getX()<getWidth())
				targetConnectionBoxAnchor.setX(getWidth());
		

		if (targetConnectionBoxAnchor.getX()<getWidth() && targetConnectionBoxAnchor.getX()>0)
			if (targetConnectionBoxAnchor.getY()>0 && targetConnectionBoxAnchor.getY()<getHeight())
				targetConnectionBoxAnchor.setY(getHeight());
	}









	public Anchor getSourceAnchor() {
		return sourceAnchor;
	}





	public Anchor getSourceConnectionBoxAnchor() {
		return sourceConnectionBoxAnchor;
	}





	public Anchor getTargetAnchor() {
		return targetAnchor;
	}





	public Anchor getTargetConnectionBoxAnchor() {
		return targetConnectionBoxAnchor;
	}


	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}

	



	@Override
	public Anchor hasPlaceableAt(double x, double y) {
		
		
		
		// Calculate the absolute Positions for the 4 default AnchorPoints
		if (isBetween(source.getX()+sourceAnchor.getX()-(sourceAnchor.getWidth()/2), source.getX()+sourceAnchor.getX()+(sourceAnchor.getWidth()/2), x)
			&& isBetween(source.getY()+sourceAnchor.getY()-(sourceAnchor.getHeight()/2), source.getY()+sourceAnchor.getY()+(sourceAnchor.getHeight()/2), y)
			)
			return sourceAnchor;
		
		
		if (isBetween(connectionBox.getX()+sourceConnectionBoxAnchor.getX()-(sourceConnectionBoxAnchor.getWidth()/2), connectionBox.getX()+sourceConnectionBoxAnchor.getX()+(sourceConnectionBoxAnchor.getWidth()/2), x)
			&& isBetween(connectionBox.getY()+sourceConnectionBoxAnchor.getY()-(sourceConnectionBoxAnchor.getHeight()/2), connectionBox.getY()+sourceConnectionBoxAnchor.getY()+(sourceConnectionBoxAnchor.getHeight()/2), y)
			)
			return sourceConnectionBoxAnchor;
		
		
		if (isBetween(connectionBox.getX()+targetConnectionBoxAnchor.getX()-(targetConnectionBoxAnchor.getWidth()/2), connectionBox.getX()+targetConnectionBoxAnchor.getX()+(targetConnectionBoxAnchor.getWidth()/2), x)
			&& isBetween(connectionBox.getY()+targetConnectionBoxAnchor.getY()-(targetConnectionBoxAnchor.getHeight()/2), connectionBox.getY()+targetConnectionBoxAnchor.getY()+(targetConnectionBoxAnchor.getHeight()/2), y)
			)
			return targetConnectionBoxAnchor;
		
		
		// Calculate the absolute Positions for the 4 default AnchorPoints
		if (isBetween(target.getX()+targetAnchor.getX()-(targetAnchor.getWidth()/2), target.getX()+targetAnchor.getX()+(targetAnchor.getWidth()/2), x)
			&& isBetween(target.getY()+targetAnchor.getY()-(targetAnchor.getHeight()/2), target.getY()+targetAnchor.getY()+(targetAnchor.getHeight()/2), y)
			)
			return targetAnchor;
		
		
		
		
		
		
		// check AnchorPoints between source and connection box
		Anchor a = sourceAnchor.getNextAnchor();
		// if we are at the end, than null should be there
		while (a!=sourceConnectionBoxAnchor)
		{
			if (a.hasCoordinate(x, y))
				return a;
			
			a = a.getNextAnchor();
		}
		
		
		// check AnchorPoints between connection Box and target
		a=targetConnectionBoxAnchor.getNextAnchor();
		
		while (a!=targetAnchor)
		{
			if (a.hasCoordinate(x, y))
				return a;
			
			a = a.getNextAnchor();
		}
		
		
		return null;
	}
	
	
	
	public MetaClassDrawable getSourceDrawable()
	{
		return source;
	}
	
	
	public MetaClassDrawable getTargetDrawable()
	{
		return target;
	}
	
	public MetaConnectionBox getConnectionBox()
	{
		return connectionBox;
	}

}
