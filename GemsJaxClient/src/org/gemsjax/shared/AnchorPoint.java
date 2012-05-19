package org.gemsjax.shared;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;


/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class AnchorPoint extends Point implements Serializable{

	/**
	 * The unique id
	 */
	private String id;
	
	private AnchorPoint nextAnchorPoint;
	
	/**
	 * Used for serialisation only
	 */
	private String nextAnchorPointId;
	
	public AnchorPoint (String id, double x, double y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public AnchorPoint(){}
	
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
		this.nextAnchorPointId = nextAnchorPoint.getID();
	}

	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		x = a.serialize("x", x).value;
		y = a.serialize("y", y).value;
		nextAnchorPointId = a.serialize("nextAnchorPointId", nextAnchorPointId).value;
	}

	/**
	 * For serialisation mapping only
	 * @return
	 */
	public String getNextAnchorPointId() {
		return nextAnchorPointId;
	}

	

}
