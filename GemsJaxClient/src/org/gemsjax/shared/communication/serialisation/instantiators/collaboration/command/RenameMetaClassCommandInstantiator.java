package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaClassCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class RenameMetaClassCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new RenameMetaClassCommand();
	}

}
