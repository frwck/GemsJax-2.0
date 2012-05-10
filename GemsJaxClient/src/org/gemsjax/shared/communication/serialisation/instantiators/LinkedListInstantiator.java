package org.gemsjax.shared.communication.serialisation.instantiators;

import java.util.LinkedList;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class LinkedListInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new LinkedList();
	}

}
