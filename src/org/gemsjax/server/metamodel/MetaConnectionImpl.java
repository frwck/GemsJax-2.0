package org.gemsjax.server.metamodel;

import org.gemsjax.shared.Constants;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModelElement;

/**
 * This class represents a connection between two MetaClasses.
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionImpl implements MetaConnection {

	private String id;
	private String name;
	
	private int targetLowerBound;
	private int targetUpperBound;
	
	/**
	 * This constant indicates, that the multiplicity of a connection is  arbitrarily, or in UML language a "*" 
	 */
	public static final int MULTIPLICITY = Constants.MULTIPLICITY_MANY;
	
	/**
	 * The target, where the MetaClass ends
	 */
	private MetaClass target;

	
	public MetaConnectionImpl(String id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public MetaClass getTarget() {
		return target;
	}

	@Override
	public int getTargetLowerBound() {
		return targetLowerBound;
	}

	@Override
	public int getTargetUpperBound() {
		return targetUpperBound;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTarget(MetaClass target) {
		this.target = target;
	}

	@Override
	public void setTargetLowerBound(int lower) {
		this.targetLowerBound = lower;
	}

	@Override
	public void setTargetUpperBound(int upper) {
		this.targetUpperBound = upper;
	}

	@Override
	public String getID() {
		return id;
	}
	
	


}
