package org.gemsjax.shared.metamodel;

/**
 * This is the super basic type for every element that is somehow part of a MetaModel.
 * Especially the {@link #getID()} method is very important, because every element of the MetaModel has a unique ID (within a MetaModel).
 * It also offers the possibility to do a quick element search by saving the elements in a HashMap (with the ID as the key).
 * Server and Client identifies the element according this ID.
 * @author Hannes Dorfmann
 *
 */
public interface MetaModelElement {
	
	/**
	 * Every element of the MetaModel has a unique ID (within a MetaModel).
	 * It also offers the possibility to do a quick element search by saving the elements in a HashMap (with the ID as the key).
	 * Server and Client identifies the element according this ID (and the ID of the MetaModel).
	 * @return
	 */
	public String getID();
	
}
