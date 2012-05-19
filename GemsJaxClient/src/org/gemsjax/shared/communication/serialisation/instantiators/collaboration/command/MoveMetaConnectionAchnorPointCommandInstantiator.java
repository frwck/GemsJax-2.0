package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.MoveMetaConnectionAchnorPointCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class MoveMetaConnectionAchnorPointCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new MoveMetaConnectionAchnorPointCommand();
	}

}
