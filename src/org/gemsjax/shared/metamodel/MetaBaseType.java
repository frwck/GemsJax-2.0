package org.gemsjax.shared.metamodel;

/**
 * The common interface for the meta model base type implementation on client and server side.
 * BaseTypes are String, int, float, double, char etc. 
 * We define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing a common class only one time, but can be used both with the concrete
 * server implementation and the concrete client implementation.
 * @author Hannes Dorfmann
 *
 */
public interface MetaBaseType extends MetaModelElement{
	
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
