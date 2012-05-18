package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassAbstractCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaClassAbstractCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new ChangeMetaClassAbstractCommand();
	}
	
	

}
