package org.gemsjax.client.metamodel;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.MetaModelCanvas;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;

/**
 * A MetaConnectionImpl is used to represent a connection like association between two {@link MetaClass}es
 * A Connection connects the {@link MetaClass} {@link #source}, which contains this {@link MetaConnection} in {@link MetaClass#getConnections()}, with {@link #target}. 
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionImpl implements MetaConnection {
	
	/**
	 * The target ({@link MetaClass}) of this connection
	 */
	private MetaClass target;
	
	private MetaClass source;
	
	
	/** The name of this  connection. The name must be unique in the {@link MetaModelImpl */
	private String name;
	
	/**
	 * The unique id of this connection
	 */
	private String id;
	
	
	private int targetLowerBound;
	
	private int targetUpperBound;
	
	
	private String sourceIconUrl;
	private String targetIconUrl;
	
	private double sourceIconWidth;
	private double sourceIconHeight;
	
	private double targetIconWidth;
	private double targetIconHeight;
	
	/**
	 * The {@link Point} /  {@link AnchorPoint} where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the source.
	 * The source is the {@link MetaClass} which contains this MetaConnection in {@link MetaClass#getConnections()}.
	 * This coordinate is relative to the source {@link MetaClassDrawable} object on the {@link MetaModelCanvas}.
	 * That means, that the {@link MetaClassImpl#getX()} is the relative 0 coordinate on the x axis.
	 * Example:
	 * If the {@link MetaClassImpl#getX()} has the absolute coordinate 10 and {@link #sourceRelativePoint} with {@link Point#x} is 5, so the
	 * absolute coordinate on the {@link MetaModelCanvas} is 15 and can be computed by add {@link MetaClassImpl#getX()} to {@link Point#x}
	 *@see #getSourceRelativePoint()
	 */
	private AnchorPoint sourceRelativePoint;

	/**
	 * The {@link Point} /  {@link AnchorPoint} where the {@link MetaConnectionDrawable} (which displays this connection in a graphical way) touches the {@link MetaClassDrawable} of the {@link #target}.
	 * See {@link #sourceRelativePoint} for more information and a concrete example.
	 * @see #getTargetRelativePoint()
	 */
	private AnchorPoint targetRelativePoint;
	

	/**
	 * The relative Point /  {@link AnchorPoint} coordinate (according to the absolute coordinate {@link #connectionBoxX}) 
	 * where the painted connection line
	 * (starting from the source with the coordinates {@link #sourceRelativePoint}) touches
	 * the connection box (which displays the {@link #name} and attributes of this Connection.
	 */
	private AnchorPoint sourceConnectionBoxRelativePoint;
	

	/**
	 * The relative Point  / {@link AnchorPoint}  (according to the absolute coordinate {@link #connectionBoxX}) 
	 * where the painted connection line
	 * (starting from {@link #target}, coordinates {@link #targetRelativeX} / {@link #targetRelativeY}) touches
	 * the connection box (which displays the {@link #name} and attributes of this Connection.
	 */
	private AnchorPoint targetConnectionBoxRelativePoint;
	

	
	private String lineColor = "black";
	
	private String fontColor = "black";
	
	private int lineSize = 2;
	
	private String gradientStartColor ="#89E093";
	private String gradientEndColor="#6DF76D";
	
	private boolean selected = false;
	
	private boolean displayingAttributes = true;
	
	/**
	 * The absolute X coordinate, where the box, which displays the connection name and attributes, will start at the top-left corner
	 */
	private double connectionBoxX;
	/**
	 * The absolute Y coordinate, which displays the connection name and attributes,  where the box will start at the top-left corner
	 */
	private double connectionBoxY;
	
	/**
	 * The width of the connection box, which displays the connection name and attributes
	 */
	private double connectionBoxWidth;
	
	/**
	 * The height of the name box,  which displays the connection name and attributes
	 */
	private double connectionBoxHeight;
	
	/**
	 * The Z index (like known from CSS) for overlapping objects
	 */
	private double zIndex;
	
	
	/**
	 * The font size for the name
	 */
	private int nameFontSize = 18;
	
	/**
	 * This field will store the width of a single character in the given {@link #fontFamily}.
	 * This field is used to calculate the width of the meta class name.
	 * @see #fontFamily
	 */
	private double nameFontCharWidth = 12;
	
	
	private double attributeFontCharWidht = 9;
	
	private double attributeFontSize = 14;
	
	
	/**
	 * The font family name. For an easier calculation of the width of a text you should allways use a monospace font.
	 * <b>If you change the font, you also have to recalculate {@link #nameFontCharWidth} and {@link #attributeFontCharWidth}. <b>
	 * @see MetaClassImpl#nameFontCharWidth
	 */
	private String fontFamily ="Courier";
	
	private List<MetaAttribute> attributes;
	
	
	private List<Point> sourceToBoxPoints;
	private List<Point> boxToTargetPoints;
	
	
	public MetaConnectionImpl(String id, String name, MetaClass target, int lower, int upper)
	{
		this.target = target;
		this.id = id;
		this.name = name;
		this.targetLowerBound = lower;
		this.targetUpperBound = upper;
		this.attributes = new ArrayList<MetaAttribute>();
		this.sourceToBoxPoints = new ArrayList<Point>();
		this.boxToTargetPoints = new ArrayList<Point>();
		
		//TODO correct id
		sourceConnectionBoxRelativePoint = new AnchorPoint("anchorID1",0,10);
		sourceRelativePoint = new AnchorPoint("anchorID2", 30, 40);
		targetConnectionBoxRelativePoint = new AnchorPoint("anchorID3", 20,80);
		targetRelativePoint = new AnchorPoint("anchorID4", 0, 10);
		
		
	}
	

	@Override
	public void setTarget(MetaClass target) {
		this.target = target;
	}

	@Override
	public MetaClass getTarget() {
		return target;
	}


	


	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}




	@Override
	public String getLineColor() {
		return lineColor;
	}


	@Override
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}


	@Override
	public int getLineSize() {
		return lineSize;
	}


	@Override
	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
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
	public double getConnectionBoxX() {
		return connectionBoxX;
	}


	@Override
	public void setConnectionBoxX(double x) {
		this.connectionBoxX = x;
	}


	@Override
	public double getConnectionBoxY() {
		return connectionBoxY;
	}


	@Override
	public void setConnectionBoxY(double y) {
		this.connectionBoxY = y;
	}


	@Override
	public double getConnectionBoxWidth() {
		return connectionBoxWidth;
	}


	@Override
	public void setConnectionBoxWidth(double width) {
		this.connectionBoxWidth = width;
	}


	@Override
	public double getConnectionBoxHeight() {
		return connectionBoxHeight;
	}


	@Override
	public void setConnectionBoxHeight(double height) {
		this.connectionBoxHeight = height;
	}


	@Override
	public double getZIndex() {
		return zIndex;
	}


	@Override
	public void setzIndex(double zIndex) {
		this.zIndex = zIndex;
	}


	@Override
	public int getNameFontSize() {
		return nameFontSize;
	}


	@Override
	public void setNameFontSize(int fontSize) {
		this.nameFontSize = fontSize;
	}


	@Override
	public double getNameFontCharWidth() {
		return nameFontCharWidth;
	}


	@Override
	public void setNameFontCharWidth(double fontCharWidth) {
		this.nameFontCharWidth = fontCharWidth;
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
	public String getFontColor() {
		return fontColor;
	}


	@Override
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}


	@Override
	public boolean isSelected() {
		return selected;
	}
	
	@Override
	public boolean isDisplayingAttributes()
	{
		return displayingAttributes;
	}
	
	@Override
	public void setDisplayingAttributes(boolean show)
	{
		displayingAttributes = show;
	}


	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}



	@Override
	public int getTargetLowerBound() {
		return targetLowerBound;
	}



	@Override
	public int getTargetUpperBound() {
		return targetUpperBound;
	}



	@Override
	public void setTargetLowerBound(int lower) {
		targetLowerBound = lower;
	}



	@Override
	public void setTargetUpperBound(int upper) {
		targetUpperBound = upper;
	}



	@Override
	public String getID() {
		return id;
	}



	@Override
	public String getSourceIcon() {
		return sourceIconUrl;
	}



	@Override
	public double getSourceIconHeight() {
		return sourceIconHeight;
	}



	@Override
	public double getSourceIconWidth() {
		return sourceIconWidth;
	}



	@Override
	public String getTargetIcon() {
		return targetIconUrl;
	}



	@Override
	public double getTargetIconHeight() {
		return targetIconHeight;
	}



	@Override
	public double getTargetIconWidth() {
		return targetIconWidth;
	}



	@Override
	public void setSourceIcon(String url) {
		this.sourceIconUrl = url;
	}



	@Override
	public void setSourceIconHeight(double height) {
		this.sourceIconHeight = height;
	}



	@Override
	public void setSourceIconWidth(double width) {
		this.sourceIconWidth = width;
	}



	@Override
	public void setTagetIconHeight(double height) {
		this.targetIconHeight = height;
	}



	@Override
	public void setTargetIcon(String url) {
		this.targetIconUrl = url;
	}



	@Override
	public void setTargetIconWidth(double width) {
		this.targetIconWidth = width;
		
	}


	@Override
	public void setAttributeFontCharWidht(double attributeFontCharWidht) {
		this.attributeFontCharWidht = attributeFontCharWidht;
	}


	@Override
	public double getAttributeFontCharWidht() {
		return attributeFontCharWidht;
	}


	@Override
	public void setAttributeFontSize(double attributeFontSize) {
		this.attributeFontSize = attributeFontSize;
	}


	@Override
	public double getAttributeFontSize() {
		return attributeFontSize;
	}


	@Override
	public MetaClass getSource() {
		return source;
	}


	@Override
	public void setSource(MetaClass source) {
		this.source = source;
	}


	@Override
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type) throws MetaAttributeException {
		
		for (MetaAttribute a: attributes)
			if (a.getID().equals(id) || a.getName().equals(name))
				throw new MetaAttributeException(name, this);
		
		MetaAttribute a = new MetaAttributeImpl(id, name, type);
		attributes.add(a);
		return a;
	}


	@Override
	public List<MetaAttribute> getAttributes() {
		return attributes;
	}


	@Override
	public void removeAttribute(MetaAttribute attribute) {
		attributes.remove(attribute);
	}


	@Override
	public AnchorPoint getSourceConnectionBoxRelativePoint() {
		return sourceConnectionBoxRelativePoint;
	}


	@Override
	public AnchorPoint getSourceRelativePoint() {
		return sourceRelativePoint;
	}


	@Override
	public AnchorPoint getTargetConnectionBoxRelativePoint() {
		return targetConnectionBoxRelativePoint;
	}


	@Override
	public AnchorPoint getTargetRelativePoint() {
		return targetRelativePoint;
	}



}
