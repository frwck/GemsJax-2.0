package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassIconCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaClassIconCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new ChangeMetaClassIconCommand();
	}

}
