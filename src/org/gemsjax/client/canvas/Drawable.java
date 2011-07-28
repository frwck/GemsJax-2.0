package org.gemsjax.client.canvas;

import java.util.List;

import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This interface indicates which Objects can be painted on the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public interface Drawable {
	
	
	
	/**
	 * Check if a x and y coordinate is in the area of this drawable.
	 * This method will be used to determine, whenever a mouse event (example click event) on the canvas should be mapped to this Drawable (e.g. via {@link Octree}) 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasCoordinate(double x, double y);
	
	
	public double getZIndex();
	
	/**
	 * This method will be called when an object should be painted on the canvas
	 * @param context
	 */
	public void draw(Context2d context);
		
	/**
	 * Check if this Drawable has a {@link ResizeArea}, which has the x-y coordinate as part of this {@link ResizeArea}
	 * @param x
	 * @param y
	 * @return The ResizeArea which is at the position or null if there is not a {@link ResizeArea}
	 */
	public ResizeArea isResizerAreaAt(double x, double y);
	
	
}
