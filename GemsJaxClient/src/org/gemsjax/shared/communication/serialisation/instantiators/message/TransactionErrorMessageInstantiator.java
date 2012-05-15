package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.TransactionErrorMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class TransactionErrorMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new TransactionErrorMessage();
	}

}
