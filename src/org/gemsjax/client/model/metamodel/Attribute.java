package org.gemsjax.client.model.metamodel;

import org.gemsjax.client.admin.model.metamodel.exception.AttributeNameException;

/**
 * A {@link MetaClass} can have Attributes.
 * The name of an {@link Attribute} is unique in a MetaClass (even if the type is different).
 * This condition will be checked when you add a {@link Attribute} to a {@link MetaClass} by calling {@link MetaClass#addAttribute(String, String)},
 * which will throw an {@link AttributeNameException} if the name is not available.
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
