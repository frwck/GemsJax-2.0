package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateMetaConnectionCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new CreateMetaConnectionCommand();
	}

}
