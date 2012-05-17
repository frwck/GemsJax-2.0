package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.EditMetaAttributeCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class EditMetaAttributeCommandInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new EditMetaAttributeCommand();
	}
	
	

}
