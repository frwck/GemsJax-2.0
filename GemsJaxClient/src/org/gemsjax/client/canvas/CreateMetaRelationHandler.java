package org.gemsjax.client.canvas;

import org.gemsjax.shared.metamodel.MetaClass;

public interface CreateMetaRelationHandler {

	public void onCreateMetaRelation(String name, MetaClass source, MetaClass target);
	
}
