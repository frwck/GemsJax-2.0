package org.gemsjax.shared;

import com.google.gwt.user.client.ui.Anchor;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class AnchorPoint extends Point{

	/**
	 * The unique id
	 */
	private String id;
	
	private AnchorPoint nextAnchorPoint;
	
	
	public AnchorPoint (String id, double x, double y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the unique id
	 * @return
	 */
	public String getID()
	{
		return id;
	}

	/**
	 * This is the reference to the next Anchor Point.
	 * According this reference the Canvas is able to draw a line from this {@link AnchorPoint} to the 
	 * next {@link AnchorPoint}. So a linked list is created to draw a connection line on the canvas.
	 * @return
	 */
	public AnchorPoint getNextAnchorPoint() {
		return nextAnchorPoint;
	}

	public void setNextAnchorPoint(AnchorPoint nextAnchorPoint) {
		this.nextAnchorPoint = nextAnchorPoint;
	}
	
	
	

}
