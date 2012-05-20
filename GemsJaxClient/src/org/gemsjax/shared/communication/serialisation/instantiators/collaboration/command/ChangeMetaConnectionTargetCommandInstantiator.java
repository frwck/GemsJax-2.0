package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionTargetCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaConnectionTargetCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ChangeMetaConnectionTargetCommand();
	}

}
