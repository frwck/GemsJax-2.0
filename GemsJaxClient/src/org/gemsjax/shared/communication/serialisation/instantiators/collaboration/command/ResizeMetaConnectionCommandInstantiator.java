package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaConnectionCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ResizeMetaConnectionCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ResizeMetaConnectionCommand();
	}
	
	

}
