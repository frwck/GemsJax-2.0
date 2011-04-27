package org.gemsjax.client.canvas;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.exception.DoubleLimitException;

/**
 * The {@link DrawableStorage} is part of the {@link BufferedCanvas}.
 * This class manages (Z-index, check for mouseEvenets {@link #getDrawableAt(double, double)}, etv.) the {@link Drawable}s that are painted with the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public class DrawableStorage {
	

	// TODO Maybe a tree structure would increase the performance
	private List<Drawable> elements;
	private Drawable getDrawableAtResult;
	private double nextZIndex;
	
	public DrawableStorage()
	{
		elements = new LinkedList<Drawable>();
		getDrawableAtResult = null;
		nextZIndex = -1000000;
	}
	
	
	
	
	
	/**
	 * Get the element (with the highest Z index that is displayed at the x-y coordinate of the canvas
	 * @param x
	 * @param y
	 * @return The element which is at the x-y position and has the highest Z index, or null if there is no element at this position
	 */
	public synchronized Drawable getDrawableAt(double x, double y)
	{
		getDrawableAtResult = null;
		
		for (Drawable d: elements)
		{
			if (d.isCoordinateOfThis(x, y))
			{
				if (getDrawableAtResult== null || d.getZ()>=getDrawableAtResult.getZ())
					getDrawableAtResult = d;
			}
				
		}
		
		
		return getDrawableAtResult;
		
	}
	
	/**
	 * Add a {@link Drawable}.
	 * The internal list will set the list according the z index. So the list is sorted by the z index, from less to great.
	 * This has the advantage, that the list is in the correct order for tje Canvas to draw the elements.
	 * @param d
	 * @throws DoubleLimitException 
	 */
	public void add(Drawable d) throws DoubleLimitException
	{
		d.setZ(getNextZIndex());
		elements.add(elements.size(), d);
	}
	
	
	
	/**
	 * remove a {@link Drawable}
	 * @param d
	 */
	public void remove(Drawable d)
	{
		elements.remove(d);
	}
	
	/**
	 * Get all {@link Drawable}s in the order in wich they should be painted on the Canvas
	 * @return
	 */
	public Collection<Drawable> getAllElements()
	{
		return elements;
	}
	
	
	/**
	 * Get the next z index and increment 
	 * @return
	 * @throws DoubleLimitException
	 */
	public double getNextZIndex() throws DoubleLimitException
	{
		if (nextZIndex>DoubleLimitException.MAX)
			throw new DoubleLimitException("Overflow with the next incrementation. CurrentValue: "+nextZIndex);
		
		nextZIndex+=0.01;
		return nextZIndex;
		
		
	}
}
