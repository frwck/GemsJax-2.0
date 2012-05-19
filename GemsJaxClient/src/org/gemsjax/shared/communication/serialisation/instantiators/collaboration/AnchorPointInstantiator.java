package org.gemsjax.shared.communication.serialisation.instantiators.collaboration;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;

public class AnchorPointInstantiator implements ObjectInstantiator{

	@Override
	public Object newInstance() {
		return new AnchorPoint();
	}

}
