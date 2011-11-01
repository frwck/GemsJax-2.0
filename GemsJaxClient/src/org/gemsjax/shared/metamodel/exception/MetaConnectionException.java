package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;

/**
 * Is thrown, when a {@link MetaConnection} should be added to a {@link MetaClass} but cant be added for different reasons,
 * for example the {@link MetaClass} has already a {@link MetaConnection} with the same name.
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionException extends Exception {
	
	
	private String name;
	private MetaClass metaClass;
	
	public MetaConnectionException( MetaClass metaClass, String name)
	{
		this.name = name;
		this.metaClass = metaClass;
	}


	/**
	 * Get the name, which has caused this Exception. Is only set if {@link #getType()} == {@link MetaConnectionExceptionType#NAME_ALREADY_IN_USE}
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the {@link MetaClass} which has thrown this Exception
	 * @return
	 */
	public MetaClass getMetaClass()
	{
		return metaClass;
	}
}
