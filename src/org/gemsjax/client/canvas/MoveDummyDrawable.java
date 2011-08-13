package org.gemsjax.client.canvas;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModelElement;

import com.google.gwt.canvas.dom.client.Context2d;

public class MoveDummyDrawable implements Drawable{
	
	private double x, y, width, height, iconWidth, iconHeight;
	
	private String name, iconURL;
	private String gradientStartColor;
	private String gradientEndColor;
	
	private Drawable originalDrawable;
	
	private double alpha = 0.6;
	
	public MoveDummyDrawable()
	{	
	}
	
	
	public void setOriginalDrawable(Drawable original)
	{
		this.originalDrawable = original;
		
		if (originalDrawable instanceof MetaClassDrawable)
		{
			x = ((MetaClass) (originalDrawable.getDataObject())).getX();
			y = ((MetaClass) (originalDrawable.getDataObject())).getY();
		}
		else
		if (originalDrawable instanceof MetaConnectionBoxDrawable)
		{
			x = ((MetaConnection) (originalDrawable.getDataObject())).getConnectionBoxX();
			y = ((MetaConnection) (originalDrawable.getDataObject())).getConnectionBoxY();
		}
	}

	@Override
	public void draw(Context2d context) {
		context.save();
		context.setGlobalAlpha(alpha);
		
		if (originalDrawable instanceof MetaClassDrawable)
			drawMetaClassDummy(context);
		else
			if (originalDrawable instanceof MetaConnectionBoxDrawable)
			drawMetaConnectionBoxDummy(context);
		
		context.restore();
	}
	
	private void drawMetaClassDummy(Context2d context)
	{
		MetaClass mc = (MetaClass) originalDrawable.getDataObject();
		
		double x = this.x, y = this.y;
		
		if (mc.getIconURL()!=null)
		{
			
		}
		
	}
	
	private void drawMetaConnectionBoxDummy(Context2d context)
	{
		
	}

	@Override
	public Object getDataObject() {
		// TODO Auto-generated method stub
		return null;
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
