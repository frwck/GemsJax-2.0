package org.gemsjax.shared.communication.serialisation.instantiators.message;

import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class TransactionMessageInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new TransactionMessage();
	}
	
	

}
