package org.gemsjax.shared.communication.serialisation.test;

import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;


public class OtherInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new Other();
	}

}
