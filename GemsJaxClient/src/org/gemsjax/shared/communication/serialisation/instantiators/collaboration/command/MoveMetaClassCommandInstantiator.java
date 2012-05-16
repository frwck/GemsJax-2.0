package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaClassCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class MoveMetaClassCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new MoveMetaClassCommand();
	}

	
	
}
