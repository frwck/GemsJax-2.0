package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateMetaAttributeCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CreateMetaAttributeCommand();
	}

}
