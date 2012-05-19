package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaConnectionCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class RenameMetaConnectionCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new RenameMetaConnectionCommand();
	}

}
