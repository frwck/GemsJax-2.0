package org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command;

import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionIconsCommand;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class ChangeMetaConnectionIconsCommandInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new ChangeMetaConnectionIconsCommand();
	}

}
