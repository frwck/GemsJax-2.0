package org.gemsjax.client.canvas;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModelElement;

import org.gemsjax.client.admin.exception.DoubleLimitException;

/**
 * The {@link DrawableStorage} is part of the {@link BufferedCanvas}.
 * This class manages (Z-index, check for mouseEvenets {@link #getDrawableAt(double, double)}, etv.) the {@link Drawable}s that are painted with the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public class DrawableStorage {
	

	// TODO Maybe a tree structure would increase the performance
	/**
	 * A list with all Drawables that are displayed on this canvas
	 */
	private List<Drawable> elements;
	
	/**
	 * This map stores all {@link MetaModelElement}s and the corresponding {@link Drawable}
	 */
	private Map <Object, Drawable> elementsMap;
	
	private double nextZIndex;
	
	public DrawableStorage()
	{
		elements = new LinkedList<Drawable>();
		elementsMap = new HashMap<Object, Drawable>();
		
		nextZIndex = -1000000;
	}
	
	
	/**
	 * Get the {@link Drawable} of a {@link MetaModelElement}.
	 * Normally this  method is used, to get the corresponding {@link Drawable} of a MetaModelElement like {@link MetaClass}, {@link MetaConnection} etc.
	 * @param o
	 * @return The corresponding {@link Drawable} to the object o or null, if no {@link Drawable}, which displays the object o, is currently on the canvas.
	 */
	public Drawable getDrawableOf(Object o)
	{
		return elementsMap.get(o);
	}
	
	/**
	 * Get the element (with the highest Z index that is displayed at the x-y coordinate of the canvas
	 * @param x
	 * @param y
	 * @return The element which is at the x-y position and has the highest Z index, or null if there is no element at this position
	 */
	public synchronized Drawable getDrawableAt(double x, double y)
	{
		Drawable result = null;
		
		for (Drawable d: elements)
		{
			if (d.hasCoordinate(x, y))
			{
				if (result== null || d.getZIndex()>=result.getZIndex())
					result = d;
			}
				
		}
		
		
		return result;
		
	}
	
	/**
	 * Add a {@link Drawable}.
	 * The internal list will set the list according the z index. So the list is sorted by the z index, from less to great.
	 * This has the advantage, that the list is in the correct order for the Canvas to draw the elements.
	 * @param d
	 * @throws DoubleLimitException 
	 */
	public void add(Drawable d) throws DoubleLimitException
	{
		// TODO insert at the correct position, according the z index
		elements.add(elements.size(), d);
		elementsMap.put(d.getDataObject(), d);
	}
	
	
	
	/**
	 * Remove a {@link Drawable}
	 * @param d
	 */
	public void remove(Drawable d)
	{
		elements.remove(d);
		elementsMap.remove( d.getDataObject());
	}
	
	/**
	 * Get all {@link Drawable}s in the order in wich they should be painted on the Canvas
	 * @return@Override
	public List<MouseOverHandler> getMouseOverHandlers() {
		return mouseOverHandlers;
	}
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
