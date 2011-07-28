package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.parser.AttributeList;

import org.gemsjax.client.admin.model.metamodel.Attribute;
import org.gemsjax.client.admin.model.metamodel.MetaClass;
import org.gemsjax.client.admin.model.metamodel.exception.AttributeNameException;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;



/**
 * This class represents a MetaClass. A MetaClass must be added to a {@link MetaModel} and sho
 * @author Hannes Dorfmann
 *
 */
public class MetaClassDrawable implements Drawable, ClickHandler,FocusHandler, ResizeHandler, MoveHandler, MouseOverHandler{

	
	private List<ResizeArea> resizeAreas;
	
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List<MouseOverHandler> mouseOverHandlers;
	private List <ClickHandler> clickHandlers;
	private List <FocusHandler> focusHandlers;
	

	private MetaClass metaClass;
	private boolean mouseOver=false;

	public MetaClassDrawable(MetaClass metaClass)
	{
		this.metaClass = metaClass;
	
		// Handlers
		 resizeAreas = new LinkedList<ResizeArea>();
		 moveHandlers = new LinkedList<MoveHandler>();
		 resizeHandlers = new LinkedList<ResizeHandler>();
		 mouseOverHandlers = new LinkedList<MouseOverHandler>();
		 clickHandlers = new LinkedList<ClickHandler>();
		 focusHandlers = new LinkedList<FocusHandler>();

		 // a Resize Area
		 resizeAreas.add(new ResizeArea(metaClass.getX()+metaClass.getWidth()-6, metaClass.getY()+metaClass.getHeight()-6, 6, 6));

		 this.addMouseOverHandler(this);
		 this.addMoveHandler(this);
		 this.addResizeHandler(this);
		 this.addFocusHandler(this);
	}
		
	@Override
	public double getX() {
		return metaClass.getX();
	}

	@Override
	public double getY() {
		return metaClass.getY();
	}

	@Override
	public double getZ() {
		return metaClass.getZ();
	}

	@Override
	public void setX(double x) {
		metaClass.setX(x);
		
	}

	@Override
	public void setY(double y) {
		metaClass.setY(y);
	}

