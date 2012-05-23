package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.experiment.ExperimentErrorMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentErrorMessageInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new ExperimentErrorMessage();
	}

}
