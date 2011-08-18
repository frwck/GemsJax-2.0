package org.gemsjax.shared;

/**
 * This is a simple class for handling coordinates on the canvas
 * @author Hannes Dorfmann
 *
 */
public class Point {

	/**
	 * The unique id
	 */
	private String id;
	
	
	/**
	 * The x value
	 */
	public double x;
	
	/**
	 * The y value
	 */
	public double y;
	
	public Point (String id, double x, double y)
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