	@Override
	public void setZ(double z) {
		metaClass.setZ(z);
	}

	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		return (isBetween(metaClass.getX(), metaClass.getX()+metaClass.getWidth(), x) && isBetween(metaClass.getY(), metaClass.getY()+metaClass.getHeight(),y));
	}

	@Override
	public void draw(Context2d context) {
		
		CanvasGradient gradient = context.createLinearGradient(metaClass.getX(), metaClass.getY(),metaClass.getX()+metaClass.getWidth(),metaClass.getY()+metaClass.getHeight());
		
		gradient.addColorStop(0,metaClass.getGradientStartColor());
		gradient.addColorStop(0.7, metaClass.getGradientEndColor());
		
		
		context.setFillStyle(metaClass.getBorderColor());
		context.fillRect(metaClass.getX(), metaClass.getY(), metaClass.getWidth(), metaClass.getHeight());
		
		
		context.setFillStyle(gradient);
		context.fillRect(metaClass.getX()+metaClass.getBorderSize(), metaClass.getY()+metaClass.getBorderSize(), metaClass.getWidth()-2*metaClass.getBorderSize(), metaClass.getHeight()-2*metaClass.getBorderSize());
		
		drawName(context);
		
		if (metaClass.isDisplayAttributes())
			drawAttributes(context);
		
	}

	@Override
	public void drawOnMouseOver(Context2d context) {
		// TODO what to do when mouse is over
		
	}

	@Override
	public void drawOnSelected(Context2d context) {
		
		draw(context);

		if (isSelected())
		for (ResizeArea ra : resizeAreas)
			ra.draw(context);
				
	}
	
	
	/**
	 * Draw the Attributes, which should be displayed for this class
	 */
	public void drawAttributes(Context2d context){
		
		if (metaClass.getAttributeCount()==0)
			return;
		
		context.setFillStyle(metaClass.getAttributeFontColor());
		context.setFont(""+metaClass.getAttributeFontSize()+"px "+metaClass.getFontFamily());
		context.setTextAlign("left");
		
		double x = metaClass.getX() + metaClass.getAttributeLeftSpace(), y=metaClass.getY()+metaClass.getNameFontSize()+metaClass.getNameTopSpace()+metaClass.getNameBottomSpace()+metaClass.getAttributeListTopSpace();
		
		String txt;
		
		double heightForAttributeList = metaClass.getHeight() -metaClass.getNameTopSpace() - metaClass.getNameBottomSpace() - metaClass.getAttributeListTopSpace() - metaClass.getAttributeListBottomSpace();
		
		int attributeLines = (int) (heightForAttributeList / (metaClass.getAttributeFontSize()+metaClass.getAttributeToAttributeSpace()) );
		
		if (attributeLines>metaClass.getAttributeCount())
			attributeLines = metaClass.getAttributeCount();
		
		for (int i =0; i<attributeLines; i++)
		{
			Attribute a = metaClass.getAttribute(i);
		
			txt = metaClass.getWidth()-metaClass.getAttributeLeftSpace()-metaClass.getAttributeRightSpace() > ((a.getName().length()+a.getType().length()+3)*metaClass.getAttributeFontCharWidth())
				? a.getName()+" : "+a.getType() : (a.getName()+" : "+a.getType()).subSequence(0, (int) ((metaClass.getWidth()-metaClass.getAttributeLeftSpace()-metaClass.getAttributeRightSpace())/metaClass.getAttributeFontCharWidth() - 3))+"...";
			
				context.fillText(txt, x, y);
				
			y+=metaClass.getAttributeFontSize()+metaClass.getAttributeToAttributeSpace();
		}
		
	}
	
	/**
	 * Draw the meta-class name
	 */
	private void drawName(Context2d context) {
		
	
		String txt = metaClass.getWidth()-metaClass.getNameLeftSpace() >(metaClass.getName().length()*metaClass.getNameFontCharWidth()) 
				? metaClass.getName() : metaClass.getName().subSequence(0, (int) ((metaClass.getWidth()-metaClass.getNameLeftSpace())/metaClass.getNameFontCharWidth() - 3))+"...";
		
		context.setFillStyle(metaClass.getNameFontColor());
		context.setFont("bold "+metaClass.getNameFontSize()+"px "+metaClass.getFontFamily());
		
		context.setTextAlign("left");
		context.fillText(txt, metaClass.getX()+metaClass.getNameLeftSpace(), metaClass.getY()+metaClass.getNameTopSpace()+metaClass.getNameFontSize());
		
		
		// if there is at least one attribute, draw a horizontal line
		if (metaClass.isDisplayAttributes() && metaClass.getAttributeCount()>0 )
		{
			context.setFillStyle(metaClass.getBorderColor());
			context.fillRect(metaClass.getX(), metaClass.getY()+metaClass.getNameFontSize()+metaClass.getNameTopSpace()+metaClass.getNameBottomSpace(), metaClass.getWidth(), metaClass.getBorderSize());
		}
		
	}

	/**
	 * Calculate the width and height that is needed to paint the whole object completely on the canvas.
	 * 
	 */
	public void autoSize()
	{
		// TODO optimization 
		
		double nameWidth = metaClass.getNameLeftSpace() + metaClass.getName().length()*metaClass.getNameFontCharWidth() + metaClass.getNameRightSpace();
		double nameHeight = metaClass.getNameTopSpace() + metaClass.getNameFontSize() + metaClass.getNameBottomSpace();
		
		double attributesHeight = metaClass.getAttributeListTopSpace() + metaClass.getAttributeCount()* (metaClass.getAttributeFontSize() + metaClass.getAttributeToAttributeSpace()) + metaClass.getAttributeListBottomSpace();
		
		
		
		int longestChar = 0;
		
		// Calculate the longest attribute (attribute string)
		for (int i =0; i<metaClass.getAttributeCount();i++)
		{
			Attribute a = metaClass.getAttribute(i);
			if ((a.getName().length() + a.getType().length() + 3 )> longestChar)	// + 3 is for the separator String " : " between name and type
				longestChar = a.getName().length() + a.getType().length() + 3;
		}
		
		double attributeWidth = metaClass.getAttributeLeftSpace() + longestChar *metaClass.getAttributeFontCharWidth() + metaClass.getAttributeRightSpace();
		
		if (metaClass.isDisplayAttributes())
		{
			// Set width
			if (attributeWidth>nameWidth)
				metaClass.setWidth( attributeWidth );
			else
				metaClass.setWidth( nameWidth );
		}
		else
			metaClass.setWidth(nameWidth);
			
		// Set height
		metaClass.setHeight(metaClass.isDisplayAttributes() ? (nameHeight + attributesHeight) : nameHeight); 
		
		// Set the ResizeArea at the correct Position
		autoSetResizeAreaPosition();
	}
	
	
	/**
	 * Sets automatically the Position of the ResizeArea.
	 * This method should be called, whenever the width or height of this MetaClass drawable has been changed (except by a ResizeEvents)<br/>
	 * <b>Notice</b> {@link #autoSize()} will call this method automatically.
	 */
	private void autoSetResizeAreaPosition()
	{
		for (ResizeArea r : resizeAreas)
		{
			r.setX(metaClass.getX()+metaClass.getWidth()-r.getWidht());
			r.setY(metaClass.getY()+metaClass.getHeight()-r.getHeight());
		}
	}
	
	
	@Override
	public boolean isMoveable() {
		return metaClass.isCanBeMoved();
	}

	@Override
	public double getWidth() {
		return metaClass.getWidth();
	}

	@Override
	public double getHeight() {
		return metaClass.getHeight();
	}

	@Override
	public void setWidth(double width) {
		metaClass.setWidth(width);
	}

	@Override
	public void setHeight(double height) {
		metaClass.setHeight(height);
	}

	@Override
	public boolean isResizeable() {
			return metaClass.isCanBeResized();
	}

	@Override
	public void setMinWidth(double minWidth) {
		metaClass.setMinWidth(minWidth);
	}

	@Override
	public double getMinWidth() {
		
		return metaClass.getMinWidth();
	}

	@Override
	public void setMinHeight(double minHeight) {
		metaClass.setMinHeight(minHeight);
	}

	@Override
	public double getMinHeight() {
		return metaClass.getMinHeight();
	}

	@Override
	public void setSelected(boolean selected) {
		metaClass.setSelected(selected);
	}

	@Override
	public boolean isSelected() {
		return metaClass.isSelected();
	}

	@Override
	public void onResize(ResizeEvent event) {
		
		if (isResizeable() && event.getWidth()>getMinWidth() && event.getHeight()>getMinHeight())
		{
			
			for (ResizeArea r : resizeAreas)
			{
				r.setX(r.getX()+ (event.getWidth()-this.getWidth()));
				r.setY(r.getY()+ (event.getHeight()-this.getHeight()));
			}
			
			this.setWidth(event.getWidth());
			this.setHeight(event.getHeight());
			
		}
		
		
		
		
	}

	@Override
	public void onMove(MoveEvent e) {
		
		double oldX = getX();
		double oldY = getY();
		
		this.setX(e.getX()-e.getDistanceToTopLeftX());
		this.setY(e.getY()-e.getDistanceToTopLeftY());
		
		//this.setX(getX()+e.getX() - e.getStartX());
		//this.setY(getY()+e.getY() - e.getStartY());
		
		// Set the Position of the ResizeAreas
		for (ResizeArea ra : resizeAreas)
		{
			ra.setX(ra.getX() + (getX()-oldX));
			ra.setY(ra.getY() + (getY()-oldY));
		}
		
		
		
	}

	@Override
	public void addResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.add(resizeHandler);
	}

	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}

	@Override
	public void addMoveHandler(MoveHandler moveHandler) {
		moveHandlers.add(moveHandler);
	}

	@Override
	public void removeMoveHandler(MoveHandler moveHandler) {
		moveHandlers.remove(moveHandler);
	}


	@Override
	public List<ResizeHandler> getResizeHandlers() {
		return resizeHandlers;
	}

	@Override
	public boolean isMouseOver() {
		return mouseOver;
	}

	@Override
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	@Override
	public void addMouseOverHandler(MouseOverHandler mouseOverHandler) {
		mouseOverHandlers.add(mouseOverHandler);
	}

	@Override
	public void removeMouseOverHandler(MouseOverHandler mouseOverHandler) {
		mouseOverHandlers.remove(mouseOverHandler);
	}

	@Override
	public List<MouseOverHandler> getMouseOverHandlers() {
		return mouseOverHandlers;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		// TODO What to do when mouse is over. Let a Menu appear
	}

	@Override
	public ResizeArea isResizerAreaAt(double x, double y) {
		for (ResizeArea r :resizeAreas)
			if (r.hasCoordinate(x, y))
				return r;
		
		return null;
	}

	public String getBorderColor() {
		return metaClass.getBorderColor();
	}

	public void setBorderColor(String borderColor) {
		metaClass.setBorderColor(borderColor);
	}

	public int getBorderSize() {
		return metaClass.getBorderSize();
	}

	public void setBorderSize(int borderSize) {
		metaClass.setBorderSize(borderSize);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO what is to do, when the element got a click event? Conflict with FocusEvents?
	}
	
	@Override
	public void addClickHandler(ClickHandler handler)
	{
		if (!clickHandlers.contains(handler))
			clickHandlers.add(handler);
	}
	
	@Override
	public void removeClickHandler(ClickHandler handler)
	{
		clickHandlers.remove(handler);
	}

	@Override
	public boolean fireClickEvent(ClickEvent event)
	{
		for (ClickHandler h: clickHandlers)
		{
			h.onClick(event);
		}
		
		return clickHandlers.size()>0;
	}

	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			focusHandlers.add(handler);
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		for (FocusHandler h : focusHandlers)
			h.onFocusEvent(event);
		
		return focusHandlers.size()>0;
	}

	@Override
	public void removeFocusHandler(FocusHandler handler) {
	
		focusHandlers.remove(handler);
	}

	@Override
	public void onFocusEvent(FocusEvent event) {
		
		if (event.getType()==FocusEventType.GOT_FOCUS)
			this.setSelected(true);
		else
			this.setSelected(false);
		
	}

	@Override
	public boolean fireMoveEvent(MoveEvent event) {
		
		boolean delivered = false;
		
		for (MoveHandler h : moveHandlers)
		{
			h.onMove(event);
			delivered = true;
		}
		
		return delivered;
	}
}
