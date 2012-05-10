package org.gemsjax.shared.communication.serialisation.instantiators;

import java.util.LinkedHashSet;

import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class LinkedHashSetInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new LinkedHashSet();
	}

}
