package org.gemsjax.client.model.metamodel;

import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.gemsjax.client.canvas.ConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.editor.MetaModelCanvas;

/**
 * A Connection is used to represent a connection like association between two {@link MetaClass}es
 * A Connection connects {@link #metaClassA} with {@link #metaClassB} and vice versa. 
 * So there is not a start/source or end/target. {@link #metaClassA} and {@link #metaClassB} are on a par.
 * @author Hannes Dorfmann
 *
 */
public class Connection {
	
	/**
	 * One end of the {@link Connection}
	 */
	private MetaClass metaClassA;
	/**
	 * The other end of the {@link Connection}
	 */
	private MetaClass metaClassB;
	
	/** The name of this  connection. The name must be unique in the {@link MetaModel */
	private String name;
	
	
	
	/**
	 * The X coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassA}.
	 * This coordinate is relative to the {@link #metaClassA} {@link MetaClassDrawable} object on the {@link MetaModelCanvas}.
	 * That means, that the {@link MetaClass#getX()} is the relative 0 coordinate on the x axis.
	 * Example:
	 * If the {@link MetaClass#getX()} has the absolute coordinate 10 and {@link #aRelativeX} is 5, so the
	 * absolute coordinate on the {@link MetaModelCanvas} is 15 and can be computed by add {@link MetaClass#getX()} to aRelativeX
	 *@see #getMetaClassARelativeX()
	 */
	private double aRelativeX;
	
	/**
	 * The Y coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassA}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassARelativeY()
	 */
	private double aRelativeY;
	
	/**
	 * The X coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassB}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassBRelativeX()
	 */
	private double bRelativeX;
	
