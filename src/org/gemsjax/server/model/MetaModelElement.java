package org.gemsjax.server.model;

/**
 * This is the super basic type for every Element that is somehow part of a MetaModel.
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
	
	/**
	 * Get the name of this element
	 * @return
	 */
	public String getName();
	
	/**
	 * Set the name of this element.
	 * <b>Notice:</b> The name must also be unique in a MetaModel
	 * @param name
	 * @return
	 */
	public void setName(String name);

}
