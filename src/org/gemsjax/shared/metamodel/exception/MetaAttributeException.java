package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.client.metamodel.MetaAttributeImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaClass;


/**
 * This Exception is thrown, when a new Attribute should be added to a {@link MetaClass} and there exists already
 * {@link MetaAttribute} with the same name. The {@link MetaAttribute} name must be unique within a MetaClass.
 * @author Hannes Dorfmann
 *
 */
public class MetaAttributeException extends Exception {
	
	public enum MetaAttributeExceptionReason
	{
		NAME_ALREADY_IN_USE
	}
	
	
	private String name;
	private MetaClassImpl metaClass;
	private MetaAttributeExceptionReason reason;
	
	public MetaAttributeException(MetaAttributeExceptionReason reason, String duplicateName, MetaClassImpl metaClass)
	{
		this.name = duplicateName;
		this.metaClass = metaClass;
		this.reason = reason;
	}
	
	public MetaAttributeException(MetaAttributeExceptionReason type, String duplicateName, MetaClassImpl metaClass, String msg)
	{
		super(msg);
		this.name = duplicateName;
		this.metaClass = metaClass;
		this.reason = type;
	}
	
	/**
	 * Get the name which is already used as {@link MetaAttributeImpl} name in this {@link MetaClassImpl}
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get the MetaClass which has raised this exception, because there exists already an attribute with the same name
	 * @return
	 */
	public MetaClassImpl getMetaClass()
	{
		return metaClass;
	}
	
	
	public MetaAttributeExceptionReason getReason()
	{
		return reason;
	}

}
