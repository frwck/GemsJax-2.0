package org.gemsjax.client.metamodel;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaInheritance;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaInheritanceImpl implements MetaInheritance{

	/**
	 * The unique id
	 */
	private String id;
	
	/**
	 * The super class
	 */
	private MetaClass superClass;
	
	private MetaClass ownerClass;
	
	private AnchorPoint ownerClassRelativeAnchorPoint;
	private AnchorPoint superClassRelativeAnchorPoint;
	
	private double lineWidth = 1;
	private String lineColor = "black";
	
	private double zIndex;
	
	private boolean selected = false;
	
	
	public MetaInheritanceImpl(String id, MetaClass ownerClass, MetaClass superClass)
	{
		this.id = id;
		this.superClass = superClass;
		this.ownerClass = ownerClass;
	}
	
	
	
	
	@Override
	public AnchorPoint getOwnerClassRelativeAnchorPoint() {
		return ownerClassRelativeAnchorPoint;
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
	public void setOwnerClassRelativeAnchorPoint(AnchorPoint a) {
		this.ownerClassRelativeAnchorPoint = a;
	}




	@Override
	public void setSuperClassRelativeAnchorPoint(AnchorPoint a) {
		this.superClassRelativeAnchorPoint = a;
	}




	@Override
	public MetaClass getOwnerClass() {
		return ownerClass;
	}




	@Override
	public void setOwnerClass(MetaClass ownerClass) {
		this.ownerClass = ownerClass;
	}




	@Override
	public void setSuperClass(MetaClass superClass) {
	
		this.superClass = superClass;
	}




	@Override
	public double getLineSize() {
		return lineWidth;
	}




	@Override
	public void setLineSize(double lineWidth) {
		this.lineWidth = lineWidth;
	}




	@Override
	public String getLineColor() {
		return lineColor;
	}




	@Override
	public void setLineColor(String color) {
		this.lineColor = color;
	}




	@Override
	public double getZIndex() {
		return zIndex;
	}




	@Override
	public void setZIndex(double zIndex) {
		this.zIndex = zIndex;
	}




	@Override
	public boolean isSelected() {
		return selected;
	}




	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
