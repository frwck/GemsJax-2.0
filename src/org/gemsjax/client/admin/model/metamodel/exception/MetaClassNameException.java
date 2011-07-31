package org.gemsjax.client.admin.model.metamodel.exception;

import org.gemsjax.client.model.metamodel.MetaClass;
import org.gemsjax.client.model.metamodel.MetaModel;

/**
 * This exception will be thrown to indivate, that there exists already a {@link MetaClass} with this name. 
 * The {@link MetaClass} must be unique within a {@link MetaModel}
 * @author hannes
 *
 */
public class MetaClassNameException extends Exception{
	
	public MetaClassNameException(String msg) {
		super(msg);
	}

}
