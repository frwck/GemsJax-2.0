package org.gemsjax.shared.metamodel.impl;


import java.util.List;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.UUID;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;


/**
 * This class is used to create meta model components like {@link MetaClass}, {@link MetaInheritance}, {@link MetaConnection}, etc. 
 * This Class is a static implementation that follows the Factory Method Pattern.
 * This class provides two type of methods:
 * <ul>
 * <li> create...(): Use this method to create a concrete object, which is created for the first time and will then be shared via websocket with the server and the other clients</li>
 * <li> createExisting...(): This method is used to create a concrete object after receiving the information via websocket.
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class MetaFactory {

	private  MetaFactory() {}
	
	
	private static synchronized  String nextID()
	{
		return UUID.generate().toString();
	}
	
	/**
	 * Creates a new {@link MetaInheritance} object.Use this method to create a brand new object.
	 * In addition the following steps will be done:
	 * <ul>
	 * <li>The unique ID for the {@link MetaInheritance} is generated and assigned.</li>
	 * <li>The {@link AnchorPoint}s are created and the coordinates are calculated.</li>
	 * </ul>
	 * This new created {@link MetaInheritance} must be added to the inheritance list of the owner class {@link MetaClass#addInheritance(MetaInheritance)}
	 * @param ownerClass
	 * @param superClass
	 * @return
	 * @throws MetaInheritanceExcepetion Is thrown if there already exists a inheritance relation between ownerClass and superClass
	 */
	public static MetaInheritance createInheritance(MetaClass ownerClass, MetaClass superClass)  {
		
		MetaInheritance i = new MetaInheritanceImpl(nextID(), ownerClass, superClass);
		
		i.setOwnerClassRelativeAnchorPoint(new AnchorPoint(nextID(),ownerClass.getWidth()/2,  0));
		
		i.setSuperClassRelativeAnchorPoint(new AnchorPoint(nextID(), superClass.getWidth()/2, 0));
		
		i.getOwnerClassRelativeAnchorPoint().setNextAnchorPoint(i.getSuperClassRelativeAnchorPoint());
		
		
		return i;
	}
	
	/**
	 * <b>Notice: This method should only be used, if you want to create a {@link MetaInheritance} which already was collaboratively created by another user on another computer</b>,
	 * because with this method you creates a new {@link MetaInheritance} object with the given ID which must be unique.
	 * If you want to create a new {@link MetaInheritance} object cause of local user interaction (example clicking on canvas) then use {@link #createInheritance(MetaClass)}.
	 * The following steps are done:
	 * <ul>
	 * <li>The {@link AnchorPoint}s object are created with the passed IDs and the passed coordinates are set.</li>
	 * </ul>
	 * 
	 * This new created {@link MetaInheritance} must be added to the inheritance list of the owner class {@link MetaClass#addInheritance(MetaInheritance)}
	 * 
	 * 
	 * @param id The unique ID
	 * @param ownerClass {@link MetaInheritance#getOwnerClass()}
	 * @param superClass The superClass
	 * @param superAnchorID The unique ID of the {@link AnchorPoint} on the super class
	 * @param superX The relative x coordinate for the super class {@link AnchorPoint}
	 * @param superY The relative y coordinate for the super class {@link AnchorPoint}
	 * @param classAnchorID
	 * @param classX The relative x coordinate for the class (which derives from the super class) {@link AnchorPoint}
	 * @param classY The relative y coordinate for the class (which derives from the super class) {@link AnchorPoint}
	 * @param otherAnchorPoints The list with the {@link AnchorPoint}s that are between the {@link MetaInheritance#getOwnerClassRelativeAnchorPoint()} and {@link MetaInheritance#getSuperClassRelativeAnchorPoint()} in the given list order <b>(the {@link AnchorPoint#getNextAnchorPoint()} must already be set correct)</b>.
	 *			That means that the list element with index 0 is the next element of the "class  {@link AnchorPoint}" and the list element with index 1 is the next  {@link AnchorPoint} of the element with index 0 ...
	 * @return The created {@link MetaInheritance} object
	 * @throws MetaInheritanceExcepetion  Is thrown if there already exists a inheritance relation between ownerClass and superClass
	 */
	public static MetaInheritance createExistingInheritance(String id,MetaClass ownerClass, MetaClass superClass, String superAnchorID, double superX, double superY, String classAnchorID,  double classX, double classY, List<AnchorPoint> otherAnchorPoints )
	{
		MetaInheritance inh = new MetaInheritanceImpl(id, ownerClass, superClass);
		
		AnchorPoint sa = new AnchorPoint(superAnchorID, superX, superY);
		AnchorPoint ca = new AnchorPoint(classAnchorID, classX, classY);
		
		if (otherAnchorPoints == null || otherAnchorPoints.size() == 0)
			ca.setNextAnchorPoint(sa);
		else
		{
			ca.setNextAnchorPoint(otherAnchorPoints.get(0));
			otherAnchorPoints.get(otherAnchorPoints.size()-1).setNextAnchorPoint(sa);
		}
		
		inh.setOwnerClassRelativeAnchorPoint(ca);
		inh.setSuperClassRelativeAnchorPoint(sa);
		
		return inh;
	}
	
	
	
	/**
	 * Creates a new {@link MetaModel}
	 * @param name
	 * @return
	 */
	public static MetaModel createMetaModel(int id, String name)
	{
		MetaModel m = new MetaModelImpl(id, name);
		
		return m;
	}
	
	/**
	 * Create a existing {@link MetaModel} object
	 * @param id The unique ID
	 * @param name The name
	 * @return
	 */
	public static MetaModel createExistingMetaModel(int id, String name)
	{
		return new MetaModelImpl(id, name);
	}
	
	
	
	/**
	 * Create a new {@link MetaClass} and automatically set a width and height to display the name completely by calling {@link MetaClass#autoSize()}.
	 * After this call the {@link MetaClass} must be added to a {@link MetaModel} by calling {@link MetaModel#addMetaClass(MetaClass)} 
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @return
	 */
	public static MetaClass createClass(String name, double x, double y) throws MetaClassException
	{
		
		MetaClass c = new MetaClassImpl(nextID(), name, x,y);
		c.autoSize();
		
		double minHeight = c.getNameTopSpace() + c.getNameFontSize() + c.getNameBottomSpace();
		
		if (c.getIconURL()!= null && !c.getIconURL().equals(""))
			minHeight+=c.getIconHeight();
		
		c.setMinHeight(minHeight);
		c.setMinWidth(c.getNameLeftSpace() + c.getNameRightSpace() + c.getNameFontCharWidth() * 5);
		
		return c;
	}
	

