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
public class MetaConnectionDrawable implements Drawable, Moveable, Clickable, Resizeable, Focusable, ResizeHandler, MoveHandler {
	
	
	
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
	
	
	private MetaConnectionBox connectionBox;

	// Handlers
	private List<FocusHandler> focusHandlers;
	private List<ClickHandler> clickHandlers;
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	
	private List<AnchorPoint> sourceToBoxAnchorPoints;
	private List<AnchorPoint> boxToTargetAnchorPoints;
	
	private AnchorPoint sourceAnchorPoint;
	private AnchorPoint sourceConnectionBoxAnchorPoint;
	private AnchorPoint targetAnchorPoint;
	private AnchorPoint targetConnectionBoxAnchorPoint;
	
	
	
	
	
	
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
		
		sourceConnectionBoxRelativeX = connection.getSourceConnectionBoxRelativePoint().x;
		sourceConnectionBoxRelativeY = connection.getSourceConnectionBoxRelativePoint().y;
		sourceRelativeX = connection.getSourceRelativePoint().x;
		sourceRelativeY = connection.getSourceConnectionBoxRelativePoint().y;
		
		targetConnectionBoxRelativeX = connection.getTargetConnectionBoxRelativePoint().x;
		targetConnectionBoxRelativeY = connection.getTargetConnectionBoxRelativePoint().y;
		targetRelativeX = connection.getTargetRelativePoint().x;
		targetRelativeY = connection.getTargetRelativePoint().y;
		
		setMetaClassA(metaClassA);
		setMetaClassB(metaClassB);
		
		sourceAnchorPoint = new AnchorPoint(connection.getSourceRelativePoint(), source);
		sourceConnectionBoxAnchorPoint = new AnchorPoint(connection.getSourceConnectionBoxRelativePoint(), connectionBox);
		targetConnectionBoxAnchorPoint = new AnchorPoint(connection.getTargetConnectionBoxRelativePoint(), connectionBox);
		targetAnchorPoint = new AnchorPoint(connection.getTargetRelativePoint(), target);
		
		sourceToBoxAnchorPoints = new ArrayList<AnchorPoint>();
		boxToTargetAnchorPoints = new ArrayList<AnchorPoint>();
	}
	
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.beginPath();
		context.setFillStyle(connection.getLineColor());
		context.setLineWidth(connection.getLineSize());
		
		context.moveTo(source.getX() + getSourceRelativeX(), source.getY() + getSourceRelativeY());
		context.lineTo( connectionBox.getX()+ getSourceConnectionBoxRelativeX(), connectionBox.getY()+ getSourceConnectionBoxRelativeY());
		
		context.moveTo(target.getX() + getTargetRelativeX(), target.getY() + getTargetRelativeY());
		context.lineTo(connectionBox.getX() + getTargetConnectionBoxRelativeX(), connectionBox.getY() + getTargetConnectionBoxRelativeY());
	
		
		context.stroke();
		context.restore();
		
		connectionBox.draw(context);
		
		if (connection.isSelected())
			drawOnSelect(context);
		
	}
	
	
	private void drawOnSelect(Context2d context)
	{
		sourceAnchorPoint.draw(context);
		sourceConnectionBoxAnchorPoint.draw(context);
		targetAnchorPoint.draw(context);
		targetConnectionBoxAnchorPoint.draw(context);
		
		for (AnchorPoint a: sourceToBoxAnchorPoints)
			a.draw(context);
		
		for (AnchorPoint a: boxToTargetAnchorPoints)
			a.draw(context);
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
	
		// linear function 
		// y = m*x + b
		/*
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
		*/
		return false;
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
		connectionBox.setX(x);
	}


	@Override
	public void setY(double y) {
		connectionBox.setY(y);
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


	@Override
	public void addResizeHandler(ResizeHandler resizeHandler) {
		if (!resizeHandlers.contains(resizeHandler))
			resizeHandlers.add(resizeHandler);
		
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

}
