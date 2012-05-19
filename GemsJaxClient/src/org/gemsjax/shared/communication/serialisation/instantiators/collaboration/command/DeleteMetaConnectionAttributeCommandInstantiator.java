package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaConnectionAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class DeleteMetaConnectionAttributeCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new DeleteMetaConnectionAttributeCommand();
	}

}
