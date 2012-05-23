package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.experiment.ExperimentSuccessfulMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentSuccessfulMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ExperimentSuccessfulMessage();
	}

}
