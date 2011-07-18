package org.gemsjax.client.admin.model.metamodel.exception;

import org.gemsjax.client.admin.model.metamodel.Attribute;
import org.gemsjax.client.admin.model.metamodel.MetaClass;


/**
 * This Exception will be thrown, when a new Attribute should be added to a {@link MetaClass} and there exists already
 * {@link Attribute} with the same name. So {@link Attribute} names must be unique within a MetaClass.
 * @author Hannes Dorfmann
 *
 */
public class AttributeNameException extends Exception {
	
	private String name;
	private MetaClass metaClass;
	
	public AttributeNameException(String duplicateName, MetaClass metaClass, String msg)
	{
		super(msg);
		this.name = duplicateName;
		this.metaClass = metaClass;
	}
	
	/**
	 * Get the name which is already used as {@link Attribute} name in this {@link MetaClass}
	 * @return
	 */
	public String getDuplicateName()
	{
		return name;
	}
	
	/**
	 * Get the MetaClass which has raised this exception, because there exists already an attribute with the same name
	 * @return
	 */
	public MetaClass getMetaClass()
	{
		return metaClass;
	}

}
