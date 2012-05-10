package org.gemsjax.shared.communication.serialisation.instantiators;

import java.util.LinkedHashMap;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class LinkedHashMapInstantiator implements ObjectInstantiator {

	@Override
	public Object newInstance() {
		return new LinkedHashMap();
	}

}
