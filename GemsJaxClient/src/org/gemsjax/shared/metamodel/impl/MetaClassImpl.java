package org.gemsjax.shared.metamodel.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaContainmentRelation;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.metamodel.exception.MetaContainmentRelationException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;



/**
 * This class represents a MetaClass. A MetaClass must be added to a {@link MetaModelImpl} and sho
 * @author Hannes Dorfmann
 *
 */
public class MetaClassImpl implements MetaClass {

	/** The unique ID */
	private String id;
	
	// Drawable fields
	
	private double x, y,z;
	private double width = 100, height = 200, minWidth = 60, minHeight = 80;
	
	private String borderColor = "black";
	
	private int borderSize = 1;
	
	private String gradientStartColor ="#FCCE70";
	private String gradientEndColor="#FCFBD5";
	
	private String iconURL = "/images/icons/defaultMetaClass.png";
	private double iconWidth = 50;
	private double iconHeight = 50;
	
	
	/**
	 * The font family name. For an easier calculation of the width of a text you should allways use a monospace font.
	 * <b>If you change the font, you also have to recalculate {@link #nameFontCharWidth} and {@link #attributeFontCharWidth}. <b>
	 * @see MetaClassImpl#nameFontCharWidth
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
	 * The horizontal space, between the box, and the icon
	 */
	private double iconToClassBoxSpace = 10;
	
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

	private boolean selected;
	
		
	// MetaClass Data fields

	/**
	 * The name of this MetaClass
	 */
	private String name;
	
	/**
	 * Is this MetaClass abstract?
	 */
	private boolean isAbstract;
	
	private List <MetaAttribute>attributes;
	
	private List <MetaConnection> connections;
	
	private List<MetaInheritance> inheritances;
	
	private List<MetaContainmentRelation> containments;
	
	
	
	private Set<CollaborateableElementPropertiesListener> listeners;
	
	private Map<String, MetaAttribute> attributeMap;
	
	public MetaClassImpl(){
		 // Lists
		 attributes = new ArrayList<MetaAttribute>();
		 inheritances = new ArrayList<MetaInheritance>();
		 connections = new ArrayList<MetaConnection>();
		 containments = new ArrayList<MetaContainmentRelation>();
		 attributeMap = new ConcurrentHashMap<String, MetaAttribute>();
		 listeners = new LinkedHashSet<CollaborateableElementPropertiesListener>();
	}
	

	public MetaClassImpl(String id, String name, double x, double y)
	{
		
		this(id,x,y);
		this.name = name;
	}
	
	
	public MetaClassImpl(String id, String name)
	{
		this(id, 100,100);
		this.name = name;
	}
		
	public MetaClassImpl(String id, double x, double y) {
		this();
		this.id = id;
		this.x = x;
		this.y = y;
		 
		 selected = false;
		 
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
	public void setX(double x) {
		this.x = x;
		
	}

	@Override
	public void setY(double y) {
		this.y=y;
	}

	@Override
	public void setZIndex(double z) {
		this.z=z;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
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
	public String getBorderColor() {
		return borderColor;
	}
	
	@Override
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	@Override
	public int getBorderSize() {
		return borderSize;
	}

	@Override
	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
	}


	@Override
	public boolean isAbstract() {
		return isAbstract;
	}
	@Override
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();

	}

	
	
	@Override
	public void addAttribute(MetaAttribute attribute) throws MetaAttributeException
	{
		for(MetaAttribute a : attributes)
			if (attribute == a || a.getID().equals(attribute.getID()) || a.getName().equals(attribute.getName()) )
				throw new MetaAttributeException(name, this);
		
		attributes.add(attribute);
		attributeMap.put(attribute.getID(), attribute);
		
		attribute.addCollaborateableElementPropertiesListeners(listeners);
		
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
	}
	
	@Override
	public void removeAttribute(MetaAttribute attribute)
	{
		attributes.remove(attribute);
		attributeMap.remove(attribute.getID());
		
		attribute.removeCollaborateableElementPropertiesListeners(listeners);
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
	}

	@Override
	public void setDisplayAttributes(boolean show) {
		this.displayAttributes = show;
	}

	@Override
	public String getGradientStartColor() {
		return gradientStartColor;
	}

	@Override
	public void setGradientStartColor(String gradientStartColor) {
		this.gradientStartColor = gradientStartColor;
	}

	@Override
	public String getGradientEndColor() {
		return gradientEndColor;
	}

