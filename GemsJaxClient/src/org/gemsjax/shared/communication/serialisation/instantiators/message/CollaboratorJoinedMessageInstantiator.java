package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CollaboratorJoinedMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CollaboratorJoinedMessage();
	}

}
