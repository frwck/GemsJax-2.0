package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateExperimentMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CreateExperimentMessage();
	}

}
