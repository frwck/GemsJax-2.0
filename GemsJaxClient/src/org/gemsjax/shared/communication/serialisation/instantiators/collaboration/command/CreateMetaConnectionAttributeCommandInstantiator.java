package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateMetaConnectionAttributeCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new CreateMetaConnectionAttributeCommand();
	}

}
