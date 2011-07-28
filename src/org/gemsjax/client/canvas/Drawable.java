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
public interface Drawable extends Clickable, Focusable{
	
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
	public boolean isMoveable();
	
	/**
	 * Can the Drawable be resized?
	 * @return
	 */
	public boolean isResizeable();
	
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
	 * Is the Mouse over this drawable?
	 * @return
	 */
	public boolean isMouseOver();
	
	
	/**
	 * Set if the mouse is Over this object
	 * @param mouseOver
	 */
	public void setMouseOver(boolean mouseOver);
	
	
	
	
	/**
	 * Check if a x and y coordinate is in the area of this drawable.
	 * This method will be used to determine, whenever a mouse event (example click event) on the canvas should be mapped to this Drawable (e.g. via {@link Octree}) 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasCoordinate(double x, double y);
	
	
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
	 * Add a {@link ResizeHandler}
	 * @param resizeHandler
	 */
	public void addResizeHandler(ResizeHandler resizeHandler);
	
	/**
	 * remove a {@link ResizeHandler}
	 * @param resizeHandler
	 */
	public void removeResizeHandler(ResizeHandler resizeHandler);
	
	/**
	 * Add a {@link MoveHandler}
	 * @param moveHandler
	 */
	public void addMoveHandler(MoveHandler moveHandler);
	
	/**
	 * Remove a {@link MoveHandler}
	 * @param moveHandler
	 */
	public void removeMoveHandler(MoveHandler moveHandler);
	
	/**
	 * Add a {@link MouseOverHandler}
	 * @param mouseOverHandler
	 */
	public void addMouseOverHandler(MouseOverHandler mouseOverHandler);
	
	/**
	 * Remove a {@link MouseOverHandler}
	 * @param mouseOverHandler
	 */
	public void removeMouseOverHandler(MouseOverHandler mouseOverHandler);
	
	/**
	 * Get the list with all {@link MoveHandler}s
	 */
	public List<MoveHandler> getMoveHandlers();

	/**
	 * Get the list with all {@link ResizeHandler}s
	 */
	public List<ResizeHandler> getResizeHandlers();
	
	
	/**
	 * Get a list with all {@link MouseOverHandler}s
	 * @return
	 */
	public List<MouseOverHandler> getMouseOverHandlers();
	
	
	/**
	 * Check if this Drawable has a {@link ResizeArea}, which has the x-y coordinate as part of this {@link ResizeArea}
	 * @param x
	 * @param y
	 * @return The ResizeArea which is at the position or null if there is not a {@link ResizeArea}
	 */
	public ResizeArea isResizerAreaAt(double x, double y);
	
	
}
