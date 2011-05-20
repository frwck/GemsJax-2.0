package org.gemsjax.client.admin.model.metamodel;

/**
 * A {@link MetaClass} can have Attributes
 * @author Hannes Dorfmann
 *
 */
public class Attribute {
	
	/**
	 * The name of this Attribute
	 */
	private String name;
	
	/**
	 * The name of the Type of this Attribute
	 */
	private String typeName;
	
	
	public Attribute(String name, String type) {
		super();
		this.name = name;
		this.typeName = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return typeName;
	}


	public void setType(String type) {
		this.typeName = type;
	}
	
	
	

}
