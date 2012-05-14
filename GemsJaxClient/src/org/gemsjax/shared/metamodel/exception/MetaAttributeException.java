package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.collaboration.SemanticException;

/**
 * This Exception is thrown, when a new Attribute should be added to a {@link MetaClass} and there exists already
 * {@link MetaAttribute} with the same name. The {@link MetaAttribute} name must be unique within a MetaClass.
 * @author Hannes Dorfmann
 *
 */
public class MetaAttributeException extends SemanticException {
		
	private String name;
	private Object metaElement;
	
	public MetaAttributeException( String duplicateName, Object metaElement)
	{
		this.name = duplicateName;
		this.metaElement = metaElement;
	
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
	public Object getMetaModelElement()
	{
		return metaElement;
	}
	
	
}
