package org.gemsjax.client.admin.model.metamodel;

import java.util.List;

import org.gemsjax.client.canvas.MetaClassDrawable;

/**
 * A MetaClass is a representation of a Meta-Model class
 * @author Hannes Dorfmann
 *
 */
public class MetaClass extends MetaClassDrawable {
	
	public MetaClass(double x, double y) {
		super(x, y);
		
	}

	/**
	 * The name of this MetaClass
	 */
	private String name;
	
	/**
	 * TODO What is Root?
	 */
	private boolean isRoot;
	/**
	 * Is this MetaClass abstract?
	 */
	private boolean isAbstract;
	
	private List <Attribute>attributeList;
	
	private List <Connection> connectionList;
	
	private List<InheritanceRelation> inheritanceRelationList;

	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	public List<Connection> getConnectionList() {
		return connectionList;
	}

	public List<InheritanceRelation> getInheritanceRelationList() {
		return inheritanceRelationList;
	}
	
	
	
	

}
