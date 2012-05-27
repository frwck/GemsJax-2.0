package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.system.NewExperimentRegistrationMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class NewExperimentRegistrationMessageInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new NewExperimentRegistrationMessage();
	}

}
