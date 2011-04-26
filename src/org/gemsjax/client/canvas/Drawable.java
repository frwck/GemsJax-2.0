package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This interface indicates which Objects can be painted on the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public interface Drawable {
	
	//TODO is getX() and getY() needed? normally the isCoordinateOfThis should be enought.
	
	
	/**
	 * Get the x coordinate of the Object of the TOP-LEFT Corner
	 * @return
	 */
	public double getX();
	
	/**
	 * Get the y coordinate of the Object of the TOP-LEFT Corner
	 * @return
	 */
	public double getY();
	
	/**
	 * Get the z coordinate. This will be used for overlapping objects.
	 * The Z coordinate has the same meaning like the CSS z-index. That means that an object with an higher
	 * z value will be painted in the foreground, the other object will be painted overlapping in the background.
	 * @return
	 */
	public double getZ();
	
	
	/**
	 * @see #getX()
	 */
	public void setX(double x);
	
	/**
	 * @see #getY()
	 */
	public void setY(double y);
	
	/**
	 * @see #getZ()
	 */
	public void setZ(double z);
	
	
	/**
	 * Check if a x and y coordinate is in the area of this drawable.
	 * This method will be used to determine, whenever a mouse event (example click event) on the canvas should be mapped to this Drawable (e.g. via {@link Octree}) 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isCoordinateOfThis(int x, int y);
	
	
	/**
	 * This method will be called when an object should be painted in the normal way 
	 * @param context
	 */
	public void draw(Context2d context);
	
	/**
	 * Implement how the Drawable should be drawn when the mouse is over this Drawable
	 * @param context
	 */
	public void drawOnMouseOver(Context2d context);
	
	/**
	 * Implement how the Drawable should be drawn, when the Drawable has been selected (for example by clicking on it)
	 * @param context
	 */
	public void drawOnSelected(Context2d context);

	

}
