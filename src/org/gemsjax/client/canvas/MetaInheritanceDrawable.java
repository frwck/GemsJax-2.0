package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaInheritance;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * This class is a {@link Drawable} that dispalyes an inheritance relation on the canvas.
 * @author Hannes Dorfmann
 *
 */
public class MetaInheritanceDrawable implements Drawable, Focusable, HasPlaceable{

	
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
	
	
	private double triangleLineWidth = 20;
	
	
	/**
	 * Is the {@link Point} where the line touches the triangle which displays the inheritance super class
	 */
	private Point lineToTrianglePoint;
	
	/**
	 * Stores the 3 {@link Point}s of the inheritance triangle which displays the inheritance super class.
	 * These 3 points will be calculates in the {@link #drawLine(Context2d)} method and is used in the {@link #hasCoordinate(double, double)}
	 * to determine if a mouse interaction is on the triangle and so part of the {@link MetaInheritanceDrawable}.
	 */
	private Point trianglePoints[];
	
	
	public MetaInheritanceDrawable(MetaInheritance inheritance, MetaClassDrawable ownerDrawable, MetaClassDrawable superDrawable)
	{
		this.inheritance = inheritance;
		this.ownerDrawable = ownerDrawable;
		this.superDrawable = superDrawable;
		
		lineToTrianglePoint = new Point();
		trianglePoints = new Point[3];
		trianglePoints[0] = new Point();
		trianglePoints[1] = new Point();
		trianglePoints[2] = new Point();
		
		
		focusHandlers = new ArrayList<FocusHandler>();
		// generate the Anchors
		anchorMap = new HashMap<AnchorPoint, Anchor>();
		
		ownerAnchor = new Anchor(inheritance.getOwnerClassRelativeAnchorPoint(), ownerDrawable);
		superAnchor = new Anchor(inheritance.getSuperClassRelativeAnchorPoint(), superDrawable);
		
		this.ownerDrawable.dockAnchor(ownerAnchor);
		this.superDrawable.dockAnchor(superAnchor);
		
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
		
		while (current!=superAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			a.draw(context);
			
			current = current.getNextAnchorPoint();
		}
		
		superAnchor.draw(context);
		
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
		
		context.beginPath();
		context.moveTo(prevX, prevY);
		
		// Draw line between the ownerDrawable and superClass
		while (current!=superAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			
			context.lineTo( a.getX(), a.getY());
			
			prevX = a.getX();
			prevY = a.getY();
			current = current.getNextAnchorPoint();
		}
		
		
		context.stroke();
		
		// Draw the triangletrianglePoints[0] = new Point();
		
		
		
		
		double triangleHeight = Math.sqrt(Math.pow(triangleLineWidth,2) - Math.pow(triangleLineWidth/2, 2));
		
		double sx = superDrawable.getX() + superAnchor.getX();
		double sy = superDrawable.getY() + superAnchor.getY();
		
		
		
		switch (superAnchor.getPlaceableDestination().getCoordinatesBorderDirection( sx, sy))
		{
							
			case RIGHT: 	
							context.beginPath();
							context.moveTo(sx, sy);
							trianglePoints[0].x = sx;
							trianglePoints[0].y = sy;
							
							context.lineTo(sx + triangleHeight, sy-triangleLineWidth/2);
							trianglePoints[1].x = sx + triangleHeight;
							trianglePoints[1].y = sy-triangleLineWidth/2;
							
							context.lineTo(sx + triangleHeight, sy + triangleLineWidth/2);
							trianglePoints[2].x = sx + triangleHeight;
							trianglePoints[2].y = sy + triangleLineWidth/2;
							
							context.lineTo(sx, sy);
							
							context.closePath();
							context.stroke();
							context.fill();
							
							//Line to triangle
							context.beginPath();
							context.moveTo(prevX, prevY);
							context.lineTo(sx + triangleHeight, sy);
							lineToTrianglePoint.x = sx+triangleHeight;
							lineToTrianglePoint.y = sy;
							
							context.stroke();
							
							break;

							
							
			case LEFT:		context.beginPath();
							context.moveTo(sx, sy);
							trianglePoints[0].x = sx;
							trianglePoints[0].y = sy;
							
							context.lineTo(sx - triangleHeight, sy - triangleLineWidth/2);
							trianglePoints[1].x = sx - triangleHeight;
							trianglePoints[1].y = sy - triangleLineWidth/2;
							
							context.lineTo(sx - triangleHeight, sy + triangleLineWidth/2);
							trianglePoints[2].x = sx - triangleHeight;
							trianglePoints[2].y = sy + triangleLineWidth/2;
							
							context.lineTo(sx, sy);
							
							context.closePath();
							context.stroke();
							context.fill();
							
							//Line to triangle
							context.beginPath();
							context.moveTo(prevX, prevY);
							context.lineTo(sx - triangleHeight, sy);
							
							context.stroke();
							
							lineToTrianglePoint.x = sx-triangleHeight;
							lineToTrianglePoint.y = sy;
							
							break;
			
							
			case TOP:
							context.beginPath();
							context.moveTo(sx, sy);
							trianglePoints[0].x = sx;
							trianglePoints[0].y = sy;
							
							context.lineTo(sx - triangleLineWidth/2, sy-triangleHeight);
							trianglePoints[1].x = sx - triangleLineWidth/2;
							trianglePoints[1].y = sy-triangleHeight;
							
							context.lineTo(sx + triangleLineWidth/2, sy-triangleHeight);
							trianglePoints[2].x = sx + triangleLineWidth/2;
							trianglePoints[2].y = sy-triangleHeight;
							
							context.lineTo(sx, sy);
							
							context.closePath();
							context.stroke();
							context.fill();
							
							// Line to triangle
							context.beginPath();
							context.moveTo(prevX, prevY);
							context.lineTo(sx,sy-triangleHeight);
							
							context.stroke();
							
							lineToTrianglePoint.x = sx;
							lineToTrianglePoint.y = sy-triangleHeight;
							break;
							
							
			case NOWHERE:				
			case BOTTOM: 	
			default:
							context.beginPath();
							context.moveTo(sx, sy);
							trianglePoints[0].x = sx;
							trianglePoints[0].y = sy;
							
							context.lineTo(sx - triangleLineWidth/2, sy+triangleHeight);
							trianglePoints[1].x = sx - triangleLineWidth/2;
							trianglePoints[1].y = sy+triangleHeight;
							
							context.lineTo(sx + triangleLineWidth/2, sy+triangleHeight);
							trianglePoints[2].x = sx + triangleLineWidth/2;
							trianglePoints[2].y = sy+triangleHeight;
							
							context.lineTo(sx, sy);
							
							context.closePath();
							context.stroke();
							context.fill();trianglePoints[0] = new Point();
							
							
							// Line to triangle
							context.beginPath();
							context.moveTo(prevX, prevY);
							context.lineTo(sx,sy+triangleHeight);
							lineToTrianglePoint.x = sx;
							lineToTrianglePoint.y = sy +triangleHeight;
							
							context.stroke();
							
							break;
		}
		
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
	
	
	/**
	 * Checks if a Point is in the triangle with the coordinates {@link #trianglePoints} to determine if a mouse interaction like click etc. 
	 * is the triangle which is displayed as the super class inheritance part of this {@link Drawable}.
	 * This problem is solved by solving the equivalations:<br />
	 *  I.  px = ax + s*(bx -ax ) +t*(cx - ax) <br />
	 * II. py = ay + s*(by -ay) + t*(cy - ay) <br />
	 * 
	 * Then the 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isPointInTrianle(double px, double py)
	{
		Point a = trianglePoints[0], b = trianglePoints[1], c = trianglePoints[2];
		
		double t = (- b.y*px + a.x*b.y + px*a.y - a.x*a.y + py*b.x - a.y*b.x - py*a.x + a.y*a.x) 
					/(- c.x*b.y + a.x*b.y + c.x*a.y - a.x*a.y + c.y*b.x - a.y*b.x - c.y *a.x + a.y*a.x);
		
		double s = (py-a.y - t * (c.y-a.y) ) / (b.y - a.y);
		
		 // 0<= s,t <= 1 UND 0<= s+ t <= 1
		return 0<=s && 0<=t && 0<= (s+t) && (s+t)<=1;
	}
	

	@Override
	public boolean hasCoordinate(double x, double y) {
		
		
		if (isPointInTrianle(x, y))
			return true;
		
		// special case, if this Drawable is selected so the superAnchor is also Part of this drawable because the Anchor itself is not 
		// part of the linear function that is calculated underneath.
		if (isSelected())
			// Calculate the absolute Positions for the super default AnchorPoint
			if (isBetween(superDrawable.getX()+superAnchor.getX()-(superAnchor.getWidth()/2), superDrawable.getX()+superAnchor.getX()+(superAnchor.getWidth()/2), x)
				&& isBetween(superDrawable.getY()+superAnchor.getY()-(superAnchor.getHeight()/2), superDrawable.getY()+superAnchor.getY()+(superAnchor.getHeight()/2), y)
				)
				return true;
			
		
		
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
				nextX = lineToTrianglePoint.x;
				nextY = lineToTrianglePoint.y;
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




	@Override
	public Placeable hasPlaceableAt(double x, double y) {
	
		
		// Calculate the absolute Positions for the owner default AnchorPoint
		if (isBetween(ownerDrawable.getX()+ownerAnchor.getX()-(ownerAnchor.getWidth()/2), ownerDrawable.getX()+ownerAnchor.getX()+(ownerAnchor.getWidth()/2), x)
			&& isBetween(ownerDrawable.getY()+ownerAnchor.getY()-(ownerAnchor.getHeight()/2), ownerDrawable.getY()+ownerAnchor.getY()+(ownerAnchor.getHeight()/2), y)
			)
			return ownerAnchor;
		
		
		// Calculate the absolute Positions for the super default AnchorPoint
		if (isBetween(superDrawable.getX()+superAnchor.getX()-(superAnchor.getWidth()/2), superDrawable.getX()+superAnchor.getX()+(superAnchor.getWidth()/2), x)
			&& isBetween(superDrawable.getY()+superAnchor.getY()-(superAnchor.getHeight()/2), superDrawable.getY()+superAnchor.getY()+(superAnchor.getHeight()/2), y)
			)
			return superAnchor;
		
		
		
		
		
		
		// check AnchorPoints between source and connection box
	
		
		AnchorPoint currentPoint = ownerAnchor.getAnchorPoint();
		Anchor a;
		// if we are at the end, than null should be there
		while (currentPoint!=superAnchor.getAnchorPoint())
		{
			a = anchorMap.get(currentPoint);
			
			if (a.hasCoordinate(x, y))
				return a;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}
		
				
		return null;
	}
	
	

}
