package org.gemsjax.shared;

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
	

}
