package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaInheritance;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.uibinder.rebind.model.OwnerClass;

public class MetaInheritanceDrawable implements Drawable, Focusable{

	
	private MetaInheritance inheritance;
	
	private HashMap<AnchorPoint, Anchor> anchorMap;
	private List<FocusHandler> focusHandlers;
	
	private Anchor ownerAnchor;
	private Anchor superAnchor;
	
	private MetaClassDrawable ownerDrawable;
	private MetaClassDrawable superDrawable;
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line 
	 */
	private double mouseOffSet = 7;
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line, but the line is not a linear function, 
	 * but a vertical line instead
	 */
	private double verticalLineXOffset=7;
	
	
	private double triangleLineWidth = 10;
	
	public MetaInheritanceDrawable(MetaInheritance inheritance, MetaClassDrawable ownerDrawable, MetaClassDrawable superDrawable)
	{
		this.inheritance = inheritance;
		this.ownerDrawable = ownerDrawable;
		this.superDrawable = superDrawable;
		
		focusHandlers = new ArrayList<FocusHandler>();
		// generate the Anchors
		anchorMap = new HashMap<AnchorPoint, Anchor>();
		
		ownerAnchor = new Anchor(inheritance.getOwnerClassRelativeAnchorPoint(), ownerDrawable);
		superAnchor = new Anchor(inheritance.getSuperClassRelativeAnchorPoint(), superDrawable);
		
		anchorMap.put(inheritance.getOwnerClassRelativeAnchorPoint(), ownerAnchor);
		anchorMap.put(inheritance.getSuperClassRelativeAnchorPoint(), superAnchor);
		
		AnchorPoint current = ownerAnchor.getNextAnchorPoint();
		
		while (current!=superAnchor.getAnchorPoint())
		{
			anchorMap.put(current, new Anchor(current, null));
			current = current.getNextAnchorPoint();
		}
		
	}
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		drawLine(context);
		