	@Override
	public void setGradientEndColor(String gradientEndColor) {
		this.gradientEndColor = gradientEndColor;
	}
	
	@Override
	public String getFontFamily() {
		return fontFamily;
	}

	@Override
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	@Override
	public String getNameFontColor() {
		return nameFontColor;
	}

	@Override
	public void setNameFontColor(String nameFontColor) {
		this.nameFontColor = nameFontColor;
	}

	@Override
	public String getAttributeFontColor() {
		return attributeFontColor;
	}

	@Override
	public void setAttributeFontColor(String attributeFontColor) {
		this.attributeFontColor = attributeFontColor;
	}

	@Override
	public int getNameFontSize() {
		return nameFontSize;
	}

	@Override
	public void setNameFontSize(int nameFontSize) {
		this.nameFontSize = nameFontSize;
	}

	@Override
	public int getAttributeFontSize() {
		return attributeFontSize;
	}

	@Override
	public void setAttributeFontSize(int attributeFontSize) {
		this.attributeFontSize = attributeFontSize;
	}

	@Override
	public double getNameFontCharWidth() {
		return nameFontCharWidth;
	}

	@Override
	public void setNameFontCharWidth(double nameFontCharWidth) {
		this.nameFontCharWidth = nameFontCharWidth;
	}

	@Override
	public double getAttributeFontCharWidth() {
		return attributeFontCharWidth;
	}

	@Override
	public void setAttributeFontCharWidth(double attributeFontCharWidth) {
		this.attributeFontCharWidth = attributeFontCharWidth;
	}

	@Override
	public double getAttributeToAttributeSpace() {
		return attributeToAttributeSpace;
	}

	@Override
	public void setAttributeToAttributeSpace(double attributeToAttributeSpace) {
		this.attributeToAttributeSpace = attributeToAttributeSpace;
	}

	@Override
	public double getAttributeListTopSpace() {
		return attributeListTopSpace;
	}

	@Override
	public void setAttributeListTopSpace(double attributeListTopSpace) {
		this.attributeListTopSpace = attributeListTopSpace;
	}

	@Override
	public double getAttributeListBottomSpace() {
		return attributeListBottomSpace;
	}

	@Override
	public void setAttributeListBottomSpace(double attributeListBottomSpace) {
		this.attributeListBottomSpace = attributeListBottomSpace;
	}

	@Override
	public double getNameTopSpace() {
		return nameTopSpace;
	}

	@Override
	public void setNameTopSpace(double nameTopSpace) {
		this.nameTopSpace = nameTopSpace;
	}

	@Override
	public double getNameBottomSpace() {
		return nameBottomSpace;
	}

	@Override
	public void setNameBottomSpace(double nameBottomSpace) {
		this.nameBottomSpace = nameBottomSpace;
	}

	@Override
	public double getNameLeftSpace() {
		return nameLeftSpace;
	}

	@Override
	public void setNameLeftSpace(double nameLeftSpace) {
		this.nameLeftSpace = nameLeftSpace;
	}

	@Override
	public double getNameRightSpace() {
		return nameRightSpace;
	}

	@Override
	public void setNameRightSpace(double nameRightSpace) {
		this.nameRightSpace = nameRightSpace;
	}


	@Override
	public double getAttributeListRightSpace() {
		return attributeRightSpace;
	}

	@Override
	public void setAttributeListRightSpace(double attributeRightSpace) {
		this.attributeRightSpace = attributeRightSpace;
	}

	@Override
	public double getAttributeListLeftSpace() {
		return attributeLeftSpace;
	}

	@Override
	public void setAttributeListLeftSpace(double attributeLeftSpace) {
		this.attributeLeftSpace = attributeLeftSpace;
	}


	@Override
	public boolean isDisplayingAttributes() {
		return displayAttributes;
	}
	
	

	@Override
	public List<MetaAttribute> getAttributes() {
		return attributes;
	}


	@Override
	public List<MetaConnection> getConnections() {
		return connections;
	}


	@Override
	public List<MetaContainmentRelation> getContainmentRelations() {
		return containments;
		
	}


	@Override
	public void removeConnection(MetaConnection connection) {
		connections.remove(connection);	
	}


	@Override
	public void removeContainmentRelation(MetaContainmentRelation relation) {
		containments.remove(relation);
	}

