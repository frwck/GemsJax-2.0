package org.gemsjax.client.model.metamodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.parser.AttributeList;

import org.gemsjax.client.admin.model.metamodel.exception.AttributeNameException;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;




/**
 * This class represents a MetaClass. A MetaClass must be added to a {@link MetaModel} and sho
 * @author Hannes Dorfmann
 *
 */
public class MetaClass {

	// Drawable fields
	
	private double x, y,z;
	private double width = 100, height = 200, minWidth = 30, minHeight = 30;
	
	private String borderColor = "black";
	
	private int borderSize = 1;
	
	private String gradientStartColor ="#FCCE70";
	private String gradientEndColor="#FCFBD5";
	
	
	/**
	 * The font family name. For an easier calculation of the width of a text you should allways use a monospace font.
	 * <b>If you change the font, you also have to recalculate {@link #nameFontCharWidth} and {@link #attributeFontCharWidth}. <b>
	 * @see MetaClass#nameFontCharWidth
	 */
	private String fontFamily ="Courier";
	
	/**
	 * The font color of the class name
	 */
	private String nameFontColor = "black";
	
	/**
	 * The text color for attributes
	 */
	private String attributeFontColor ="black";
	
	/**
	 * The size in pixel of the class name
	 */
	private int nameFontSize = 18;
	
	/**
	 * The font size of the attributes
	 */
	private int attributeFontSize = 14;
	
	/**
	 * This field will store the width of a single character in the given {@link #fontFamily} for the meta class name.
	 * This field is used to calculate the width of the meta class name.
	 * @see #fontFamily
	 */
	private double nameFontCharWidth = 12;
	
	/**
	 * This field will store the width of a single character in the given {@link #fontFamily} for the attribute text.
	 * This field is used to calculate the width of a whole attribute text.
	 * @see #fontFamily
	 */
	private double attributeFontCharWidth = 9;
	
	
	
	
	/**
	 * The free space (in pixel) between one attribute to the other attribute (which is displayed in the line below)
	 */
	private double attributeToAttributeSpace= 5;
	
	/**
	 * The space (in pixel) between the top of the attributes list and the parting line (the line between MetaClass name and attribute list)
	 */
	private double attributeListTopSpace = 20;
	
	/**
	 * The space (in pixel) between the last attribute of the attributes list and the bottom border
	 */
	private double attributeListBottomSpace = 5;
	
	/**
	 * The space (in pixel) between the border and the top of the name of this meta class
	 */
	private double nameTopSpace=3;
	
	/** The space (in pixel) between the bottom - border (or the parting line if there are attributes) 
	 * and the bottom of the name of this meta class
	 */
	private double nameBottomSpace = 5;
	
	/**
	 * The space (in pixel) between the left border and the (painted) name of this meta class
	 */
	private double nameLeftSpace=5;
	
	/**
	 * The space (in pixel) between the right border and the (painted) name of this meta class
	 */
	private double nameRightSpace = 5;
	
	/**
	 * The space (in pixel) between the right border and the textual (painted) attribute
	 */
	private double attributeRightSpace = 5;
	
	/**
	 * The space (in pixel) between the left border and the textual (painted) attribute
	 */
	private double attributeLeftSpace = 5;
	
	
	/**
	 * Should the attribute list be displayed or not?
	 */
	private boolean displayAttributes = true;
	
	private boolean canBeMoved;
	private boolean canBeResized;
	private boolean selected;
	
	
		
	// MetaClass Data fields

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

	public MetaClass(String name, double x, double y)
	{
		this(x,y);
		this.name = name;
	}
	
	
	public MetaClass(String name)
	{
		this(100,100);
		this.name = name;
	}
		
	public MetaClass(double x, double y) {
		 
		this.x = x;
		this.y = y;

		//setMinHeight(nameTopSpace+nameFontSize+nameBottomSpace);
		 
		 
		 canBeMoved = true;
		 selected = false;
		 canBeResized = true;
		 
		 // Lists
		 attributeList = new ArrayList<Attribute>();
		 inheritanceRelationList = new ArrayList<InheritanceRelation>();
		 connectionList = new ArrayList<Connection>();
		
		
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
		
	}

	public void setY(double y) {
		this.y=y;
	}

	public void setZ(double z) {
		this.z=z;
	}

	
	
