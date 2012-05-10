package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CollaboratorLeftMessage implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CollaboratorLeftMessage();
	}

}
