package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This interface indicates which Objects can be painted on the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public interface Drawable {
	
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
	 * Can the Drawable be moved
	 * @return
	 */
	public boolean canBeMoved();
	
	/**
	 * Can the Drawable be resized?
	 * @return
	 */
	public boolean canBeResized();
	
	/**
	 * Set the minimum Width of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum Width
	 * @param minWidth
	 */
	public void setMinWidth(double minWidth);
	
	/**
	 * get the minimum Width of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum Width
	 */
	 
	public double getMinWidth();
	
	
	/**
	 * Set the minimum height of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum height
	 * @param minWidth
	 */
	public void setMinHeight(double minWidth);
	
	/**
	 * Get the minimum height of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum height
	 */
	public double getMinHeight();
	
	
	/**
	 * Get the width
	 * @return
	 */
	public double getWidth();
	
	/**
	 * Get the Height
	 * @return
	 */
	public double getHeight();
	
	public void setWidth(double width);
	
	public void setHeight(double height);
	
	
	/**
	 * Set this drawable as selected, so the {@link #drawOnSelected(Context2d)} method should be called
	 * @param selected
	 */
	public void setSelected(boolean selected);
	
	
	/**
	 * If this is marked as selected, draw it in another way by calling the {@link #drawOnSelected(Context2d)} method.
	 * @return
	 */
	public boolean isSelected();
	
	
	/**
	 * Check if a x and y coordinate is in the area of this drawable.
	 * This method will be used to determine, whenever a mouse event (example click event) on the canvas should be mapped to this Drawable (e.g. via {@link Octree}) 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isCoordinateOfThis(double x, double y);
	
	
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
	
	
	/**
	 * Will be called from the {@link BufferedCanvas}, if the object has been resized and {@link #canBeResized()} == true
	 * @param newWidth
	 * @param newHeight
	 */
	public void onResize(double newWidth, double newHeight);
	
	/**
	 *  Will be called from the {@link BufferedCanvas}, if the object has been moved and {@link #canBeMoved()} == true
	 * @param newX
	 * @param newY
	 */
	public void onMove(double newX, double newY);
	

}
