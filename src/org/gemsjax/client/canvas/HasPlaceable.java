package org.gemsjax.client.canvas;

public interface HasPlaceable {

	/**
	 * Checks if there is a AnchorPoint with the specified coordinate
	 * @param x
	 * @param y
	 * @return The {@link AnchorPoint}, which is at the coordinate x/y  or <code>null</code> if there is no {@link AnchorPoint}
	 */
	public Placeable hasPlaceableAt(double x, double y);
	
	
}
