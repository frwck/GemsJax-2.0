package org.gemsjax.shared.metamodel;

import org.gemsjax.shared.AnchorPoint;

/**
 * This class represents an inheritance relation.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface MetaInheritance extends MetaModelElement{
	
	
	public MetaClass getSuperClass();
	
	public AnchorPoint getSuperClassRelativeAnchorPoint();
	
	public AnchorPoint getClassRelativeAnchorPoint();
	
	public void setSuperClassRelativeAnchorPoint(AnchorPoint a);
	
	public void setClassRelativeAnchorPoint(AnchorPoint a);
	
}
