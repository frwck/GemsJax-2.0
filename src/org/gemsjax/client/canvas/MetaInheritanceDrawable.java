package org.gemsjax.client.canvas;

import java.util.HashMap;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaInheritance;

import com.google.gwt.canvas.dom.client.Context2d;

public class MetaInheritanceDrawable implements Drawable{

	
	private MetaInheritance inheritance;
	
	private HashMap<AnchorPoint, Anchor> anchorMap;
	
	
	private Anchor ownerAnchor;
	private Anchor superAnchor;
	
	private MetaClassDrawable ownerDrawable;
	private MetaClassDrawable superDrawable;
	
	public MetaInheritanceDrawable(MetaInheritance inheritance, MetaClassDrawable ownerDrawable, MetaClassDrawable superDrawable)
	{
		this.inheritance = inheritance;
		this.ownerDrawable = ownerDrawable;
		this.superDrawable = superDrawable;
		
		ownerAnchor = new Anchor(inheritance.getClassRelativeAnchorPoint(), ownerDrawable);
		superAnchor = new Anchor(inheritance.getSuperClassRelativeAnchorPoint(), superDrawable);
		
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getDataObject() {
		return inheritance;
	}

	@Override
	public double getZIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCoordinate(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

}