/**
 * Create a existing {@link MetaClass} with the passed parameters.
 *
 * @param id
 * @param name
 * @param x
 * @param y
 * @param zIndex
 * @param width
 * @param height
 * @param minWidth
 * @param minHeight
 * @param isAbstract
 * @param borderColor
 * @param borderSize
 * @param showAttributes
 * @param gradientEndColor
 * @param gradientStartColor
 * @param iconURL
 * @param iconWidth
 * @param iconHeight
 * @param nameFontColor
 * @return
 * @throws MetaClassException
 * @throws MetaAttributeException 
 */
	public static MetaClass createExistingClass(String id, String name, double x, double y, double zIndex,
			double width,
			double height,
			double minWidth,
			double minHeight,
			boolean isAbstract,
			String borderColor,
			double borderSize,
			boolean showAttributes,
			String gradientEndColor,
			String gradientStartColor,
			String iconURL,
			double iconWidth,
			double iconHeight,
			String nameFontColor,
			List<MetaAttribute> attributes
			
	) throws MetaAttributeException
	
	
	{
		MetaClass c = new MetaClassImpl(id, name, x,y);
	
		c.setWidth(width);
		c.setHeight(height);
		c.setAbstract(isAbstract);
		c.setBorderColor(borderColor);
		c.setDisplayAttributes(showAttributes);
		c.setGradientEndColor(gradientEndColor);
		c.setGradientStartColor(gradientStartColor);
		c.setIconURL(iconURL);
		c.setIconHeight(iconHeight);
		c.setIconWidth(iconWidth);
		c.setMinHeight(minHeight);
		c.setMinWidth(minWidth);
		c.setNameFontColor(nameFontColor);
		c.setZIndex(zIndex);
		
		for (MetaAttribute a: attributes)
			c.addAttribute(a);
		
		
		
		// TODO is FontSize, space etc needed to be set?
		
		return c;
	}
	
	
	/**
	 * Create a new {@link MetaBaseType}
	 * @param metaModel
	 * @param name
	 * @return
	 * @throws MetaBaseTypeException
	 
	public static synchronized MetaBaseType createBaseType(String name) 
	{
		MetaBaseType t = new MetaBaseTypeImpl(nextID(), name);
		
		return t;
	}
	*/
	
	
	/**
	 * Create a {@link MetaBaseType} with the given id and name
	 * @param id
	 * @param name
	 * @return
	 */
	public static synchronized MetaBaseType createExistingBaseType(String id, String name)
	{
		MetaBaseType t = new MetaBaseTypeImpl(id, name);
		
		return t;
	}
	
	
	/**
	 * Create a {@link MetaAttribute} by generating a new unique ID
	 * @param name
	 * @param type
	 * @return
	 */
	public static synchronized MetaAttribute createAttribute(String name, MetaBaseType type)
	{
		return new MetaAttributeImpl(nextID(), name, type);
	}
	
	
	/**
	 * Create an existing {@link MetaAttribute} with the given ID
	 * @param id
	 * @param name
	 * @param type
	 * @return
	 */
	public static synchronized MetaAttribute createExistingAttribute(String id, String name, MetaBaseType type)
	{
		return new MetaAttributeImpl(id, name, type);
	}
	
	
	/**
	 * Create a new {@link MetaConnection} and the according {@link AnchorPoint}s
	 * @param name
	 * @param source
	 * @param target
	 * @return
	 */
	public static synchronized MetaConnection createMetaConnection(String name, MetaClass source, MetaClass target)
	{
		MetaConnection con = new MetaConnectionImpl(nextID(), name, source, target);
		
		con.autoSize();
	
		con.setConnectionBoxMinHeight(con.getNameTopSpace() + con.getNameBottomSpace() + con.getNameFontSize());
		con.setConnectionBoxMinWidth(con.getNameLeftSpace() + con.getNameFontCharWidth()*5);
		
		double x, y;
		
		/// Calculate the direct way / coordinates for the connection box
		
		if (source.getX()<target.getX())
			x =  source.getX() + ( ( target.getX() - (source.getX() + source.getWidth() ) ) - con.getConnectionBoxWidth() ) /2 ;
		else
			x =  target.getX() + ( ( source.getX() - (target.getX() + target.getWidth() ) ) - con.getConnectionBoxWidth() ) /2 ;
		
		
		y = source.getY() + ( Math.abs(source.getY() - target.getY()) - con.getConnectionBoxHeight() /2);
		
		con.setConnectionBoxX(x);
		con.setConnectionBoxY(y);
		
		AnchorPoint sourcePoint = new AnchorPoint(nextID(), source.getWidth(), (source.getHeight()/2));
		AnchorPoint targetPoint = new AnchorPoint(nextID(), 0, (target.getHeight()/2));
		
		AnchorPoint boxSourcePoint = new AnchorPoint(nextID(), 0, (con.getConnectionBoxHeight()/2));
		AnchorPoint boxTargetPoint = new AnchorPoint(nextID(), con.getConnectionBoxWidth(), (con.getConnectionBoxHeight()/2));
		
		
		sourcePoint.setNextAnchorPoint(boxSourcePoint);
		boxTargetPoint.setNextAnchorPoint(targetPoint);
		
		
		
		con.setSourceRelativePoint(sourcePoint);
		con.setSourceConnectionBoxRelativePoint(boxSourcePoint);
		con.setTargetRelativePoint(targetPoint);
		con.setTargetConnectionBoxRelativePoint(boxTargetPoint);
		
		
		
		return con;
	}
	
	
	
	
	
}
