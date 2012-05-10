package org.gemsjax.shared.communication.serialisation.instantiators.collaboration;

import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class TransactionInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new TransactionImpl();
	}

}
