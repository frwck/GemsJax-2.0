package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionMultiplicityCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaConnectionMultiplicityCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ChangeMetaConnectionMultiplicityCommand();
	}
	

}
