package org.gemsjax.shared.communication.serialisation.instantiators.experiment;

import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentDTOInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ExperimentDTO();
	}

}
