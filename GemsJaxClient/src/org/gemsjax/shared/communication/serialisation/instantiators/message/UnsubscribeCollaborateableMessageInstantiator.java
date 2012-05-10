package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.UnsubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class UnsubscribeCollaborateableMessageInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new UnsubscribeCollaborateableMessage();
	}

}
