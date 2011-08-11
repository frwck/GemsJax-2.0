package org.gemsjax.shared.metamodel;

import java.util.List;

import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.metamodel.exception.MetaContainmentRelationException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;

/** The common interface for the meta model class implementation on client and server side.
* We define a common api that can be used for implementing concrete objects on server and client.
* We also can implement common classes (for example to check the validity of a model) that can be used on
* server and client side, by implementing a common class only one time, but can be used both with the concrete
* server implementation and the concrete client implementation of this MetaClass
* @author Hannes Dorfmanns
*/
public interface MetaClass extends MetaModelElement{

	
	/**
	 * Get the name of this element
	 * @return
	 */
	public String getName();
	
	/**
	 * Set the name of this element.
	 * <b>Notice:</b> The name must also be unique in a MetaModel
	 * @param name
	 * @return
	 */
	public void setName(String name);

	/**
	 * Create and add a {@link MetaAttribute} to this MetaClass
	 * @param id
	 * @param name
	 * @param type
	 * @return The added and new creates {@link MetaAttribute}
	 * @throws MetaAttributeException
	 */
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type) throws MetaAttributeException;
	
	/**
	 * Create and add a new {@link MetaConnection}
	 * @param id
	 * @param name
	 * @param target
	 * @param lower
	 * @param upper
	 * @return The new created and added MetaConnection object
	 * @throws MetaConnectionException if a MetaConnection with the same name exists.
	 */
	public MetaConnection addConnection(String id, String name, MetaClass target, int lower, int upper)  throws MetaConnectionException;
	
	/**
	 * Create and add a new {@link MetaContainmentRelation} object
	 * @param id
	 * @param classToContain The MetaClass which is contained 
	 * @param max
	 * @param min
	 * @return The new created {@link MetaContainmentRelation} object
	 */
	public MetaContainmentRelation addContainmentRelation(String id, MetaClass classToContain, int max, int min) throws MetaContainmentRelationException;
	
	
	/**
	 * create a new inheritance relation between this {@link MetaClass} and the other super MetaClass (parameter).
	 * That means that this Meta class derives attributes from the super MetaClass (parameter).
	 * @param superMetaClass
	 * @throws MetaInheritanceExcepetion If a inheritance relation for the same superMetaClass already exists
	 */
	public void addInheritance(MetaClass superMetaClass) throws MetaInheritanceExcepetion;
	
	/**
	 * <b> Use this just as a read only list!</b> <br />
	 * Use {@link #addAttribute(String, String, MetaBaseType)} and {@link #removeAttribute(MetaAttribute)} to manipulate this list,
	 * but never manipulate this list directly
	 * @return
	 */
	public List<MetaAttribute> getAttributes();
	
	/**
	 * <b> Use this just as a read only list!</b> <br />
	 * Use {@link #addConnection(String, String, MetaClass, int, int)} and {@link #removeConnection(MetaConnection)} to manipulate this list,
	 * but never manipulate this list directly
	 * @return
	 */
	public List<MetaConnection> getConnections();
	
	/**
	 * <b> Use this just as a read only list!</b> <br />
	 * Use {@link #addContainmentRelation(String, int, int)} and {@link #removeContainmentRelation(MetaContainmentRelation)} to manipulate this list,
	 * but never manipulate this list directly
	 * @return
	 */
	public List<MetaContainmentRelation> getContainmentRelations();
	
	/**
	 * Get a List with all super classes / base types. That means, that this list contains all classes/ base types
	 * from which this MetaClass derives.
	 * @return
	 */
	public List<MetaClass> getInheritances();
	
	public void removeAttribute(MetaAttribute attribute);
	public void removeConnection(MetaConnection connection);
	public void removeContainmentRelation(MetaContainmentRelation relation);
	public void removeInheritance(MetaClass type);
 
	
	// Properties for the painting
	
	/**
	 * Get the with of a single character in the current attribute font.
	 * That is needed to calculate the with of a attribute on the canvas
	 */
	public double getAttributeFontCharWidth();
	
	/**
	 * Get the font color of the attributes
	 * @return
	 */
	public String getAttributeFontColor();
	/**
	 * The attribute font size
	 * @return
	 */
	public int getAttributeFontSize();
	
	/**
	 * Get the space between the left border and the point where the painted list of attributes starts (on the left)
	 * @return
	 */
	public double getAttributeListLeftSpace();
	
	/**
	 * Get the space between the bottom border and the point where the painted list of attributes ends (on the bottom)
	 * @return
	 */
	public double getAttributeListBottomSpace();
	
	/**
	 * Get the space between the name separator line and the point where the painted list of attributes starts (on the top)
	 * @return
	 */
	public double getAttributeListTopSpace();
	/**
	 * Get the space between the right border and the point where the painted list of attributes starts (on the right)
	 * @return
	 */
	public double getAttributeListRightSpace();
	
	/**
	 * Get the horizontal space between each attribut in the painter Attribute list
	 * @return
	 */
	public double getAttributeToAttributeSpace();
	
	/**
	 * The border color
	 * @return
	 */
	public String getBorderColor();
	
	/**
	 * The border size
	 * @return
	 */
	public int getBorderSize();
	
	/**
	 * The font that is used to draw text on the canvas (Name and Attributes)
	 * @return
	 */
	public String getFontFamily();
	
	/**
	 * The background color with which the background gradient ends
	 * @return
	 */
	public String getGradientEndColor();
	/**
	 * The background color with which the background gradient starts
	 * @return
	 */
	public String getGradientStartColor();
	
	/**
	 * The current height of the Drawable for this object in pixel
	 * @return
	 */
	public double getHeight();
	
	/**
	 * Get the minimum height. The Drawable for this object on the canvas can not be smaller that this value (in pixel)
	 * @return
	 */
	public double getMinHeight();
	
	/**
	 * Get the minimum width. The Drawable for this object on the canvas can not be smaller that this value (in pixel)
	 * @return
	 */
	public double getMinWidth();
	
	/**
	 * The space in pixel between the top border and the top of the text
	 * @return
	 */
	public double getNameBottomSpace();
	
	/**
	 * Get the width (in pixel) of a single char for the current font and font size of the name.
	 * This value is needed to calculate the width of the name
	 * @return
	 */
	public double getNameFontCharWidth();
	
	/**
	 * Font color of the name
	 * @return
	 */
	public String getNameFontColor();
	/**
	 * The font size of the name
	 * @return
	 */
	public int getNameFontSize();
	
	/**
	 * The space between the left border and the name text
	 * @return
	 */
	public double getNameLeftSpace();
	
	/**
	 * The space between the right border and the name
	 * @return
	 */
	public double getNameRightSpace();
	
	/**
	 * The space between the top border and the name
	 * @return
	 */
	public double getNameTopSpace() ;
	
	/**
	 * The current with of this drawable object
	 * @return
	 */
	public double getWidth();
	
	/**
	 * The x coordinate of the top - left corner of this object drawable on the canvas
	 * @return
	 */
	public double getX();
	/**
	 * The y coordinate of the top - left corner of this object drawable on the canvas
	 * @return
	 */
	public double getY();
	
	/**
	 * The z index is similar to the z index in CSS, because drawable objects can be overlapped coordinate of the top - left corner of this object drawable on the canvason the canvas
	 * @return
	 */
	public double getZIndex();
	
	/**
	 * Get the url to the icon. The icon (if set) is displayed on the canvas
	 * @return The URL to the icon or null if no icon is set
	 */
	public String getIconURL();
	
	/**
	 * Get the width of the rectangle where the icon is painted in.
	 * Don't get confused: This value is not the width of the icon (image) itself,
	 * but it defines, the width the icon is scaled to.
	 * Example: The icon have a width of 300 px, but this {@link #setIconMaxWidth(double)} value is set to 200,
	 * then the icon itself will be scaled down to a width of 200.
	 * @return
	 */
	public double getIconWidth();
	
	/**
	 * Get the max height of the rectangle where the icon is painted in
	 * @see #getIconMaxWidth()
	 * @return
	 */
	public double getIconHeight();
	
	
	
	/**
	 * Should the Attributes be displayed on the canvas or should they be hidden
	 * @return
	 */
	public boolean isDisplayingAttributes();
	
	public boolean isSelected();
	
	public boolean isAbstract();
	
	public void setAbstract(boolean abstr);
	
	/**
	 * @see #getAttributeFontCharWidth()
	 * @param attributeFontCharWidth
	 */
	public void setAttributeFontCharWidth(double attributeFontCharWidth);
	
	/**
	 * 
	 * @param attributeFontColor
	 * @see #getAttributeFontColor()
	 */
	public void setAttributeFontColor(String attributeFontColor);
	/**
	 * @see #getAttributeFontSize()
	 * @param attributeFontSize
	 */
	public void setAttributeFontSize(int attributeFontSize);
	
	/**
	 * @see #getAttributeListLeftSpace()
	 * @param attributeLeftSpace
	 */
	public void setAttributeListLeftSpace(double attributeLeftSpace);
	/**
	 * @see #getAttributeLisBottomSpace()
	 * @param attributeListBottomSpace
	 */
	public void setAttributeListBottomSpace(double attributeListBottomSpace);
	
	/**
	 * @see #getAttributeListTopSpace()
	 * @param attributeListTopSpace
	 */
	public void setAttributeListTopSpace(double attributeListTopSpace);
	
	/**
	 * @see #getAttributeListRightSpace()
	 * @param attributeRightSpace
	 */
	public void setAttributeListRightSpace(double attributeRightSpace) ;
	
	/**
	 * @see #getAttributeToAttributeSpace()
	 * @param attributeToAttributeSpace
	 */
	public void setAttributeToAttributeSpace(double attributeToAttributeSpace);
	
	/**
	 * @see #getBorderColor()
	 * @param borderColor
	 */
	public void setBorderColor(String borderColor);
	
	/**
	 * @see #getBorderSize()
	 * @param borderSize
	 */
	public void setBorderSize(int borderSize);
	/**
	 * @see #isDisplayingAttributes()
	 * @param show
	 */
	public void setDisplayAttributes(boolean show);
	/**
	 * @see #getFontFamily()
	 * @param fontFamily
	 */
	public void setFontFamily(String fontFamily);
	
	/**
	 * @see #getGradientEndColor()
	 * @param gradientEndColor
	 */
	public void setGradientEndColor(String gradientEndColor);
	/**
	 * @see #getGradientStartColor()
	 * @param gradientStartColor
	 */
	public void setGradientStartColor(String gradientStartColor);
	
	/**
	 * @see #getHeight()
	 * @param height
	 */
	public void setHeight(double height);
	
	/**
	 * @see #getMinHeight()
	 * @param minHeight
	 */
	public void setMinHeight(double minHeight);
	
	/**
	 * @see #getMinWidth()
	 * @param minWidth
	 */
	public void setMinWidth(double minWidth);
	
	/**
	 * @see #getNameBottomSpace()
	 * @param nameBottomSpace
	 */
	public void setNameBottomSpace(double nameBottomSpace) ;
	/**
	 * @see #getNameFontCharWidth()
	 * @param nameBottomSpace
	 */
	public void setNameFontCharWidth(double nameFontCharWidth);
	/**
	 * @see #getNameFontColor()
	 * @param nameBottomSpace
	 */
	public void setNameFontColor(String nameFontColor);
	/**
	 * @see #getNameFontSize()
	 * @param nameBottomSpace
	 */
	public void setNameFontSize(int nameFontSize) ;
	/**
	 * @see #getNameLeftSpace()
	 * @param nameBottomSpace
	 */
	public void setNameLeftSpace(double nameLeftSpace);
	/**
	 * @see #getNameRightSpace()
	 * @param nameBottomSpace
	 */
	public void setNameRightSpace(double nameRightSpace);
	/**
	 * @see #getNameTopSpace()
	 * @param nameTopSpace
	 */
	public void setNameTopSpace(double nameTopSpace);
	
	/**
	 * @see #isSelected()
	 * @param selected
	 */
	public void setSelected(boolean selected);
	
	/**
	 * @see #getWidth()
	 * @param width
	 */
	public void setWidth(double width);
	/**
	 * @see #getX()
	 * @param nameBottomSpace
	 */
	public void setX(double x);
	/**
	 * @see #getY()
	 * @param nameBottomSpace
	 */
	public void setY(double y);
	
	/**
	 * @see #getZIndex()
	 * @param nameBottomSpace
	 */
	public void setZIndex(double z);
	
	
	/**
	 * @see #getIconURL()
	 * @param url
	 */
	public void setIconURL(String url);
	
	/**
	 * @see #getIconMaxWidth()
	 * @param width
	 */
	public void setIconWidth(double width);
	
	/**
	 * @see #getIconHeight()
	 * @param height
	 */
	public void setIconHeight(double height);
		
	
	
}
