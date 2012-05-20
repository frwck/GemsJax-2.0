package org.gemsjax.client.canvas;

import org.gemsjax.shared.metamodel.MetaClass;

public interface CreateMetaInheritanceHandler {
	
	public void onCreateInheritance(MetaClass clazz, MetaClass superClass);

}
