package org.gemsjax.shared.model;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;

/**
 * A {@link Model} has a base {@link MetaModel} to get the semantic like relation etc.
 * @author Hannes Dorfmann
 *
 */
public interface Model extends Collaborateable {
	

	/**
	 * Get the base {@link MetaModel}
	 * @return
	 */
	public MetaModel getMetaModel();
	
	
	/**
	 * Set the base {@link MetaModel}
	 * @param metaModel
	 */
	public void setMetaModel(MetaModel metaModel);

}
