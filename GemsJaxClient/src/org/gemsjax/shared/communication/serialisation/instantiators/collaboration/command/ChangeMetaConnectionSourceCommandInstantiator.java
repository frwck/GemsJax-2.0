package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionSourceCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaConnectionSourceCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new ChangeMetaConnectionSourceCommand();
	}
	
	

}
