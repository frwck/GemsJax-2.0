package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;


public class SearchRaster {
	
	private class RasterNode
	{
		/**
		 *  A List to handle the z index of the drawables. This list is always sorted by the z index from greater to less.
		 */
		private List<Drawable> drawables;
		
		public RasterNode()
		{
			drawables = new LinkedList<Drawable>();
		}
		
		public void add(Drawable drawable)
		{
			for (Drawable d: drawables)
			{
				if (drawable.getZ()>= d.getZ())
				{
					drawables.add(drawable);
					return;
				}
			}
			
			drawables.add(drawable);
		}
		
	}

	// TODO A tree would increase the performance
	private List<RasterNode> elements;
	public SearchRaster()
	{
		elements = new LinkedList<RasterNode>();
	}
	
	
	
	
	public Drawable getDrawableAt(double x, double y)
	{
		// TODO continue here
		return null;
	}
}
