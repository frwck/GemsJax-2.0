package org.gemsjax.client.canvas;

import org.gemsjax.client.admin.model.metamodel.Connection;
import org.gemsjax.client.admin.model.metamodel.MetaClass;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.Context2d;

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
		
		metaClassA.addMoveHandler(this);
		metaClassA.addResizeHandler(this);
		metaClassB.addMoveHandler(this);
		metaClassB.addResizeHandler(this);
		
	}
	
	
	
	
	
	@Override
	public void draw(Context2d context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getZIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCoordinate(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void addMoveHandler(MoveHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fireMoveEvent(MoveEvent event) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fireClickEvent(ClickEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFocusHandler(FocusHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeFocusHandler(FocusHandler handler) {
		// TODO Auto-generated method stub
		
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
	 * new set metaClassA. It also set directly the {@link Connection}s {@link MetaClass} by calling {@link Connection#setSource(MetaClass)}.
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
		connection.setSource((MetaClass)metaClassA.getDataObject());
		
		this.metaClassA.addMoveHandler(this);
		this.metaClassA.addResizeHandler(this);
	}





	public MetaClassDrawable getMetaClassB() {
		return metaClassB;
	}





	/**
	 * Set the other end of the {@link ConnectionDrawable} (named {@link #metaClassB} ).
	 * This method register this ConnectionDrawable as {@link MoveHandler} and {@link ResizeHandler} for the
	 * new set metaClassB. It also set directly the {@link Connection}s {@link MetaClass} by calling {@link Connection#setTarget(MetaClass)}.
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
		
		connection.setTarget((MetaClass)metaClassB.getDataObject());
		
		this.metaClassB.addMoveHandler(this);
		this.metaClassB.addResizeHandler(this);
		
	}

}