		if (isSelected())
			drawSelected(context);
		
	}
	
	
	private void drawSelected(Context2d context)
	{
		AnchorPoint current = ownerAnchor.getAnchorPoint();
		Anchor a;
		
		while (current!=ownerAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			a.draw(context);
			
			current = current.getNextAnchorPoint();
		}
		
		ownerAnchor.draw(context);
		
	}
	
	private void drawLine(Context2d context)
	{
		context.save();
		context.setStrokeStyle(inheritance.getLineColor());
		context.setFillStyle("white");
		context.setLineWidth(inheritance.getLineSize());
		
		
		
		double prevX , prevY;
		Anchor a;
		
		prevX = ownerDrawable.getX() + ownerAnchor.getX();
		prevY = ownerDrawable.getY() + ownerAnchor.getY();
		
		AnchorPoint current = ownerAnchor.getAnchorPoint().getNextAnchorPoint();
		
		// Draw line between the ownerDrawable and superClass
		while (current!=superAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			
			context.moveTo(prevX, prevY);
			context.lineTo( a.getX(), a.getY());
			
			prevX = a.getX();
			prevY = a.getY();
			current = current.getNextAnchorPoint();
		}
		
		
		// Draw the triangle
		
		
		
		double triangleHeight = Math.sqrt(Math.pow(triangleLineWidth,2) - Math.pow(triangleLineWidth/2, 2));
		
		double sx = superDrawable.getX() + superAnchor.getX();
		double sy = superDrawable.getY()+ superAnchor.getY();
		
		//context.beginPath();
		
		switch (superAnchor.getPlaceableDestination().getCoordinatesBorderDirection( sx, sy))
		{
			case BOTTOM: 	context.moveTo(sx, sy);
							context.lineTo(sx - triangleLineWidth/2, sy+triangleHeight);
							context.moveTo(sx - triangleLineWidth/2, sy+triangleHeight);
							context.lineTo(sx + triangleLineWidth/2, sy+triangleHeight);
							context.moveTo(sx - triangleLineWidth/2, sy+triangleHeight);
							context.lineTo(sx, sy);
							
							// Line to triangle
							context.moveTo(prevX, prevY);
							context.lineTo(sx,sy+triangleHeight);
							
							break;
							
			case LEFT: 		context.moveTo(sx, sy);
							context.lineTo(sx + triangleHeight, sy-triangleLineWidth/2);
							context.moveTo(sx + triangleHeight, sy-triangleLineWidth/2);
							context.lineTo(sx + triangleHeight, sy + triangleLineWidth/2);
							context.moveTo(sx + triangleHeight, sy + triangleLineWidth/2);
							context.lineTo(sx, sy);
							
							//Line to triangle
							context.moveTo(prevX, prevY);
							context.lineTo(sx + triangleHeight, sy);
							break;

							
							
			case RIGHT:		context.moveTo(sx, sy);
							context.lineTo(sx - triangleHeight, sy - triangleLineWidth/2);
							context.moveTo(sx - triangleHeight, sy - triangleLineWidth/2);
							context.lineTo(sx - triangleHeight, sy + triangleLineWidth/2);
							context.moveTo(sx - triangleHeight, sy + triangleLineWidth/2);
							context.lineTo(sx, sy);
							
							//Line to triangle
							context.moveTo(prevX, prevY);
							context.lineTo(sx - triangleHeight, sy);
							break;
			
							
			case NOWHERE:
			case TOP:
			default:		context.moveTo(sx, sy);
							context.lineTo(sx - triangleLineWidth/2, sy-triangleHeight);
							context.moveTo(sx - triangleLineWidth/2, sy-triangleHeight);
							context.lineTo(sx + triangleLineWidth/2, sy-triangleHeight);
							context.moveTo(sx + triangleLineWidth/2, sy-triangleHeight);
							context.lineTo(sx, sy);
							
							// Line to triangle
							context.moveTo(prevX, prevY);
							context.lineTo(sx,sy-triangleHeight);
							break;
		}
		
		//context.closePath();
		context.stroke();
		//context.fill();

		context.restore();
	}
	
	
	@Override
	public Object getDataObject() {
		return inheritance;
	}

	@Override
	public double getZIndex() {
		return inheritance.getZIndex();
	}

	@Override
	public boolean hasCoordinate(double x, double y) {
		
		double currentX, currentY, nextX, nextY, m, b, t;
		
		Anchor currentAnchor = ownerAnchor;
		
		Anchor nextAnchor = null;
		
		AnchorPoint currentPoint = ownerAnchor.getAnchorPoint();
		
		
		while (currentPoint != superAnchor.getAnchorPoint())
		{
			currentAnchor = anchorMap.get(currentPoint);
			nextAnchor = anchorMap.get(currentPoint.getNextAnchorPoint());
			
			// if the current is the sourceAnchorPoint, which is the start point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (currentAnchor == ownerAnchor)
			{
				currentX = ownerDrawable.getX() + ownerAnchor.getX();
				currentY = ownerDrawable.getY() + ownerAnchor.getY();
			}
			else
			{
				currentX = currentAnchor.getX();
				currentY = currentAnchor.getY();
			}
			
			
			
			// if the next is the sourceConnectionBoxAnchorPoint, which is the end point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (nextAnchor == superAnchor)
			{
				// TODO line is at triangle center
				nextX = ownerDrawable.getX() + superAnchor.getX();
				nextY = ownerDrawable.getY() + superAnchor.getY();
			}
			else
			{
				nextX = nextAnchor.getX();
				nextY = nextAnchor.getY();
			}
			
			
			
			// special case that current and next have the same x coordinate, so there is a vertical line instead of a linear function
			if (Math.abs(currentX-x)<=verticalLineXOffset && Math.abs(nextX-x)<=verticalLineXOffset)
			{
				if (currentY<nextY && isBetween(currentY, nextY, y))
					return true;
				else
				if (currentY>=nextY && isBetween(nextY, currentY, y))
					return true;
			}
			
			
			// calculate the slopetriangle 
			m = (currentY - nextY) / (currentX - nextX);
			
			// calculate x axis deferral	b = y - m*x
			b = currentY - m * currentX;
			
			// temp variable to calculate  m * x + b = t
			t = m * x + b;
			
			if (Math.abs(t - y)<=mouseOffSet)
				return true;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}
		
		
		return false;
		
	}

	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean isSelected() {
		return inheritance.isSelected();
	}
	
	public Anchor getOwnerClassAnchor()
	{
		return ownerAnchor;
	}
	
	public Anchor getSuperClassAnchor()
	{
		return superAnchor;
	}
	
	
	public Anchor getAnchor(AnchorPoint point)
	{
		return anchorMap.get(point);
	}




	public MetaClassDrawable getOwnerDrawable() {
		return ownerDrawable;
	}




	public void setOwnerDrawable(MetaClassDrawable ownerDrawable) {
		this.ownerDrawable = ownerDrawable;
	}




	public MetaClassDrawable getSuperDrawable() {
		return superDrawable;
	}




	public void setSuperDrawable(MetaClassDrawable superDrawable) {
		this.superDrawable = superDrawable;
	}




	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				focusHandlers.add(focusHandlers.size(), handler);
			else
				focusHandlers.add(0,handler);
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		
		boolean delivered = false;
		
		for (FocusHandler h : focusHandlers)
		{
			h.onFocusEvent(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeFocusHandler(FocusHandler handler) {
		focusHandlers.remove(handler);
	}
	
	

}
