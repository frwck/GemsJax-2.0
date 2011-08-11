package org.gemsjax.shared.metamodel;

import java.util.List;

import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;


/**
 * The common interface for the meta model attribute implementation on client and server side.
 * We define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing a common class only one time, but can be used both with the concrete
 * server implementation and the concrete client implementation.
 * 
 * <br /><br />
 * A MetaConnection models a connection / relationship type between two {@link MetaClass}es. 
 * Its only possible to model a one - way connection, that means, that a bidirectional connection can not be
 * modeled directly by one {@link MetaConnection}, 
 * because the MetaConnection has only a target {@link MetaClass}. 
 * 
 * If you want to model a bidirectional MetaConnection between {@link MetaClass} A and {@link MetaClass} B, you must
 * create two {@link MetaConnection} objects. The first must be owned by {@link MetaClass} A  ({@link MetaClass#addConnection(MetaConnection)})
 * and the target must be set to {@link MetaClass} B and the second MetaConnection object must 
 *  be owned by {@link MetaClass} B  ({@link MetaClass#addConnection(MetaConnection)})
 * and the target must be set to {@link MetaClass} A.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface MetaConnection extends MetaModelElement{

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

	public int getTargetLowerBound();
	public void setTargetLowerBound(int lower);
	
	public int getTargetUpperBound();
	public void setTargetUpperBound(int upper);
	
	public void setTarget(MetaClass target);
	public MetaClass getTarget();
	
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type) throws MetaAttributeException;
	
	/**
	 * <b> Use this just as a read only list!</b> <br />
	 * Use {@link #addAttribute(String, String, MetaBaseType)} and {@link #removeAttribute(MetaAttribute)} to manipulate this list,
	 * but never manipulate this list directly
	 * @return
	 */
	public List<MetaAttribute> getAttributes();
	
	public void removeAttribute(MetaAttribute attribute);
	
	
	/**
	 * Get the icon, that is painted on the "target" end of this connection
	 * @return
	 */
	public String getTargetIcon();
	
	/**
	 * Set the icon, that is displayed on the target end of this connection
	 * @param url
	 */
	public void setTargetIcon(String url);
	
	/**
	 * Get the icon, that is displayed on the "source" end of this connection
	 * @return
	 */
	public String getSourceIcon();
	
	/**
	 * Set the icon, that is displayed on the "source" end of this connection
	 * @param url
	 */
	public void setSourceIcon(String url);
	
	/**
	 * Get the current width of the icon. That is not the width of the icon itself, but the width, 
	 * with that the icon is painted on the canvas by scaling it to this width
	 * @return
	 */
	public double getTargetIconWidth();
	
	/**
	 * Get the current height of the icon. That is not the height of the icon itself, but the height, 
	 * with that the icon is painted on the canvas by scaling it to this height
	 * @return
	 */
	public double getTargetIconHeight();
	public void setTargetIconWidth(double width);
	/**
	 * /**
	 * Get the current height of the icon. That is not the height of the icon itself, but the height, 
	 * with that the icon is painted on the canvas by scaling it to this height
	 
	 * @param height
	 */
	public void setTagetIconHeight(double height);
	
	/**
	 * Get the current width of the icon. That is not the width of the icon itself, but the width, 
	 * with that the icon is painted on the canvas by scaling it to this width
	 * @return
	 */
	public double getSourceIconWidth();
	
	/**
	 * Get the current height of the icon. That is not the height of the icon itself, but the height, 
	 * with that the icon is painted on the canvas by scaling it to this height
	 * @return
	 */
	public double getSourceIconHeight();
	
	/**
	 * Get the current width of the icon. That is not the width of the icon itself, but the width, 
	 * with that the icon is painted on the canvas by scaling it to this height
	 * @return
	 *
	 * @param width
	 */
	public void setSourceIconWidth(double width);
	
	/**
	 * Get the current height of the icon. That is not the height of the icon itself, but the height, 
	 * with that the icon is painted on the canvas by scaling it to this height
	 * @param height
	 */
	public void setSourceIconHeight(double height);
	
	/**
	 * The X coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the source.
	 * The source is the {@link MetaClass} which contains this MetaConnection in {@link MetaClass#getConnections()}.
	 * This coordinate is relative to the source {@link MetaClassDrawable} object on the {@link MetaModelCanvas}.
	 * That means, that the {@link MetaClassImpl#getX()} is the relative 0 coordinate on the x axis.
	 * Example:
	 * If the {@link MetaClassImpl#getX()} has the absolute coordinate 10 and {@link #getSourceRelativeX()} is 5, so the
	 * absolute coordinate on the {@link MetaModelCanvas} is 15 and can be computed by add {@link MetaClassImpl#getX()} to aRelativeX
	 * @return
	 */
	public double getSourceRelativeX();
	
	/**
	 * Get the width of a single char for attributes.
	 * This value is needed to compute the width of a attribute text
	 * @return
	 */
	public double getAttributeFontCharWidht();
	
	/**
	 * @see #getAttributeFontCharWidht()
	 * @param attributeFontSize
	 */
	public void setAttributeFontSize(double attributeFontSize);
	
	
	public double getAttributeFontSize();
	
	/**
	 * Get the width of a single char for connections name.
	 * This value is needed to compute the width of the name
	 * @return
	 */
	public double getNameFontCharWidth();
	
	
	public String getFontColor() ;
	
	
	public String getFontFamily();
	
	
	public int getNameFontSize();
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #nameBoxX}) 
	 * where the painted connection line
	 * (starting from the source with the coordinates {@link #getSourceRelativeX()} / {@link #getsourceRelativeY()}) touches
	 * the connection box (which displays the {@link #getName()} and attributes of this Connection.
	 * @return
	 */
	public double getSourceConnectionBoxRelativeX();
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #nameBoxX}) 
	 * where the painted connection line
	 * (starting from the source with the coordinates {@link #getSourceRelativeX()} / {@link #getSourceRelativeY()}) touches
	 * the connection box (which displays the {@link #getName()} and attributes of this Connection.
	 * @return
	 */
	public double getSourceConnectionBoxRelativeY();
	
	
	/**
	 * The Y coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the source.
	 * See {@link #getSourceRelativeX()} for more information and a concrete example.
	 * @see #getSourceRelativeY()
	 */
	public double getSourceRelativeY();
	
	/**
	 * The background color gradient end color
	 * @return
	 */
	public String getGradientEndColor();
	
	/**
	 * The background color gradient start color
	 * @return
	 */
	public String getGradientStartColor();
	
	/**
	 * Get the line color
	 * @return
	 */
	public String getLineColor();
	
	public int getLineSize();
	
	/**
	 * The height of the connection box, which displays the connection name and attributes
	 * @return
	 */
	public double getConnectionBoxHeight();
	
	/**
	 * The width of the connection box, which displays the connection name and attributes
	 * @return
	 */
	public double getConnectionBoxWidth();
	
	/**
	 * The absolute X coordinate, where the box, which displays the connection name and attributes (called connection box), starts at the top-left corner
	 *@return
	 */
	public double getConnectionBoxX();
	
	/**
	 * The absolute Y coordinate, where the box, which displays the connection name and attributes (called connection box), starts at the top-left corner
	 *@return
	 */
	public double getConnectionBoxY() ;
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #getConnectionBoxX()}) 
	 * where the painted connection line
	 * (starting from {@link #getTarget()}, coordinates {@link #getTargetRelativeX()} / {@link #getTargetRelativeY()}) touches
	 * the connection box (which displays the name and attributes of this Connection.
	 */
	public double getTargetConnectionBoxRelativeX();
	
	/**
	 * The relative Y coordinate (according to the absolute coordinate {@link #getConnectionBoxX()}) 
	 * where the painted connection line
	 * (starting from {@link #getTarget()}, coordinates {@link #getTargetRelativeX()} / {@link #getTargetRelativeY()}) touches
	 * the connection box (which displays the name and attributes of this Connection.
	 */
	public double getTargetConnectionBoxRelativeY();
	
	
	/**
	 * The X coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the target.
	 * See {@link #getSourceRelativeX()} for more information and a concrete example.
	 * @see #getTargetRelativeX()
	 */
	public double getTargetRelativeX();
	
	/**
	 * The Y coordinate where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the target.
	 * See {@link #getSourceRelativeX()} for more information and a concrete example.
	 * @see #getTargetRelativeX()
	 */
	public double getTargetRelativeY();
	
	/**
	 * For overlapping, same functionality as CSS z index
	 * @return
	 */
	public double getZIndex();
	
	
	public boolean isSelected();
	
	
	public boolean isDisplayingAttributes();
	
	public void setDisplayingAttributes(boolean show);
	
	/**
	 * @see #getAttributeFontCharWidht()
	 * @param attributeFontCharWidht
	 */
	public void setAttributeFontCharWidht(double attributeFontCharWidht);
	
	/**
	 * @see #getConnectionBoxHeight()
	 * @param height
	 */
	public void setConnectionBoxHeight(double height);
	
	/**
	 * @see #getConnectionBoxWidth()
	 * @param width
	 */
	public void setConnectionBoxWidth(double width);
	
	/**
	 * @see #getConnectionBoxX()
	 * @param x
	 */
	public void setConnectionBoxX(double x);
	
	/**
	 * @see #getConnectionBoxY()
	 * @param y
	 */
	public void setConnectionBoxY(double y);
	
	/**
	 * @see #getNameFontCharWidth()
	 * @param fontCharWidth
	 */
	public void setNameFontCharWidth(double fontCharWidth);
	
	/**
	 * 
	 * @param fontColor Color in all acceptable CSS forms
	 */
	public void setFontColor(String fontColor);
	
	/**
	 * 
	 * @param fontFamily
	 */
	public void setFontFamily(String fontFamily);
	
	/**
	 * Font size for the name. 
	 * <br /><b>Remember to adjust the font char width by changing the font size ({@link #setNameFontCharWidth(double)})</b>
	 * @param fontSize
	 */
	public void setNameFontSize(int fontSize);
	
	
	public void setSelected(boolean selected);
	
	/**
	 * @see #getSourceConnectionBoxRelativeX()
	 * @param x
	 */
	public void setSourceConnectionBoxRelativeX(double x);
	
	/**
	 * @see #getSourceConnectionBoxRelativeY()
	 * @param y
	 */
	public void setSourceConnectionBoxRelativeY(double y);
	
	/**
	 * @see #getSourceRelativeX()
	 * @param x
	 */
	public void setSourceRelativeX(double x);
	
	/**
	 * @see #getSourceRelativeY()
	 * @param y
	 */
	public void setSourceRelativeY(double y);
	
	/**
	 * @see #getTargetConnectionBoxRelativeX()
	 * @param x
	 */
	public void setTargetConnectionBoxRelativeX(double x);
	
	/**
	 * @see #getTargetConnectionBoxRelativeY()
	 * @param bNameBoxRelativeY
	 */
	public void setTargetConnectionBoxRelativeY(double bNameBoxRelativeY) ;
	
	/**
	 * @see #getTargetRelativeX()
	 * @param x
	 */
	public void setTargetRelativeX(double x);
	
	/**
	 * @see #getTargetRelativeY()
	 * @param y
	 */
	public void setTargetRelativeY(double y);
	
	/**
	 * @see #getZIndex()
	 * @param zIndex
	 */
	public void setzIndex(double zIndex);
	
	/**
	 * @see #getGradientStartColor()
	 * @param gradientStartColor HTML color 
	 */
	public void setGradientStartColor(String gradientStartColor);
	
	/**
	 * @see #getGradientEndColor()
	 * @param gradientEndColor
	 */
	public void setGradientEndColor(String gradientEndColor);
	
	/**
	 * @see #getLineSize()
	 * @param lineSize
	 */
	public void setLineSize(int lineSize) ;
	
	/**
	 * @see #getLineColor()
	 * @param lineColor
	 */
	public void setLineColor(String lineColor) ;
	
	/**
	 * Set the source {@link MetaClass}, where this connection starts
	 * @see #setTarget(MetaClass)
	 * @param source
	 */
	public void setSource(MetaClass source);
	
	/**
	 * @see #setSource(MetaClass)
	 * @return
	 */
	public MetaClass getSource();
}


