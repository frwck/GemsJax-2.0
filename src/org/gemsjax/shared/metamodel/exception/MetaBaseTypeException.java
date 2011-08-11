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
		
	public MetaBaseTypeException(MetaModel metaModel)
	{
		this.metaModel = metaModel;
	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

}
