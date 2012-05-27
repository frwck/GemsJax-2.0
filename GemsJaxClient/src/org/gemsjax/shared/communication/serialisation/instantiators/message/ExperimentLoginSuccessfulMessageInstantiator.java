package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.system.ExperimentLoginSuccessfulMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ExperimentLoginSuccessfulMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ExperimentLoginSuccessfulMessage();
	}
	
	

}
