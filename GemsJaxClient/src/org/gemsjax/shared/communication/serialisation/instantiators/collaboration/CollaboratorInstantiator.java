package org.gemsjax.shared.communication.serialisation.instantiators.collaboration;

import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class CollaboratorInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new Collaborator();
	}

}