	@Override
	public String getID() {
		return id;
	}


	
	/**
	 * Do not use this directly. Call MetaModel.addConnection() to add a connection
	 */
	@Override
	public void addConnection(MetaConnection connection) throws MetaConnectionException {
		
		for (MetaConnection c : connections)
			if (c.getName().equals(connection.getName()) || c.getID().equals(connection.getID()))
				throw new MetaConnectionException(this,name);
		
		connections.add(connection);
		
			
	}


	@Override
	public void addContainmentRelation(MetaContainmentRelation relation) throws MetaContainmentRelationException
	{
		for (MetaContainmentRelation r : containments)
			if (r.getMetaClass().getID().equals(relation.getID()) || r.getMetaClass().getID().equals(relation.getMetaClass().getID() ) )
				throw new MetaContainmentRelationException(this, relation.getMetaClass());
		
		containments.add(relation);
	}


	@Override
	public void addInheritance(MetaInheritance inheritance) throws MetaInheritanceExcepetion {
		
		for (MetaInheritance c : inheritances)
			if (c.getSuperClass().getID().equals(inheritance.getSuperClass().getID()))
				throw new MetaInheritanceExcepetion(this, inheritance.getSuperClass() );
		
		
		inheritances.add(inheritance);
	}




	@Override
	public double getIconHeight() {
		return iconHeight;
	}


	@Override
	public double getIconWidth() {
		return iconWidth;
	}


	@Override
	public String getIconURL() {
		return iconURL;
	}


	@Override
	public double getZIndex() {
		return z;
	}


	@Override
	public void removeInheritance(MetaInheritance i) {
		inheritances.remove(i);
	}


	@Override
	public void setIconHeight(double height) {
		this.iconHeight = height;
	}


	@Override
	public void setIconWidth(double width) {
		this.iconWidth = width;
	}


	@Override
	public void setIconURL(String url) {
		iconURL = url;
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
	}


	@Override
	public List<MetaInheritance> getInheritances() {
		return inheritances;
	}


	@Override
	public double getIconToClassBoxSpace() {
		return iconToClassBoxSpace;
	}


	@Override
	public void autoSize() {

		// TODO optimization 
		
		double nameWidth = this.getNameLeftSpace() + this.getName().length()*this.getNameFontCharWidth() + this.getNameRightSpace();
		double nameHeight = this.getNameTopSpace() + this.getNameFontSize() + this.getNameBottomSpace();
		
		double attributesHeight = this.getAttributeListTopSpace() + this.getAttributes().size()* (this.getAttributeFontSize() + this.getAttributeToAttributeSpace()) + this.getAttributeListBottomSpace();
		
		
		
		int longestChar = 0;
		
		// Calculate the longest attribute (attribute string)
		for (int i =0; i<this.getAttributes().size();i++)
		{
			MetaAttribute a = this.getAttributes().get(i);
			if ((a.getName().length() + a.getType().getName().length() + 3 )> longestChar)	// + 3 is for the separator String " : " between name and type
				longestChar = a.getName().length() + a.getType().getName().length() + 3;
		}
		
		double attributeWidth = this.getAttributeListLeftSpace() + longestChar *this.getAttributeFontCharWidth() + this.getAttributeListRightSpace();
		
		if (this.isDisplayingAttributes())
		{
			// Set width
			if (attributeWidth>nameWidth)
				this.setWidth( attributeWidth );
			else
				this.setWidth( nameWidth );
		}
		else
			this.setWidth(nameWidth);
			
		// Set height
		
		double height=0;
		
		if (iconURL!=null && !iconURL.equals(""))
			height += iconHeight;
		
		if (isDisplayingAttributes())
			height+= nameHeight + attributesHeight;
		else
			height += nameHeight;
		
		this.setHeight(height); 
		
	}


	@Override
	public void addPropertiesListener(CollaborateableElementPropertiesListener l) {
		listeners.add(l);
		
		for (MetaAttribute a: attributes)
			a.addCollaborateableElementPropertiesListeners(listeners);
	}


	@Override
	public void removePropertiesListener(
			CollaborateableElementPropertiesListener l) {
		listeners.remove(l);
		
		for (MetaAttribute a: attributes)
			a.removeCollaborateableElementPropertiesListeners(listeners);
	}


	@Override
	public boolean isAttributeNameAvailable(String name) {
		for(MetaAttribute a : attributes)
			if (a.getName().equals(name) )
				return false;
		
		return true;
	}


	@Override
	public MetaAttribute getAttributeById(String id) {
		return attributeMap.get(id);
	}


}