	/**
	 * The Y coordinate where the {@link ConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #metaClassB}.
	 * See {@link #aRelativeX} for more information and a concrete example.
	 * @see #getMetaClassBRelativeY()
	 */
	private double bRelativeY;
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #nameBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #metaClassA}, coordinates {@link #aRelativeX} / {@link #aNameBoxRelativeY}) touches
	 * the name box (which displayes the {@link #name} of this Connection.
	 */
	private double aNameBoxRelativeX;
	
	/**
	 * The relative Y coordinate (according to the absolute coordinate {@link #nameBoxY}) 
	 * where the painted connection line
	 * (starting from {@link #metaClassA}, coordinates {@link #aRelativeX} / {@link #aNameBoxRelativeY}) touches
	 * the name box (which displayes the {@link #name} of this Connection.
	 */
	private double aNameBoxRelativeY;
	
	/**
	 * The relative X coordinate (according to the absolute coordinate {@link #nameBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #metaClassB}, coordinates {@link #bRelativeX} / {@link #bNameBoxRelativeY}) touches
	 * the name box (which displayes the {@link #name} of this Connection.
	 */
	private double bNameBoxRelativeX;
	
	/**
	 * The relative Y coordinate (according to the absolute coordinate {@link #nameBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #metaClassB}, coordinates {@link #bRelativeX} / {@link #bNameBoxRelativeY}) touches
	 * the name box (which displayes the {@link #name} of this Connection.
	 */
	private double bNameBoxRelativeY;
	
	private String lineColor = "black";
	
	private String fontColor = "black";
	
	private int lineSize = 1;
	
	private String gradientStartColor ="#5c9967";
	private String gradientEndColor="#ecf2ee";
	
	private boolean selected = false;
	
	/**
	 * The absolute X coordinate, where the box, which displays the connection name, will start at the top-left corner
	 */
	private double nameBoxX;
	/**
	 * The absolute Y coordinate, which displays the connection name,  where the box will start at the top-left corner
	 */
	private double nameBoxY;
	
	/**
	 * The width of the name box, which displays the connection name
	 */
	private double nameBoxWidth;
	
	/**
	 * The height of the name box,  which displays the connection name
	 */
	private double nameBoxHeight;
	
	/**
	 * The Z index (like known from CSS) for overlapping objects
	 */
	private double zIndex;
	
	
	/**
	 * The font size 
	 */
	private int fontSize = 14;
	
	/**
	 * This field will store the width of a single character in the given {@link #fontFamily}.
	 * This field is used to calculate the width of the meta class name.
	 * @see #fontFamily
	 */
	private double fontCharWidth = 9;
	
	
	/**
	 * The font family name. For an easier calculation of the width of a text you should allways use a monospace font.
	 * <b>If you change the font, you also have to recalculate {@link #nameFontCharWidth} and {@link #attributeFontCharWidth}. <b>
	 * @see MetaClass#nameFontCharWidth
	 */
	private String fontFamily ="Courier";
	
	
	/**
	 * Creates a new connection between the MetaClass a (stored in the field {@link #metaClassA}) and the {@link MetaClass} b (stored in the field {@link #metaClassB}).
	 * @param a
	 * @param b
	 */
	public Connection(MetaClass a, MetaClass b)
	{
		this.metaClassA = a;
		this.metaClassB = b;
		
	}


	
	public MetaClass getMetaClassA() {
		return metaClassA;
	}


	public void setMetaClassA(MetaClass metaClassA) {
		this.metaClassA = metaClassA;
	}


	public MetaClass getMetaClassB() {
		return metaClassB;
	}


	public void setMetaClassB(MetaClass metaClassB) {
		this.metaClassB = metaClassB;
	}


	/**
	 * @see #aRelativeX
	 * @return
	 */
	public double getMetaClassARelativeX() {
		return aRelativeX;
	}


	/**
	 * @see #aRelativeX
	 * @param aRelativeX
	 */
	public void setMetaClassARelativeX(double aRelativeX) {
		this.aRelativeX = aRelativeX;
	}


	/**
	 * @see #aRelativeY
	 * @return
	 */
	public double getMetaClassARelativeY() {
		return aRelativeY;
	}

	/**
	 * @see #aRelativeY
	 * @param aRelativeY
	 */
	public void setMetaClassARelativeY(double aRelativeY) {
		this.aRelativeY = aRelativeY;
	}


	/**
	 * @see #bRelativeX
	 * @return
	 */
	public double getMetaClassBRelativeX() {
		return bRelativeX;
	}

	/**
	 * @see #bRelativeX
	 * @param bRelativeX
	 */
	public void setMetaClassBelativeX(double bRelativeX) {
		this.bRelativeX = bRelativeX;
	}


	/**
	 * @see #bRelativeY
	 * @return
	 */
	public double getMetaClassBRelativeY() {
		return bRelativeY;
	}


	/**
	 * @see #bRelativeY
	 * @param bRelativeY
	 */
	public void setMetaClassBRelativeY(double bRelativeY) {
		this.bRelativeY = bRelativeY;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public double getaRelativeX() {
		return aRelativeX;
	}



	public void setaRelativeX(double aRelativeX) {
		this.aRelativeX = aRelativeX;
	}



	public double getaRelativeY() {
		return aRelativeY;
	}



	public void setaRelativeY(double aRelativeY) {
		this.aRelativeY = aRelativeY;
	}



	public double getbRelativeX() {
		return bRelativeX;
	}



	public void setbRelativeX(double bRelativeX) {
		this.bRelativeX = bRelativeX;
	}



	public double getbRelativeY() {
		return bRelativeY;
	}



	public void setbRelativeY(double bRelativeY) {
		this.bRelativeY = bRelativeY;
	}



	public double getaNameBoxRelativeX() {
		return aNameBoxRelativeX;
	}



	public void setaNameBoxRelativeX(double aNameBoxRelativeX) {
		this.aNameBoxRelativeX = aNameBoxRelativeX;
	}



	public double getaNameBoxRelativeY() {
		return aNameBoxRelativeY;
	}



	public void setaNameBoxRelativeY(double aNameBoxRelativeY) {
		this.aNameBoxRelativeY = aNameBoxRelativeY;
	}



	public double getbNameBoxRelativeX() {
		return bNameBoxRelativeX;
	}



	public void setbNameBoxRelativeX(double bNameBoxRelativeX) {
		this.bNameBoxRelativeX = bNameBoxRelativeX;
	}



	public double getbNameBoxRelativeY() {
		return bNameBoxRelativeY;
	}



	public void setbNameBoxRelativeY(double bNameBoxRelativeY) {
		this.bNameBoxRelativeY = bNameBoxRelativeY;
	}



	public String getLineColor() {
		return lineColor;
	}



	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}



	public int getLineSize() {
		return lineSize;
	}



	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
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



	public double getNameBoxX() {
		return nameBoxX;
	}



	public void setNameBoxX(double nameBoxX) {
		this.nameBoxX = nameBoxX;
	}



	public double getNameBoxY() {
		return nameBoxY;
	}



	public void setNameBoxY(double nameBoxY) {
		this.nameBoxY = nameBoxY;
	}



	public double getNameBoxWidth() {
		return nameBoxWidth;
	}



	public void setNameBoxWidth(double nameBoxWidth) {
		this.nameBoxWidth = nameBoxWidth;
	}



	public double getNameBoxHeight() {
		return nameBoxHeight;
	}



	public void setNameBoxHeight(double nameBoxHeight) {
		this.nameBoxHeight = nameBoxHeight;
	}



	public double getZIndex() {
		return zIndex;
	}



	public void setzIndex(double zIndex) {
		this.zIndex = zIndex;
	}



	public int getFontSize() {
		return fontSize;
	}



	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}



	public double getFontCharWidth() {
		return fontCharWidth;
	}



	public void setFontCharWidth(double fontCharWidth) {
		this.fontCharWidth = fontCharWidth;
	}



	public String getFontFamily() {
		return fontFamily;
	}



	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}



	public String getFontColor() {
		return fontColor;
	}



	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}



	public boolean isSelected() {
		return selected;
	}



	public void setSelected(boolean selected) {
		this.selected = selected;
	}



}
