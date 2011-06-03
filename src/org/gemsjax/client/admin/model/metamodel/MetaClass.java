package org.gemsjax.client.admin.model.metamodel;

<<<<<<< HEAD
import java.util.List;

import org.gemsjax.client.canvas.MetaClassDrawable;

/**
 * A MetaClass is a representation of a Meta-Model class
 * @author Hannes Dorfmann
 *
 */
public class MetaClass extends MetaClassDrawable {
	
	public MetaClass(double x, double y) {
		super(x, y);
		
	}
=======
import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.Context2d;




/**
 * This class is the {@link Drawable} which will draw a MetaClass object on the Canvas
 * @author Hannes Dorfmann
 *
 */
public class MetaClass implements Drawable, ResizeHandler, MoveHandler, MouseOverHandler{

	// Drawable fields
	
	private double x, y,z;
	private double width = 100, height = 200, minWidth = 30, minHeight = 30;
	
	private String borderColor;
	private String backgroundColor;
	private String textColor;
	
	private boolean canBeMoved;
	private boolean canBeResized;
	private boolean selected;
	private boolean mouseOver;
	
	
	private int borderSize;
	
	private List<ResizeArea> resizeAreas;
	
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List<MouseOverHandler> mouseOverHandlers;
	
	
	// MetaClass Data fields

>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

	/**
	 * The name of this MetaClass
	 */
	private String name;
	
	/**
	 * TODO What is Root?
	 */
	private boolean isRoot;
	/**
	 * Is this MetaClass abstract?
	 */
	private boolean isAbstract;
	
	private List <Attribute>attributeList;
	
	private List <Connection> connectionList;
	
	private List<InheritanceRelation> inheritanceRelationList;
<<<<<<< HEAD

	
	
=======
		
	public MetaClass(double x, double y) {
		 
		 // Drawbale Settings
		 this.x = x;
		 this.y = y;
		 this.backgroundColor = "white";
		 
		 this.borderColor = "black";
		 textColor = "black";
		 this.borderSize = 1;
		 canBeMoved = true;
		 selected = false;
		 mouseOver = false;
		 canBeResized = true;
		 
		 
		 // Handlers
		 resizeAreas = new LinkedList<ResizeArea>();
		 moveHandlers = new LinkedList<MoveHandler>();
		 resizeHandlers = new LinkedList<ResizeHandler>();
		 mouseOverHandlers = new LinkedList<MouseOverHandler>();
		 
		 // a Resize Area
		 resizeAreas.add(new ResizeArea(x+width-6, y+height-6, 6, 6));
		 
		 

		 this.addMouseOverHandler(this);
		 this.addMoveHandler(this);
		 this.addResizeHandler(this);
		
		
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public void setX(double x) {
		this.x = x;
		
	}

	@Override
	public void setY(double y) {
		this.y=y;
	}

	@Override
	public void setZ(double z) {
		this.z=z;
	}

	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		return (isBetween(this.x, this.x+width, x) && isBetween(this.y, this.y+height,y));
	}

	@Override
	public void draw(Context2d context) {
		
		
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		context.setFillStyle(backgroundColor);
		context.fillRect(x+borderSize, y+borderSize, width-2*borderSize, height-2*borderSize);
		
	}

	@Override
	public void drawOnMouseOver(Context2d context) {
		// TODO Auto-generated method stub
		
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
		
	}
	
	/**
	 * Draw the Classname somewhere
	 */
	public void drawName(Context2d context){
		
	}

	@Override
	public boolean isMoveable() {
		return canBeMoved;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean isResizeable() {
			return canBeResized;
	}

	@Override
	public void setMinWidth(double minWidth) {
		this.minWidth = minWidth;
	}

	@Override
	public double getMinWidth() {
		
		return minWidth;
	}

	@Override
	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	@Override
	public double getMinHeight() {
		return minHeight;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		return selected;
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
	public List<MoveHandler> getMoveHandlers() {
		return moveHandlers;
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
	public void onMouseOver(double x, double y) {
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
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	public List<Connection> getConnectionList() {
		return connectionList;
	}

	public List<InheritanceRelation> getInheritanceRelationList() {
		return inheritanceRelationList;
	}
	
	
	
	

}
