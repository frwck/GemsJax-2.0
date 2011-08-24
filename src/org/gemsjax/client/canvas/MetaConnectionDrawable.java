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
		
		context.moveTo(source.getX() + sourceAnchorPoint.getX(), source.getY() + sourceAnchorPoint.getY());
		context.lineTo( connectionBox.getX()+ sourceConnectionBoxAnchorPoint.getX(), connectionBox.getY()+ sourceConnectionBoxAnchorPoint.getY());
		
		context.moveTo(target.getX() + targetAnchorPoint.getX(), target.getY() + targetAnchorPoint.getY());
		context.lineTo(connectionBox.getX() + targetConnectionBoxAnchorPoint.getX(), connectionBox.getY() + targetConnectionBoxAnchorPoint.getY());
	
		
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
	
	
	/**
	 * Automatically adjust the coordinates of the {@link #sourceConnectionBoxAnchorPoint} and {@link #targetConnectionBoxAnchorPoint}.
	 * This must always be called, when the width or the height has been changed.
	 */
	public void adjustConnectionBoxAnchors()
	{
		
		if (sourceConnectionBoxAnchorPoint.getY() > getHeight())
			sourceConnectionBoxAnchorPoint.setY(getHeight());
		
		if (sourceConnectionBoxAnchorPoint.getX()> getWidth())
			sourceConnectionBoxAnchorPoint.setX(getWidth());
		
		
		if (sourceConnectionBoxAnchorPoint.getY()<getHeight() && sourceConnectionBoxAnchorPoint.getY() > 0)
			if (sourceConnectionBoxAnchorPoint.getX()>0 && sourceConnectionBoxAnchorPoint.getX()<getWidth())
				sourceConnectionBoxAnchorPoint.setX(getWidth());


		if (sourceConnectionBoxAnchorPoint.getX()<getWidth() && sourceConnectionBoxAnchorPoint.getX()>0)
			if (sourceConnectionBoxAnchorPoint.getY()>0 && sourceConnectionBoxAnchorPoint.getY()<getHeight())
				sourceConnectionBoxAnchorPoint.setY(getHeight());
		
		
		
		
		if (targetConnectionBoxAnchorPoint.getY() > getHeight())
			targetConnectionBoxAnchorPoint.setY(getHeight());
		
		if (targetConnectionBoxAnchorPoint.getX()> getWidth())
			targetConnectionBoxAnchorPoint.setX(getWidth());
		
		
		if (targetConnectionBoxAnchorPoint.getY()<getHeight() && targetConnectionBoxAnchorPoint.getY()>0)
			if (targetConnectionBoxAnchorPoint.getX()>0 && targetConnectionBoxAnchorPoint.getX()<getWidth())
				targetConnectionBoxAnchorPoint.setX(getWidth());
		

		if (targetConnectionBoxAnchorPoint.getX()<getWidth() && targetConnectionBoxAnchorPoint.getX()>0)
			if (targetConnectionBoxAnchorPoint.getY()>0 && targetConnectionBoxAnchorPoint.getY()<getHeight())
				targetConnectionBoxAnchorPoint.setY(getHeight());
	}





	public List<AnchorPoint> getSourceToBoxAnchorPoints() {
		return sourceToBoxAnchorPoints;
	}





	public List<AnchorPoint> getBoxToTargetAnchorPoints() {
		return boxToTargetAnchorPoints;
	}





	public AnchorPoint getSourceAnchorPoint() {
		return sourceAnchorPoint;
	}





	public AnchorPoint getSourceConnectionBoxAnchorPoint() {
		return sourceConnectionBoxAnchorPoint;
	}





	public AnchorPoint getTargetAnchorPoint() {
		return targetAnchorPoint;
	}





	public AnchorPoint getTargetConnectionBoxAnchorPoint() {
		return targetConnectionBoxAnchorPoint;
	}

}
