package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class GetAllExperimentsMessageInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new GetAllExperimentsMessage();
	}

}
