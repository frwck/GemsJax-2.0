package org.gemsjax.client.canvas;

public interface HasAnchor {

	/**
	 * Checks if there is a AnchorPoint with the specified coordinate
	 * @param x
	 * @param y
	 * @return The {@link AnchorPoint}, which is at the coordinate x/y  or <code>null</code> if there is no {@link AnchorPoint}
	 */
	public AnchorPoint hasAnchorAt(double x, double y);
	
	/**
	 *  Highlight the destination where the AnchorPoint can be moved to in a graphical way on the canvas
	 */
	public void highlightAnchorPointDestinationArea();
	
	/**
	 * Checks and Determine, wherever the {@link AnchorPoint} can be moved to
	 * @param x
	 * @param y
	 * @return true, if the Anchor can be  moved to this position, else false. Moved means in this case placed (for example by mouse release) at the position x/y
	 */
	public boolean canAnchorPointBeMovedTo(double x, double y);
	
}
