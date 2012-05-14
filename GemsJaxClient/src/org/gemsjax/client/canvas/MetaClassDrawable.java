package org.gemsjax.client.canvas;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.IconLoadEvent;
import org.gemsjax.client.canvas.events.MouseOutEvent;
import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.IconLoadHandler;
import org.gemsjax.client.canvas.handler.MouseOutHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaClass;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * This class represents a {@link MetaClass}
 * @author Hannes Dorfmann
 *
 */
public class MetaClassDrawable implements Drawable, Clickable, Focusable, MouseOutable, MouseOverable, Moveable,
											Resizeable, IconLoadable, PlaceableDestination  {

	
	private List<ResizeArea> resizeAreas;
	
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List<MouseOverHandler> mouseOverHandlers;
	private List <ClickHandler> clickHandlers;
	private List <FocusHandler> focusHandlers;
	private List <MouseOutHandler> mouseOutHandlers;
	private List <IconLoadHandler> iconLoadableHandlers;
	
	
	private String destinationAreaHighlightColor = "rgba(168,245,140,0.5)";
	private String onMouseOverDestinationColor = "rgba(85,187,250,0.5)";

	private MetaClass metaClass;
	
	/**
	 * The current x coordinate of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getX()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this x value is set and changed permanently, but the original x value of the 
	 * {@link MetaClass#setX(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this x value is the same as the original {@link MetaClass#getX()} value, or vice versa.
	 */
	private double x;
	
	/**
	 * The current y coordinate of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getY()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this y value is set and changed permanently, but the original y value of the 
	 * {@link MetaClass#setY(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this y value is the same as the original {@link MetaClass#getY()} value, or vice versa.
	 */
	private double y;
	
	/**
	 * The current width of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getWidth()},
	 * because this value is used to draw resize events / animations.
	 * So while a animation this width value is set and changed permanently, but the original width value of the 
	 * {@link MetaClass#setWidth(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this value is the same as the original {@link MetaClass#getWidth()} value, or vice versa.
	 */
	private double width;
	/**
	 * The current height of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getHeight()},
	 * because this value is used to draw resize events / animations.
	 * So while a animation this width value is set and changed permanently, but the original height value of the 
	 * {@link MetaClass#setHeight(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this value is the same as the original {@link MetaClass#getHeight()} value, or vice versa.
	 */
	private double height;
	
	
	private boolean iconLoaded = false;
	private Image iconImage;

	/**
	 * A list with all {@link Anchor}s that are docked to this {@link MetaClassDrawable}.
	 * The list of Anchors is important to react on resizements to adjust the Anchors relative coordinates.
	 */
	private List<DockableAnchor> dockedAnchors;
	
	
	public MetaClassDrawable(MetaClass metaClass)
	{
		this.metaClass = metaClass;
		
		iconImage = new Image();
			
		iconImage.addLoadHandler(new LoadHandler() {
				
				@Override
				public void onLoad(LoadEvent event) {
					iconLoaded = true;
					fireIconLoadEvent(new IconLoadEvent(MetaClassDrawable.this, iconImage.getUrl()));
				}
			});
				
	
		iconImage.setVisible(false);
		RootPanel.get().add(iconImage); // image must be on page to fire load events
		
		if (metaClass.getIconURL()!=null && !metaClass.getIconURL().equals(""))
			loadIcon(metaClass.getIconURL());
		
		// Handler lists
		 resizeAreas = new LinkedList<ResizeArea>();
		 moveHandlers = new LinkedList<MoveHandler>();
		 resizeHandlers = new LinkedList<ResizeHandler>();
		 mouseOverHandlers = new LinkedList<MouseOverHandler>();
		 clickHandlers = new LinkedList<ClickHandler>();
		 focusHandlers = new LinkedList<FocusHandler>();
		 mouseOutHandlers = new LinkedList<MouseOutHandler>();
		 iconLoadableHandlers = new LinkedList<IconLoadHandler>();
		 
		 dockedAnchors = new LinkedList<DockableAnchor>();
		 
		 this.x = metaClass.getX();
		 this.y = metaClass.getY();
		 this.width = metaClass.getWidth();
		 this.height = metaClass.getHeight();
		 

		 // a Resize Area
		 resizeAreas.add(new ResizeArea(x+width-6, y+height-6, 6, 6));

	}
	
	/**
	 * Dock an {@link Anchor} to this {@link Drawable}.
	 * This docking is important, to react on resizements.
	 * @param a
	 */
	@Override
	public void dockAnchor(DockableAnchor a)
	{
		if (!dockedAnchors.contains(a))
			dockedAnchors.add(a);
	}
	
	
	/**
	 * Undock an {@link Anchor}
	 * @see #dockAnchor(Anchor)
	 * @param a
	 */
	@Override
	public void undockAnchor(DockableAnchor a)
	{
		dockedAnchors.remove(a);
	}
	
	
	
	
	/**
	 * Get a list with all {@link Anchor}s that are currently docked to this {@link MetaClassDrawable}
	 * @return
	 */
	@Override
	public List<DockableAnchor> getDockedAnchors()
	{
		return dockedAnchors;
	}
	
	
	private void loadIcon(String url)
	{	
		iconLoaded = false;
		iconImage.setUrl(url);
	}
	
	
	public boolean isIconLoaded()
	{
		return iconLoaded;
	}
	
	public ImageElement getIcon()
	{
		return (ImageElement) iconImage.getElement().cast();
	}
	
	public List<ResizeArea> getResizeAreas()
	{
		return resizeAreas;
	}
		
	/**
	 * Get the x coordinate of the MetaClassDrawable of the TOP-LEFT corner
	 * 
	 * The current x coordinate of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getX()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this x value is set and changed permanently, but the original x value of the 
	 * {@link MetaClass#setX(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this x value is the same as the original {@link MetaClass#getX()} value, or vice versa.
	 
	 * @return
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Get the y coordinate of the Object of the TOP-LEFT Corner
	 *
	 * The current y coordinate of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getY()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this y value is set and changed permanently, but the original y value of the 
	 * {@link MetaClass#setY(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this y value is the same as the original {@link MetaClass#getY()} value, or vice versa.
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set the current x coordinate of this {@link MetaClassDrawable}.
	 * <b>This method will not set the value of the underlying MetaClass object ({@link MetaClass#setX(double)}).</b>
	 * This could be another value as the {@link MetaClass#getX()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this x value is set and changed permanently, but the original x value of the 
	 * {@link MetaClass#setX(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this x value is the same as the original {@link MetaClass#getX()} value, or vice versa.
	 *  
	 */
	public void setX(double x) {
		this.x = x;
		autoSetResizeAreaPosition();
		
	}

	/**
	 * Set the current y coordinate of this {@link MetaClassDrawable}.
	 * <b>This method will not set the value of the underlying MetaClass object ({@link MetaClass#setX(double)}).</b>
	 * This could be another value as the {@link MetaClass#getY()},
	 * because this value is used to draw Move events / animations.
	 * So while a animation this y value is set and changed permanently, but the original y value of the 
	 * {@link MetaClass#setY(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this y value is the same as the original {@link MetaClass#getY()} value, or vice versa.
	 */
	public void setY(double y) {
		this.y = y;
		autoSetResizeAreaPosition();
	}

	public void setZ(double z) {
		metaClass.setZIndex(z);
	}

	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		return (isBetween(getX(), getX()+getWidth(), x) && isBetween(getY(), getY()+getHeight(),y));
	}

	@Override
	public void draw(Context2d context) {
		
		context.save();
		
		double x = this.x;
		double y = this.y;
		
		double iconHeight = drawIcon(x, y, context);
		
		y = y+iconHeight;
		
		drawBox(x,y, iconHeight, context);
		
		
		
		drawName(x,y, context);
		
		if (metaClass.isDisplayingAttributes())
			drawAttributes(x,y,iconHeight,context);
		
		
		if (isSelected())
			drawOnSelected(context);
		
		
		context.restore();
		
	}
	
	
	private void drawBox(double x, double y, double iconHeight, Context2d context)
	{
		double height = this.height - iconHeight;
		
		context.setLineWidth(1);
		
		CanvasGradient gradient = context.createLinearGradient(x, y, x+width, y+height);
		
		gradient.addColorStop(0,metaClass.getGradientStartColor());
		gradient.addColorStop(0.7, metaClass.getGradientEndColor());
		
		
		context.setShadowBlur(10);
		context.setShadowColor("black");
		context.setFillStyle(metaClass.getBorderColor());
		context.fillRect(x,y, this.width, height);
		context.setShadowBlur(0);
		
		context.setFillStyle(gradient);
		context.fillRect(x+metaClass.getBorderSize(), y+metaClass.getBorderSize(), width-2*metaClass.getBorderSize(), height-2*metaClass.getBorderSize());
		
	}
	
	private double drawIcon(double x, double y, Context2d context)
	{
		double height = metaClass.getIconHeight();
		double width = metaClass.getIconWidth();
		
		double spaceLeft =  (getWidth() - metaClass.getIconWidth() )/ 2;
		if (spaceLeft<0) spaceLeft=0;
		
		//TODO rethinking of MetaClass icon
		
		
		/*
		if (metaClass.getIconWidth()>getWidth())
		{
			width = getWidth();	
			height = (width * metaClass.getIconHeight())/metaClass.getIconWidth();
			
		}
		else
		{	height = metaClass.getIconHeight();
			width = metaClass.getIconWidth();
			spaceLeft = (getWidth() - metaClass.getIconWidth() )/ 2;
		}
		*/
		
		if (isIconLoaded())
		{
			context.drawImage(getIcon(), x+spaceLeft, y, width, height);
		}
		else
		{	

			String loadingTxt = "Loading";
			
			context.setFont(""+metaClass.getAttributeFontSize()+"px "+metaClass.getFontFamily());
			double textWidth = loadingTxt.length() * metaClass.getAttributeFontCharWidth();
			
			context.setFillStyle("black");
			context.setTextAlign("left");
			
			if (textWidth<width)
				context.fillText(loadingTxt, x+((width-textWidth)/2), y+metaClass.getAttributeFontSize(), width);
			else
			{
				int chars = (int)(textWidth / metaClass.getAttributeFontCharWidth()) - 3;
				
				if (chars<=0)
					context.fillText("...", x, y+metaClass.getAttributeFontSize(), width);
				else
					context.fillText(loadingTxt.substring(0, chars)+"...", x, y+metaClass.getAttributeFontSize(), width);
			}
		}
		
		
		
		return height;	
		
	}

	
	/**
	 * Implement how the Drawable should be drawn, when the Drawable has been selected (for example by clicking on it)
	 * @param context
	 */
	private void drawOnSelected(Context2d context) {
		
		// Set correct Position
		autoSetResizeAreaPosition();
		
		// Draw the ResizeAreas 
		for (ResizeArea ra : resizeAreas)
			ra.draw(context);
				
	}
	
	
	/**
	 * Draw the Attributes, which should be displayed for this class
	 */
	private void drawAttributes(double originalX, double originalY, double iconHeight, Context2d context){
		
		if (metaClass.getAttributes().size()==0)
			return;
		
		context.setFillStyle(metaClass.getAttributeFontColor());
		context.setFont(""+metaClass.getAttributeFontSize()+"px "+metaClass.getFontFamily());
		context.setTextAlign("left");
		
		double x =originalX + metaClass.getAttributeListLeftSpace(), y=originalY+metaClass.getNameFontSize()+metaClass.getNameTopSpace()+metaClass.getNameBottomSpace()+metaClass.getAttributeListTopSpace();
		
		String txt;
		
		double heightForAttributeList = this.height -iconHeight -metaClass.getNameTopSpace() - metaClass.getNameBottomSpace() - metaClass.getAttributeListTopSpace() - metaClass.getAttributeListBottomSpace();
		
		int attributeLines = (int) (heightForAttributeList / (metaClass.getAttributeFontSize()+metaClass.getAttributeToAttributeSpace()) );
		
		if (attributeLines>metaClass.getAttributes().size())
			attributeLines = metaClass.getAttributes().size();
		
		for (int i =0; i<attributeLines; i++)
		{
			MetaAttribute a = metaClass.getAttributes().get(i);
		
			txt = width-metaClass.getAttributeListLeftSpace()-metaClass.getAttributeListRightSpace() > ((a.getName().length()+a.getType().getName().length()+3)*metaClass.getAttributeFontCharWidth())
				? a.getName()+" : "+a.getType().getName() : (a.getName()+" : "+a.getType().getName()).subSequence(0, (int) ((getWidth()-metaClass.getAttributeListLeftSpace()-metaClass.getAttributeListRightSpace())/metaClass.getAttributeFontCharWidth() - 3))+"...";
			
				context.fillText(txt, x, y);
				
			y+=metaClass.getAttributeFontSize()+metaClass.getAttributeToAttributeSpace();
		}
		
	}
	
	/**
	 * Draw the meta-class name
	 */
	private void drawName(double x, double y, Context2d context) {
		
	
		String txt = this.width - metaClass.getNameLeftSpace() >(metaClass.getName().length()*metaClass.getNameFontCharWidth()) 
				? metaClass.getName() : metaClass.getName().subSequence(0, (int) ((this.width-metaClass.getNameLeftSpace())/metaClass.getNameFontCharWidth() - 3))+"...";
		
		context.setFillStyle(metaClass.getNameFontColor());
		context.setFont("bold "+metaClass.getNameFontSize()+"px "+metaClass.getFontFamily());
		
		context.setTextAlign("left");
		context.fillText(txt, x+metaClass.getNameLeftSpace(), y+metaClass.getNameTopSpace()+metaClass.getNameFontSize());
		
		
		// if there is at least one attribute, draw a horizontal line
		if (metaClass.isDisplayingAttributes() && metaClass.getAttributes().size()>0 && height > metaClass.getIconHeight()+metaClass.getNameFontSize()+metaClass.getNameTopSpace()+metaClass.getNameBottomSpace())
		{
			context.setFillStyle(metaClass.getBorderColor());
			context.fillRect(x, y+metaClass.getNameFontSize()+metaClass.getNameTopSpace()+metaClass.getNameBottomSpace(), width, metaClass.getBorderSize());
		}
		
	}

	/**
	 * Calculate the width and height that is needed to paint the whole object completely on the canvas.
	 * 
	 */
	public void autoSize()
	{
		metaClass.autoSize();
		
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
			r.setX(x+width-r.getWidht());
			r.setY(y+height-r.getHeight());
		}
	}
	

	/**
	 * Get the current width of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getWidth()},
	 * because this value is used to draw resize events / animations.
	 * So while a animation this width value is set and changed permanently, but the original width value of the 
	 * {@link MetaClass#setWidth(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this value is the same as the original {@link MetaClass#getWidth()} value, or vice versa.
	 * @return The current width of this {@link MetaClassDrawable} (which is not necessarily the same as {@link MetaClass#getWidth()})
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * The current height of this {@link MetaClassDrawable}.
	 * This could be another value as the {@link MetaClass#getHeight()},
	 * because this value is used to draw resize events / animations.
	 * So while a animation this width value is set and changed permanently, but the original height value of the 
	 * {@link MetaClass#setHeight(double)} is set only when the animation is finished (for example when the mouse is released).
	 * However after the animation this value is the same as the original {@link MetaClass#getHeight()} value, or vice versa.
	 * @return The current height of this {@link MetaClassDrawable} (which is not necessarily the same as {@link MetaClass#getHeight()})
	 
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * <b>This method will not set the value of the underlying MetaClass object ({@link MetaClass#setWidth(double)}).</b>
	 * @see #getWidth()
	 */
	public void setWidth(double width) {
		
		this.width = width;
		autoSetResizeAreaPosition();
	}
	
	public void setWidthHeight(double width, double height)
	{
		this.height = height;
		this.width = width;
		
		autoSetResizeAreaPosition();
	}

	/**
	 * <b>This method will not set the value of the underlying MetaClass object ({@link MetaClass#setHeight(double)}).</b>
	 * @see #getHeight()
	 */
	public void setHeight(double height) {
		this.height = height;
		autoSetResizeAreaPosition();
	}
	
	/**
	 * This method adjusts automatically all {@link #dockedAnchors} (docked by calling  {@link #dockAnchor(Anchor)} ).
	 * This method is should be called after resizements of the width or height of this drawable to ensure, that no {@link DockableAnchor}
	 * is out of the border or in the border.
	 * 
	 */
	public void adjustDockedAnchors()
	{
		
		for (DockableAnchor a : dockedAnchors)				
		{
			
			if (a.getRelativeY() > getHeight())
				a.setRelativeY(getHeight());
			
			if (a.getRelativeX()> getWidth())
				a.setRelativeX(getWidth());
			
			
			if (a.getRelativeY()<getHeight() && a.getRelativeY() > 0)
				if (a.getRelativeX()>0 && a.getRelativeX()<getWidth())
					a.setRelativeX(getWidth());


			if (a.getRelativeX()<getWidth() && a.getRelativeX()>0)
				if (a.getRelativeY()>0 && a.getRelativeY()<getHeight())
					a.setRelativeY(getHeight());
			
		}
			
	}


	/**
	 * Set the minimum Width of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum Width
	 * @param minWidth
	 */
	public void setMinWidth(double minWidth) {
		metaClass.setMinWidth(minWidth);
	}

	/**
	 * get the minimum Width of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum Width
	 */
	public double getMinWidth() {
		
		return metaClass.getMinWidth();
	}

	/**
	 * Set the minimum height of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum height
	 * @param minWidth
	 */
	public void setMinHeight(double minHeight) {
		metaClass.setMinHeight(minHeight);
	}


	/**
	 * Get the minimum height of this Drawable.
	 * So if you can Resize the Drawable, you never will be able to resize it to a smaller as the minimum height
	 */
	public double getMinHeight() {
		return metaClass.getMinHeight();
	}

	/**
	 * Set this drawable as selected, so the {@link #drawOnSelected(Context2d)} method should be called
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		metaClass.setSelected(selected);
	}

	/**
	 * Set this as selected, so the {@link #drawOnSelected(Context2d)} method should be called
	 * @param selected
	 */
	public boolean isSelected() {
		return metaClass.isSelected();
	}

	/*
	@Override
	public void onResize(ResizeEvent event) {
		
		
		if (event.getWidth()>getMinWidth() && event.getHeight()>getMinHeight())
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
	*/

	/*
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

*/
	@Override
	public void addResizeHandler(ResizeHandler resizeHandler) {
		
		if (!resizeHandlers.contains(resizeHandler))
			if (resizeHandler instanceof Presenter)		// The presenter should always be the last in the list
				resizeHandlers.add(resizeHandlers.size(), resizeHandler);
			else
				resizeHandlers.add(0,resizeHandler);
	}

	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}

	@Override
	public void addMoveHandler(MoveHandler moveHandler) {
	
			if (!moveHandlers.contains(moveHandler))
				if (moveHandler instanceof Presenter)		// The presenter should always be the last in the list
					moveHandlers.add(moveHandlers.size(), moveHandler);
				else
					moveHandlers.add(0,moveHandler);
	}

	@Override
	public void removeMoveHandler(MoveHandler moveHandler) {
		moveHandlers.remove(moveHandler);
	}



	@Override
	public void addMouseOverHandler(MouseOverHandler mouseOverHandler) {
		if (!mouseOverHandlers.contains(mouseOverHandler))
			if (mouseOverHandler instanceof Presenter)		// The presenter should always be the last in the list
				mouseOverHandlers.add(mouseOverHandlers.size(), mouseOverHandler);
			else
				mouseOverHandlers.add(0,mouseOverHandler);
			
	}

	@Override
	public void removeMouseOverHandler(MouseOverHandler mouseOverHandler) {
		mouseOverHandlers.remove(mouseOverHandler);
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
	public void addClickHandler(ClickHandler handler)
	{
		if (!clickHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should allways be the last in the list
				clickHandlers.add(clickHandlers.size(), handler);
			else
				clickHandlers.add(0,handler);
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
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				focusHandlers.add(focusHandlers.size(), handler);
			else
				focusHandlers.add(0,handler);
			
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
	public boolean fireMoveEvent(MoveEvent event) {
		
		boolean delivered = false;
		
		for (MoveHandler h : moveHandlers)
		{
			h.onMove(event);
			delivered = true;
		}
		
		return delivered;
	}
	
	

	@Override
	public boolean fireMouseOverEvent(MouseOverEvent event) {
		
		boolean delivered = false;
		
		for (MouseOverHandler h : mouseOverHandlers)
		{
			h.onMouseOver(event);
			delivered = true;
		}
		
		return delivered;
	}

	
	@Override
	public boolean fireResizeEvent(ResizeEvent event) {
		
		boolean delivered = false;
		
		for (ResizeHandler h : resizeHandlers)
		{
			h.onResize(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void addMouseOutHandler(MouseOutHandler handler) {
		if (!mouseOutHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				mouseOutHandlers.add(mouseOutHandlers.size(), handler);
			else
				mouseOutHandlers.add(0,handler);
	}

	@Override
	public boolean fireMouseOutEvent(MouseOutEvent event) {
		
		boolean delivered = false;
		
		for (MouseOutHandler h : mouseOutHandlers)
		{
			h.onMouseOut(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeMouseOutHandler(MouseOutHandler handler) {
		
		mouseOutHandlers.remove(handler);
		
	}

	
	@Override
	public double getZIndex() {
		return metaClass.getZIndex();
	}

	@Override
	public Object getDataObject() {
		return metaClass;
	}


	@Override
	public void addIconLoadHandler(IconLoadHandler h) {
		
		
		if (!iconLoadableHandlers.contains(h))
			if (!iconLoadableHandlers.contains(h))
				if (h instanceof Presenter)		// The presenter should always be the last in the list
					iconLoadableHandlers.add(iconLoadableHandlers.size(), h);
				else
					iconLoadableHandlers.add(0,h);
	}


	@Override
	public boolean fireIconLoadEvent(IconLoadEvent e) {
		boolean delivered = false;
		
		for (IconLoadHandler h: iconLoadableHandlers)
		{
			h.onIconLoaded(e);
			delivered = true;
		}
		
		return delivered;
			
	}


	@Override
	public void removeIconLoadHanlder(IconLoadHandler h) {
		iconLoadableHandlers.remove(h);
	}


	@Override
	public Point canPlaceableBePlacedAt(double x, double y) {
		
		double offset = 3;
		
		// the left border
		if (isBetween(getX()-offset, getX()+offset,x) && isBetween(getY(), getY()+getHeight(),y))
			return new Point(getX(),y);
		else	// right border
		if (isBetween(getX()+getWidth()-offset, getX()+getWidth()+offset,x) && isBetween(getY(), getY()+getHeight(),y))
			return new Point(getX()+getWidth(),y);
		else // Top Border
		if (isBetween(getX(), getX() + getWidth(), x ) && isBetween(getY()-offset, getY()+offset,y))
			return new Point(x,getY());
		else // bottom border
		if (isBetween(getX(), getX() + getWidth(), x ) && isBetween(getY()+getHeight()-offset, getY()+getHeight()+offset,y))
				return new Point(x,getY()+getHeight());
		
		return null;
	}


	@Override
	public void highlightDestinationArea(Context2d context) {
	
		context.save();
		context.setStrokeStyle(destinationAreaHighlightColor);
		context.setLineWidth(3);
		
		context.beginPath();
		context.moveTo(getX(), getY());
		context.lineTo(getX(), getY()+getHeight());
		context.lineTo(getX()+getWidth(), getY()+getHeight());
		context.lineTo(getX()+getWidth(), getY());
		context.lineTo(getX(), getY());
		context.closePath();
		
		context.stroke();
		
		context.restore();
	}


	@Override
	public void highlightOnMouseOverDestinationArea(Context2d context) {
		
		context.save();
		context.setStrokeStyle(onMouseOverDestinationColor);
		context.setLineWidth(3);
		
		context.beginPath();
		context.moveTo(getX(), getY());
		context.lineTo(getX(), getY()+getHeight());
		context.lineTo(getX()+getWidth(), getY()+getHeight());
		context.lineTo(getX()+getWidth(), getY());
		context.lineTo(getX(), getY());
		context.closePath();
		
		context.stroke();
		
		context.restore();
	}

	@Override
	public BorderDirection getCoordinatesBorderDirection(double x, double y) {
	
		double offset = 5;
		
		// TOP
		if (isBetween(this.x, this.x+this.width, x) && isBetween(this.y-offset, this.y+offset, y))
			return BorderDirection.TOP;
		else
		// BOTTOM
		if (isBetween(this.x, this.x+this.width, x) && isBetween(this.y + this.height-offset, this.y+this.height+offset, y))
			return BorderDirection.BOTTOM;
		else
		// LEFT
		if (isBetween(this.y, this.y+this.height, y) && isBetween(this.x -offset, this.x + offset, x))
			return BorderDirection.LEFT;
		else
		// RIGHT
		if (isBetween(this.y, this.y+this.height, y) && isBetween(this.x + this.width -offset, this.x + this.width + offset, x))
			return BorderDirection.RIGHT;
		
		
		return BorderDirection.NOWHERE;
		
	}
	
	
	
	public MetaClass getMetaClass(){
		return metaClass;
	}
	
	
}
