package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.client.metamodel.MetaModelImpl;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

/**
 * This exception is thrown  when you try to create a MetaClass, which is not allowed,
 * because for example there exists already a {@link MetaClass} with this name. 
 * The {@link MetaClass} must be unique within a {@link MetaModel}
 * @author hannes
 *
 */
public class MetaClassException extends Exception{
	
		
	private String name;
	
	private MetaModel metaModel;
	
	/**
	 * @param name
	 */
	public MetaClassException(String name, MetaModel metaModel)
	{
		this.name = name;
		this.metaModel = metaModel;
	}


	public String getName() {
		return name;
	}
	
	public MetaModel getMetaModel()
	{
		return metaModel;
	}

}