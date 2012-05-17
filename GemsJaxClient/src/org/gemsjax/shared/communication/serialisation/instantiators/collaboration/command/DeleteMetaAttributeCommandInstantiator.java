package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class DeleteMetaAttributeCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new DeleteMetaAttributeCommand();
	}

}
