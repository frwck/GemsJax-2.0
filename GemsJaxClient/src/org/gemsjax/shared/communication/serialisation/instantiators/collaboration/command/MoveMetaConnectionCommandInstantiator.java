package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaConnectionCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class MoveMetaConnectionCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new MoveMetaConnectionCommand();
	}

}
