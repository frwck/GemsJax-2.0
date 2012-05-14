package org.gemsjax.shared.communication.serialisation.instantiators.collaboration;

import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;
import org.gemsjax.shared.metamodel.impl.MetaBaseTypeImpl;

public class MetaBaseTypeInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new MetaBaseTypeImpl();
	}

}
