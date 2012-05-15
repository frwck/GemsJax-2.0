package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaClassCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateMetaClassCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new CreateMetaClassCommand();
	}

}
