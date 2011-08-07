package org.gemsjax.client.metamodel;

/**
 * {@link InheritanceRelation} represents an inheritance relation for {@link MetaClass}es. 
 * That means, that a {@link MetaClass} can inherit {@link Attribute}s and properties from the superClass
 * @author Hannes Dorfmann
 *
 */
public class InheritanceRelation {
	
	private MetaClass superClass;
	private MetaClass subClass;
	
	
	public InheritanceRelation(MetaClass superClass, MetaClass subClass) {
		super();
		this.superClass = superClass;
		this.subClass = subClass;
	}


	public MetaClass getSuperClass() {
		return superClass;
	}


	public void setSuperClass(MetaClass superClass) {
		this.superClass = superClass;
	}


	public MetaClass getSubClass() {
		return subClass;
	}


	public void setSubClass(MetaClass subClass) {
		this.subClass = subClass;
	}
	

}
