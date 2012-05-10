package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class SubscribeCollaborateableErrorMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new SubscribeCollaborateableErrorMessage();
	}

}
