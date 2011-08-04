package org.gemsjax.server.model;

import org.gemsjax.shared.Constants;

/**
 * This class represents a connection between two MetaClasses.
 * @author Hannes Dorfmann
 *
 */
public class MetaConnection implements MetaModelElement {

	private String id;
	private String name;
	
	private int targetLowerBound;
	private int targerUpperBound;
	
	/**
	 * This constant indicates, that the multiplicity of a connection is  arbitrarily, or in UML language a "*" 
	 */
	public static final int MULTIPLICITY = Constants.MULTIPLICITY_MANY;
	
	/**
	 * The source where the MetaClass starts
	 */
	private MetaClass source;
	/**
	 * The target, where the MetaClass ends
	 */
	private MetaClass target;
	
	
	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public int getTargetLowerBound() {
		return targetLowerBound;
	}

	public void setTargetLowerBound(int targetLowerBound) {
		this.targetLowerBound = targetLowerBound;
	}

	public int getTargerUpperBound() {
		return targerUpperBound;
	}

	public void setTargerUpperBound(int targerUpperBound) {
		this.targerUpperBound = targerUpperBound;
	}

	public MetaClass getSource() {
		return source;
	}

	public void setSource(MetaClass source) {
		this.source = source;
	}

	public MetaClass getTarget() {
		return target;
	}

	public void setTarget(MetaClass target) {
		this.target = target;
	}

}
