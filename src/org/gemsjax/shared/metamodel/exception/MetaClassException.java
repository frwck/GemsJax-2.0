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
	
	public enum MetaClassExceptionReason
	{
		/**
		 * Indicates that a {@link MetaClass} with the same name already exists
		 */
		NAME_ALREADY_IN_USE
	}
	
	
	private MetaClassExceptionReason reason;
	private String name;
	
	
	
	public MetaClassException(MetaClassExceptionReason reason) {
		this.reason = reason;
	}
	
	/**
	 * 
	 * @param reason
	 * @param name
	 */
	public MetaClassException(MetaClassExceptionReason reason, String name)
	{
		this.name = name;
		this.reason = reason;
	}

	public MetaClassExceptionReason getReason() {
		return reason;
	}

	public String getName() {
		return name;
	}

}
