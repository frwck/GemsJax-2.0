package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaClassCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ResizeMetaClassCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ResizeMetaClassCommand();
	}

	
	
	
}
