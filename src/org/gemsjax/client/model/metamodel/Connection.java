package org.gemsjax.client.model.metamodel;

import org.gemsjax.client.canvas.ConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.editor.MetaModelCanvas;

/**
 * A Connection is used to represent a connection like association between two {@link MetaClass}es
 * A Connection connects {@link #metaClassA} with {@link #metaClassB} and vice versa. 
 * So there is not a start/source or end/target. {@link #metaClassA} and {@link #metaClassB} are on a par.
 * @author Hannes Dorfmann
 *
 */
public class Connection {
	
	/**
	 * One end of the {@link Connection}
	 */
	private MetaClass metaClassA;
	/**
	 * The other end of the {@link Connection}
	 */
	private MetaClass metaClassB;
	
	/**
	 * The X coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassA}.
	 * This coordinate is relative to the {@link #metaClassA} {@link MetaClassDrawable} object on the {@link MetaModelCanvas}.
	 * That means, that the {@link MetaClass#getX()} is the relative 0 coordinate on the x axis.
	 * Example:
	 * If the {@link MetaClass#getX()} has the absolute coordinate 10 and {@link #aRelativeX} is 5, so the
	 * absolute coordinate on the {@link MetaModelCanvas} is 15 and can be computed by add {@link MetaClass#getX()} to aRelativeX
	 *@see #getMetaClassARelativeX()
	 */
	private double aRelativeX;
	
	/**
	 * The Y coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassA}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassARelativeY()
	 */
	private double aRelativeY;
	
	/**
	 * The X coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassB}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassBRelativeX()
	 */
	private double bRelativeX;
	
	/**
	 * The Y coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassB}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassBRelativeY()
	 */
	private double bRelativeY;
	
	
	/**
	 * Creates a new connection between the MetaClass a (stored in the field {@link #metaClassA}) and the {@link MetaClass} b (stored in the field {@link #metaClassB}).
	 * @param a
	 * @param b
	 */
	public Connection(MetaClass a, MetaClass b)
	{
		this.metaClassA = a;
		this.metaClassB = b;
		
	}


	
	public MetaClass getMetaClassA() {
		return metaClassA;
	}


	public void setMetaClassA(MetaClass metaClassA) {
		this.metaClassA = metaClassA;
	}


	public MetaClass getMetaClassB() {
		return metaClassB;
	}


	public void setMetaClassB(MetaClass metaClassB) {
		this.metaClassB = metaClassB;
	}


	/**
	 * @see #aRelativeX
	 * @return
	 */
	public double getMetaClassARelativeX() {
		return aRelativeX;
	}


	/**
	 * @see #aRelativeX
	 * @param aRelativeX
	 */
	public void setMetaClassARelativeX(double aRelativeX) {
		this.aRelativeX = aRelativeX;
	}


	/**
	 * @see #aRelativeY
	 * @return
	 */
	public double getMetaClassARelativeY() {
		return aRelativeY;
	}

	/**
	 * @see #aRelativeY
	 * @param aRelativeY
	 */
	public void setMetaClassARelativeY(double aRelativeY) {
		this.aRelativeY = aRelativeY;
	}


	/**
	 * @see #bRelativeX
	 * @return
	 */
	public double getMetaClassBRelativeX() {
		return bRelativeX;
	}

	/**
	 * @see #bRelativeX
	 * @param bRelativeX
	 */
	public void setMetaClassBelativeX(double bRelativeX) {
		this.bRelativeX = bRelativeX;
	}


	/**
	 * @see #bRelativeY
	 * @return
	 */
	public double getMetaClassBRelativeY() {
		return bRelativeY;
	}


	/**
	 * @see #bRelativeY
	 * @param bRelativeY
	 */
	public void setMetaClassBRelativeY(double bRelativeY) {
		this.bRelativeY = bRelativeY;
	}



}
