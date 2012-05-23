package org.gemsjax.shared.communication.serialisation.instantiators.experiment;

import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentGroupDTOInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ExperimentGroupDTO();
	}

}
