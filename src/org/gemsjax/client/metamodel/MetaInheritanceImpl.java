package org.gemsjax.client.metamodel;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaInheritance;

public class MetaInheritanceImpl implements MetaInheritance{

	/**
	 * The unique id
	 */
	private String id;
	
	/**
	 * The super class
	 */
	private MetaClass superClass;
	
	private AnchorPoint classRelativeAnchorPoint;
	private AnchorPoint superClassRelativeAnchorPoint;
	
	
	public MetaInheritanceImpl(String id, MetaClass superClass)
	{
		this.id = id;
		this.superClass = superClass;
	}
	
	
	
	
	@Override
	public AnchorPoint getClassRelativeAnchorPoint() {
		return classRelativeAnchorPoint;
	}

	@Override
	public MetaClass getSuperClass() {
		return superClass;
	}

	@Override
	public AnchorPoint getSuperClassRelativeAnchorPoint() {
		return superClassRelativeAnchorPoint;
	}

	@Override
	public String getID() {
		return id;
	}




	@Override
	public void setClassRelativeAnchorPoint(AnchorPoint a) {
		this.classRelativeAnchorPoint = a;
	}




	@Override
	public void setSuperClassRelativeAnchorPoint(AnchorPoint a) {
		this.superClassRelativeAnchorPoint = a;
	}

}
