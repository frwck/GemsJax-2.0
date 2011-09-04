package org.gemsjax.shared.metamodel;

import org.gemsjax.shared.AnchorPoint;

/**
 * This class represents an inheritance relation.
 * The "owner class" ( {@link #getOwnerClass()} , {@link #setSuperClass(MetaClass)}) derives the attributes from the super class ({@link #getSuperClass()} , {@link #setSuperClass(MetaClass)})
 * The owner class has the instance of the MetaInheritance in the {@link MetaClass#getInheritances()} list.
 *<br /><br /> 
 * In UML it would look like this: <br/>
 * <br/>
 *  OwnerClass --------->  SuperClass
 * 
 * 
 * @author Hannes Dorfmann
 *
 */
public interface MetaInheritance extends MetaModelElement{
	
	/**
	 * Get the super class. The "owner class" derives from this class
	 * @return
	 * @see #getOwnerClass()
	 */
	public MetaClass getSuperClass();
	
	/**
	 * Get the "owner class". The owner class is the owner of this {@link MetaInheritance} 
	 * @return
	 */
	public MetaClass getOwnerClass();
	
	public void setOwnerClass(MetaClass ownerClass);
	
	public void setSuperClass(MetaClass superClass);
	
	public AnchorPoint getSuperClassRelativeAnchorPoint();
	
	public AnchorPoint getClassRelativeAnchorPoint();
	
	public void setSuperClassRelativeAnchorPoint(AnchorPoint a);
	
	public void setClassRelativeAnchorPoint(AnchorPoint a);
	
}
