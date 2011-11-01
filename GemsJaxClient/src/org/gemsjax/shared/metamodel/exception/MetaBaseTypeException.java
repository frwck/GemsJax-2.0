package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaModel;

/**
 * This Exception is thrown, when you try to add a {@link MetaBaseType} to a {@link MetaModel}, which already has this {@link MetaBaseType}
 * in its meta base types list.
 * @author Hannes Dorfmann
 *
 */
public class MetaBaseTypeException extends Exception{
	
	private MetaModel metaModel;
	private String baseTypeName;
		
	public MetaBaseTypeException(MetaModel metaModel, String baseTypeName)
	{
		this.metaModel = metaModel;
		this.baseTypeName = baseTypeName;
	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public String getBaseTypeName() {
		return baseTypeName;
	}

}
