package org.gemsjax.client.canvas;

import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;

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
	
	/**
	 * Has the same meaning as the CSS z indexd
	 * @return
	 */
	public double getZIndex();
	
	
	/**
	 * This method will be called when an object should be painted on the canvas
	 * @param context
	 */
	public void draw(Context2d context);
	
	/**
	 * @return True if this Drawable is selected
	 */
	public boolean isSelected();
	
	/**
	 * Get the data object that is displayed with this drawable on the canvas.
	 * This is normaly a {@link MetaClassImpl}, {@link MetaConnectionImpl}, {@link InheritanceRelation}, ...
	 * @return
	 */
	public Object getDataObject();
	
	
}
