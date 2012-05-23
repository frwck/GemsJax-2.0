package org.gemsjax.shared.communication.serialisation.instantiators.user;

import org.gemsjax.shared.communication.message.experiment.UserDTO;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class UserDTOInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new UserDTO();
	}

}
