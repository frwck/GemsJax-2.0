package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;

/**
 * This class is a {@link Drawable} that displays a {@link MetaConnectionImpl} on the {@link MetaClassCanvas}.
 * A {@link MetaConnectionImpl} is displayed between two {@link MetaClassDrawable}s.<br /> <br />
 * This class implements the {@link ResizeHandler} and the {@link MoveHandler} interface and will be registered to the 
 * connected {@link MetaClassDrawable}s. So when a {@link MetaClassDrawable} was moved / resized, this {@link MetaConnectionDrawable}
 * will automatically adjust itself to the {@link MetaClassDrawable}s.
 * 
 * This class paints the lines and icons for a connection, while the {@link MetaConnectionBoxDrawable} draws a box with the name 
 * and Attributes.
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionDrawable implements Drawable, Moveable, Clickable, Focusable, ResizeHandler, MoveHandler {
	
	
	
	/**
	 * The X coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the source.
	 * The source is the {@link MetaClass} which contains this MetaConnection in {@link MetaClass#getConnections()}.
	 * This coordinate is relative to the source {@link MetaClassDrawable} object on the {@link MetaModelCanvas}.
	 * That means, that the {@link MetaClassImpl#getX()} is the relative 0 coordinate on the x axis.
	 * Example:
	 * If the {@link MetaClassImpl#getX()} has the absolute coordinate 10 and {@link #sourceRelativeX} is 5, so the
	 * absolute coordinate on the {@link MetaModelCanvas} is 15 and can be computed by add {@link MetaClassImpl#getX()} to aRelativeX
	 *@see #getSourceRelativeX()
	 */
	private double sourceRelativeX;
	
	/**
	 * The Y coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the source.
	 * See {@link #sourceRelativeX} for more information and a concrete example.
	 * @see #getSourceRelativeY()
	 */
	private double sourceRelativeY;
	
	/**
	 * The X coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #target}.
	 * See {@link #sourceRelativeX} for more information and a concrete example.
	 * @see #getTargetRelativeX()
	 */
	private double targetRelativeX;
	
	/**
	 * The Y coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #target}.
	 * See {@link #sourceRelativeX} for more information and a concrete example.
	 * @see #getTargetRelativeY()
	 */
	private double targetRelativeY;
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #connectionBoxX}) 
	 * where the painted connection line
	 * (starting from the source with the coordinates {@link #sourceRelativeX} / {@link #sourceRelativeY}) touches
	 * the connection box (which displays the {@link #name} and attributes of this Connection.
	 */
	private double sourceConnectionBoxRelativeX;
	
	/**
	 * The relative Y coordinate (according to the absolute coordinate {@link #connectionBoxY}) 
	 * where the painted connection line
	 * (starting from the source, coordinates {@link #sourceRelativeX} / {@link #sourceRelativeY}) touches
	 * the connection box (which displays the {@link #name} and attributes of this Connection.
	 */
	private double sourceConnectionBoxRelativeY;
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #connectionBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #target}, coordinates {@link #targetRelativeX} / {@link #targetRelativeY}) touches
	 * the connection box (which displays the {@link #name} and attributes of this Connection.
	 */
	private double targetConnectionBoxRelativeX;
	
	/**
	 * The relative Y coordinate (according to the absolute coordinate {@link #connectionBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #target}, coordinates {@link #targetRelativeX} / {@link #targetRelativeY}) touches
	 * the name box (which displays the {@link #name} and attributes of this Connection.
	 */
	private double targetConnectionBoxRelativeY;
	
	

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
	
	
	private MetaConnectionBoxDrawable connectionBoxDrawable;

	// Handlers
	private List<FocusHandler> focusHandlers;
	private List<ClickHandler> clickHandlers;
	private List<MoveHandler> moveHandlers;
	
	
	
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
		
		this.connectionBoxDrawable = new MetaConnectionBoxDrawable(connection, this);
		
		// Handlers
		focusHandlers = new ArrayList<FocusHandler>();
		clickHandlers = new ArrayList<ClickHandler>();
		moveHandlers = new ArrayList<MoveHandler>();
		
		sourceConnectionBoxRelativeX = connection.getSourceConnectionBoxRelativeX();
		sourceConnectionBoxRelativeY = connection.getSourceConnectionBoxRelativeY();
		sourceRelativeX = connection.getSourceRelativeX();
		sourceRelativeY = connection.getSourceConnectionBoxRelativeY();
		
		targetConnectionBoxRelativeX = connection.getTargetConnectionBoxRelativeX();
		targetConnectionBoxRelativeY = connection.getTargetConnectionBoxRelativeY();
		targetRelativeX = connection.getTargetRelativeX();
		targetRelativeY = connection.getTargetRelativeY();
		
		setMetaClassA(metaClassA);
		setMetaClassB(metaClassB);
	}
	
	
	public MetaConnectionBoxDrawable getConnectionBoxDrawable()
	{
		return connectionBoxDrawable;
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.beginPath();
		context.setFillStyle(connection.getLineColor());
		context.setLineWidth(connection.getLineSize());
		
		context.moveTo(source.getX() + getSourceRelativeX(), source.getY() + getSourceRelativeY());
		context.lineTo( connectionBoxDrawable.getX()+ getSourceConnectionBoxRelativeX(), connectionBoxDrawable.getY()+ getSourceConnectionBoxRelativeY());
		
		context.moveTo(target.getX() + getTargetRelativeX(), target.getY() + getTargetRelativeY());
		context.lineTo(connectionBoxDrawable.getX() + getTargetConnectionBoxRelativeX(), connectionBoxDrawable.getY() + getTargetConnectionBoxRelativeY());
	
		
		context.stroke();
		context.restore();
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
	
		// linear function 
		// y = m*x + b
		
		// For the line between MetaClass A and NameBox
		double y2 = connection.getSource().getY() + connection.getSourceRelativeY() ;
		double y1 = connection.getConnectionBoxY() + connection.getSourceConnectionBoxRelativeY();
		
		double x2 = connection.getSource().getX() + connection.getSourceRelativeX();
		double x1 = connection.getConnectionBoxX() + connection.getSourceConnectionBoxRelativeX(); 
		
		double m = (y2 -y1)/ (x2 - x1);
		
		double b = y1 - m*x1;
		
		boolean onAToNameBox = (y == m *x + b);
		
		if (onAToNameBox)
			return true;
		
		
		// For the line between MetaClass B and NameBox
		y2 = connection.getTarget().getY() + connection.getTargetRelativeY() ;
		y1 = connection.getConnectionBoxY() + connection.getTargetConnectionBoxRelativeY();
		
		x2 = connection.getTarget().getX() + connection.getSourceRelativeX();
		x1 = connection.getConnectionBoxX() + connection.getSourceConnectionBoxRelativeX(); 
		
		m = (y2 -y1)/ (x2 - x1);
		
		b = y1 - m*x1;
		
		boolean onBToNameBox = (y == m*x +b);
		
		if (onBToNameBox )
			Window.alert("Connection clicked");
		
		return onBToNameBox;
		
	}

	

	@Override
	public void addMoveHandler(MoveHandler handler) {
		if (!moveHandlers.contains(handler))
			moveHandlers.add(handler);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public void removeMoveHandler(MoveHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClickHandler(ClickHandler handler) {
		
		if (!moveHandlers.contains(handler))
		clickHandlers.add(handler);
		
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
			focusHandlers.add(handler);
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
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onMove(MoveEvent e) {
		// TODO Auto-generated method stub
		
	}





	public MetaClassDrawable getMetaClassA() {
		return source;
	}




	/**
	 * Set the other end of the {@link MetaConnectionDrawable} (named {@link #source}).
	 * This method register this ConnectionDrawable as {@link MoveHandler} and {@link ResizeHandler} for the
	 * new set metaClassA. It also set directly the {@link MetaConnectionImpl}s {@link MetaClassImpl} by calling {@link MetaConnectionImpl#setMetaClassA(MetaClassImpl)}.
	 * If metaClassA is set previously, this ConnectionDrawable will be unregistered as {@link ResizeHandler} / {@link MoveHandler}.
	 * @param metaClassA
	 */
	public void setMetaClassA(MetaClassDrawable metaClassA) {
		
		if (this.source!=null)
		{
			this.source.removeMoveHandler(this);
			this.source.removeResizeHandler(this);
		}
		
		this.source = metaClassA;
		//TODO this should not be done directly, but by the presenter
		connection.setSource((MetaClassImpl)metaClassA.getDataObject());
		
		this.source.addMoveHandler(this);
		this.source.addResizeHandler(this);
	}





	public MetaClassDrawable getMetaClassB() {
		return target;
	}





	/**
	 * Set the other end of the {@link MetaConnectionDrawable} (named {@link #target} ).
	 * This method register this ConnectionDrawable as {@link MoveHandler} and {@link ResizeHandler} for the
	 * new set metaClassB. It also set directly the {@link MetaConnectionImpl}s {@link MetaClassImpl} by calling {@link MetaConnectionImpl#setMetaClassB(MetaClassImpl)}.
	 * If metaClassB is set previously, this ConnectionDrawable will be unregistered as {@link ResizeHandler} / {@link MoveHandler}.
	 * @param metaClassB
	 */
	public void setMetaClassB(MetaClassDrawable metaClassB) {
		
		if (this.target!=null)
		{
			this.target.removeMoveHandler(this);
			this.target.removeResizeHandler(this);
		}
		
		this.target = metaClassB;
		
		// TODO this should not be done in the drawable, but in the presenter
		connection.setTarget((MetaClassImpl)metaClassB.getDataObject());
		
		this.target.addMoveHandler(this);
		this.target.addResizeHandler(this);
		
	}


	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub
		
	}


	public double getSourceRelativeX() {
		return sourceRelativeX;
	}


	public void setSourceRelativeX(double sourceRelativeX) {
		this.sourceRelativeX = sourceRelativeX;
	}


	public double getSourceRelativeY() {
		return sourceRelativeY;
	}


	public void setSourceRelativeY(double sourceRelativeY) {
		this.sourceRelativeY = sourceRelativeY;
	}


	public double getTargetRelativeX() {
		return targetRelativeX;
	}


	public void setTargetRelativeX(double targetRelativeX) {
		this.targetRelativeX = targetRelativeX;
	}


	public double getTargetRelativeY() {
		return targetRelativeY;
	}


	public void setTargetRelativeY(double targetRelativeY) {
		this.targetRelativeY = targetRelativeY;
	}


	public double getSourceConnectionBoxRelativeX() {
		return sourceConnectionBoxRelativeX;
	}


	public void setSourceConnectionBoxRelativeX(double sourceConnectionBoxRelativeX) {
		this.sourceConnectionBoxRelativeX = sourceConnectionBoxRelativeX;
	}


	public double getSourceConnectionBoxRelativeY() {
		return sourceConnectionBoxRelativeY;
	}


	public void setSourceConnectionBoxRelativeY(double sourceConnectionBoxRelativeY) {
		this.sourceConnectionBoxRelativeY = sourceConnectionBoxRelativeY;
	}


	public double getTargetConnectionBoxRelativeX() {
		return targetConnectionBoxRelativeX;
	}


	public void setTargetConnectionBoxRelativeX(double targetConnectionBoxRelativeX) {
		this.targetConnectionBoxRelativeX = targetConnectionBoxRelativeX;
	}


	public double getTargetConnectionBoxRelativeY() {
		return targetConnectionBoxRelativeY;
	}


	public void setTargetConnectionBoxRelativeY(double targetConnectionBoxRelativeY) {
		this.targetConnectionBoxRelativeY = targetConnectionBoxRelativeY;
	}

}
