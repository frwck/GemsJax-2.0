package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class GetAllExperimentsAnswerMessageInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new GetAllExperimentsAnswerMessage();
	}

}
