package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaInheritanceCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CreateMetaInheritanceCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new CreateMetaInheritanceCommand();
	}

}
