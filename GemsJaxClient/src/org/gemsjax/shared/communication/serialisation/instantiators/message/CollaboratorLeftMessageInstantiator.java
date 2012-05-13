package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.CollaboratorLeftMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CollaboratorLeftMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CollaboratorLeftMessage();
	}

}