	public boolean isMoveable() {
		return canBeMoved;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isResizeable() {
			return canBeResized;
	}

	public void setMinWidth(double minWidth) {
		this.minWidth = minWidth;
	}

	public double getMinWidth() {
		
		return minWidth;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
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

	/*
	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	
	public List<Connection> getConnectionList() {
		return connectionList;
	}

	public List<InheritanceRelation> getInheritanceRelationList() {
		return inheritanceRelationList;
	}


	*/
	
	
	public void addAttribute(String name, String type) throws AttributeNameException
	{
		for(Attribute a : attributeList)
			if (a.getName().equals(name))
				throw new AttributeNameException(name,this, "An Attribute with the name "+name+" already exists");
		
		attributeList.add(new Attribute(name, type));
	}
	
	/**
	 * Remove a {@link Attribute} (by searching the attribute according to the attribute name)
	 * If the Attribute was not found, nothing will be done.
	 * @param name
	 */
	public void removeAttribute(String name)
	{
		for (Attribute a: attributeList)
		{
			if (a.getName().equals(name))
			{
				// TODO check if this will throw a java iterator exception
				attributeList.remove(a);
				return;
			}
		}
	}


	public void setDisplayAttributes(boolean show) {
		this.displayAttributes = show;
	}


	public String getGradientStartColor() {
		return gradientStartColor;
	}


	public void setGradientStartColor(String gradientStartColor) {
		this.gradientStartColor = gradientStartColor;
	}


	public String getGradientEndColor() {
		return gradientEndColor;
	}


	public void setGradientEndColor(String gradientEndColor) {
		this.gradientEndColor = gradientEndColor;
	}


	public String getFontFamily() {
		return fontFamily;
	}


	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}


	public String getNameFontColor() {
		return nameFontColor;
	}


	public void setNameFontColor(String nameFontColor) {
		this.nameFontColor = nameFontColor;
	}


	public String getAttributeFontColor() {
		return attributeFontColor;
	}


	public void setAttributeFontColor(String attributeFontColor) {
		this.attributeFontColor = attributeFontColor;
	}


	public int getNameFontSize() {
		return nameFontSize;
	}


	public void setNameFontSize(int nameFontSize) {
		this.nameFontSize = nameFontSize;
	}


	public int getAttributeFontSize() {
		return attributeFontSize;
	}


	public void setAttributeFontSize(int attributeFontSize) {
		this.attributeFontSize = attributeFontSize;
	}


	public double getNameFontCharWidth() {
		return nameFontCharWidth;
	}


	public void setNameFontCharWidth(double nameFontCharWidth) {
		this.nameFontCharWidth = nameFontCharWidth;
	}


	public double getAttributeFontCharWidth() {
		return attributeFontCharWidth;
	}


	public void setAttributeFontCharWidth(double attributeFontCharWidth) {
		this.attributeFontCharWidth = attributeFontCharWidth;
	}


	public double getAttributeToAttributeSpace() {
		return attributeToAttributeSpace;
	}


	public void setAttributeToAttributeSpace(double attributeToAttributeSpace) {
		this.attributeToAttributeSpace = attributeToAttributeSpace;
	}


	public double getAttributeListTopSpace() {
		return attributeListTopSpace;
	}


	public void setAttributeListTopSpace(double attributeListTopSpace) {
		this.attributeListTopSpace = attributeListTopSpace;
	}


	public double getAttributeListBottomSpace() {
		return attributeListBottomSpace;
	}


	public void setAttributeListBottomSpace(double attributeListBottomSpace) {
		this.attributeListBottomSpace = attributeListBottomSpace;
	}


	public double getNameTopSpace() {
		return nameTopSpace;
	}


	public void setNameTopSpace(double nameTopSpace) {
		this.nameTopSpace = nameTopSpace;
	}


	public double getNameBottomSpace() {
		return nameBottomSpace;
	}


	public void setNameBottomSpace(double nameBottomSpace) {
		this.nameBottomSpace = nameBottomSpace;
	}


	public double getNameLeftSpace() {
		return nameLeftSpace;
	}


	public void setNameLeftSpace(double nameLeftSpace) {
		this.nameLeftSpace = nameLeftSpace;
	}


	public double getNameRightSpace() {
		return nameRightSpace;
	}


	public void setNameRightSpace(double nameRightSpace) {
		this.nameRightSpace = nameRightSpace;
	}


	public double getAttributeRightSpace() {
		return attributeRightSpace;
	}


	public void setAttributeRightSpace(double attributeRightSpace) {
		this.attributeRightSpace = attributeRightSpace;
	}


	public double getAttributeLeftSpace() {
		return attributeLeftSpace;
	}


	public void setAttributeLeftSpace(double attributeLeftSpace) {
		this.attributeLeftSpace = attributeLeftSpace;
	}


	public boolean isCanBeMoved() {
		return canBeMoved;
	}


	public void setCanBeMoved(boolean canBeMoved) {
		this.canBeMoved = canBeMoved;
	}


	public boolean isCanBeResized() {
		return canBeResized;
	}


	public void setCanBeResized(boolean canBeResized) {
		this.canBeResized = canBeResized;
	}


	public boolean isDisplayAttributes() {
		return displayAttributes;
	}
	
	/**
	 * Get the number of attributes
	 * @return
	 */
	public int getAttributeCount()
	{
		return attributeList.size();
	}
	
	
	/**
	 * Get the Attribute of the attribute list at the index
	 * @param index
	 * @return
	 */
	public Attribute getAttribute(int index)
	{
		return attributeList.get(index);
	}

}
