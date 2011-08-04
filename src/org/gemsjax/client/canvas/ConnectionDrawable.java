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
import org.gemsjax.client.model.metamodel.Connection;
import org.gemsjax.client.model.metamodel.MetaClass;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;

/**
 * This class is a {@link Drawable} that displays a {@link Connection} on the {@link MetaClassCanvas}.
 * A {@link Connection} is displayed between two {@link MetaClassDrawable}s.<br /> <br />
 * This class implements the {@link ResizeHandler} and the {@link MoveHandler} interface and will be registered to the 
 * connected {@link MetaClassDrawable}s. So when a {@link MetaClassDrawable} was moved / resized, this {@link ConnectionDrawable}
 * will automatically adjust itself to the {@link MetaClassDrawable}s.
 * @author Hannes Dorfmann
 *
 */
public class ConnectionDrawable implements Drawable, Moveable, Clickable, Focusable, ResizeHandler, MoveHandler {
	

	/**
	 * The {@link Connection} that is displayed with this Drawable
	 */
	private Connection connection;
	
	/**
	 * The Connection connects {@link #metaClassA} with {@link #metaClassB}.
	 */
	private MetaClassDrawable metaClassA;

	/**
	 * The Connection connects {@link #metaClassA} with {@link #metaClassB}.
	 */
	private MetaClassDrawable metaClassB;
	
	
	private ConnectionNameBoxDrawable nameBoxDrawable;

	// Handlers
	private List<FocusHandler> focusHandlers;
	private List<ClickHandler> clickHandlers;
	private List<MoveHandler> moveHandlers;
	
	
	
	/**
	 * Creates {@link Drawable} that displays a {@link Connection}.
	 * A {@link Connection} is displayed between two {@link MetaClass}es, 
	 * @param connection The {@link Connection} that is displayed with this Drawable
	 * @param metaClassA The {@link MetaClassDrawable} where the connection starts.
	 * @param metaClassB The {@link MetaClassDrawable} where the connection ends.
	 */
	public ConnectionDrawable(Connection connection, MetaClassDrawable metaClassA, MetaClassDrawable metaClassB)
	{
		this.connection = connection;
		this.metaClassA = metaClassA;
		this.metaClassB = metaClassB;
		
		this.nameBoxDrawable = new ConnectionNameBoxDrawable(connection);
		
		// Handlers
		focusHandlers = new ArrayList<FocusHandler>();
		clickHandlers = new ArrayList<ClickHandler>();
		moveHandlers = new ArrayList<MoveHandler>();
		
		setMetaClassA(metaClassA);
		setMetaClassB(metaClassB);
	}
	
	
	public ConnectionNameBoxDrawable getNameBoxDrawable()
	{
		return nameBoxDrawable;
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		context.beginPath();
		context.setFillStyle(connection.getLineColor());
		context.setLineWidth(connection.getLineSize());
		
		context.moveTo(connection.getMetaClassA().getX() + connection.getMetaClassARelativeX(), connection.getMetaClassA().getY() + connection.getMetaClassARelativeY());
		context.lineTo(connection.getNameBoxX() + connection.getANameBoxRelativeX(), connection.getNameBoxY() + connection.getANameBoxRelativeY());
		
		context.moveTo(connection.getMetaClassB().getX() + connection.getMetaClassBRelativeX(), connection.getMetaClassB().getY() + connection.getMetaClassBRelativeY());
		context.lineTo(connection.getNameBoxX() + connection.getBNameBoxRelativeX(), connection.getNameBoxY() + connection.getBNameBoxRelativeY());
	
		
		context.stroke();
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
		double y2 = connection.getMetaClassA().getY() + connection.getMetaClassARelativeY() ;
		double y1 = connection.getNameBoxY() + connection.getANameBoxRelativeY();
		
		double x2 = connection.getMetaClassA().getX() + connection.getMetaClassARelativeX();
		double x1 = connection.getNameBoxX() + connection.getANameBoxRelativeX(); 
		
		double m = (y2 -y1)/ (x2 - x1);
		
		double b = y1 - m*x1;
		
		boolean onAToNameBox = (y == m *x + b);
		
		if (onAToNameBox)
			return true;
		
		
		// For the line between MetaClass B and NameBox
		y2 = connection.getMetaClassB().getY() + connection.getMetaClassBRelativeY() ;
		y1 = connection.getNameBoxY() + connection.getBNameBoxRelativeY();
		
		x2 = connection.getMetaClassA().getX() + connection.getMetaClassARelativeX();
		x1 = connection.getNameBoxX() + connection.getANameBoxRelativeX(); 
		
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
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
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
		return metaClassA;
	}




	/**
	 * Set the other end of the {@link ConnectionDrawable} (named {@link #metaClassA}).
	 * This method register this ConnectionDrawable as {@link MoveHandler} and {@link ResizeHandler} for the
	 * new set metaClassA. It also set directly the {@link Connection}s {@link MetaClass} by calling {@link Connection#setMetaClassA(MetaClass)}.
	 * If metaClassA is set previously, this ConnectionDrawable will be unregistered as {@link ResizeHandler} / {@link MoveHandler}.
	 * @param metaClassA
	 */
	public void setMetaClassA(MetaClassDrawable metaClassA) {
		
		if (this.metaClassA!=null)
		{
			this.metaClassA.removeMoveHandler(this);
			this.metaClassA.removeResizeHandler(this);
		}
		
		this.metaClassA = metaClassA;
		connection.setMetaClassA((MetaClass)metaClassA.getDataObject());
		
		this.metaClassA.addMoveHandler(this);
		this.metaClassA.addResizeHandler(this);
	}





	public MetaClassDrawable getMetaClassB() {
		return metaClassB;
	}





	/**
	 * Set the other end of the {@link ConnectionDrawable} (named {@link #metaClassB} ).
	 * This method register this ConnectionDrawable as {@link MoveHandler} and {@link ResizeHandler} for the
	 * new set metaClassB. It also set directly the {@link Connection}s {@link MetaClass} by calling {@link Connection#setMetaClassB(MetaClass)}.
	 * If metaClassB is set previously, this ConnectionDrawable will be unregistered as {@link ResizeHandler} / {@link MoveHandler}.
	 * @param metaClassB
	 */
	public void setMetaClassB(MetaClassDrawable metaClassB) {
		
		if (this.metaClassB!=null)
		{
			this.metaClassB.removeMoveHandler(this);
			this.metaClassB.removeResizeHandler(this);
		}
		
		this.metaClassB = metaClassB;
		
		connection.setMetaClassB((MetaClass)metaClassB.getDataObject());
		
		this.metaClassB.addMoveHandler(this);
		this.metaClassB.addResizeHandler(this);
		
	}

}
