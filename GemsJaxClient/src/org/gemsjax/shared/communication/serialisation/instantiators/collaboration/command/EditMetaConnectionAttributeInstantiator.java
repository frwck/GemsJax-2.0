package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.EditMetaConnectionAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class EditMetaConnectionAttributeInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new EditMetaConnectionAttributeCommand();
	}

}
