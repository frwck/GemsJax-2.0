package org.gemsjax.shared.communication.serialisation.instantiators.experiment;

import org.gemsjax.shared.communication.message.experiment.ExperimentInvitationDTO;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentInvitationDTOInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new ExperimentInvitationDTO();
	}

}
