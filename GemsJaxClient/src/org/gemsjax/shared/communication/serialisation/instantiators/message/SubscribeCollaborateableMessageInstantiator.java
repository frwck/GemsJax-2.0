package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class SubscribeCollaborateableMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new SubscribeCollaborateableMessage();
	}

}
