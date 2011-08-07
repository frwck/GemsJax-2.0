package org.gemsjax.shared.metamodel;


/**
 * The common interface for the meta model attribute implementation on client and server side.
 * We define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing a common class only one time, but can be used both with the concrete
 * server implementation and the concrete client implementation.
 * 
 * <br /><br />
 * A MetaConnection models a connection / relationship type between two {@link MetaClass}es. 
 * Its only possible to model a one - way connection, that means, that a bidirectional connection can not be
 * modeled directly by one {@link MetaConnection}, 
 * because the MetaConnection has only a target {@link MetaClass}. 
 * 
 * If you want to model a bidirectional MetaConnection between {@link MetaClass} A and {@link MetaClass} B, you must
 * create two {@link MetaConnection} objects. The first must be owned by {@link MetaClass} A  ({@link MetaClass#addConnection(MetaConnection)})
 * and the target must be set to {@link MetaClass} B and the second MetaConnection object must 
 *  be owned by {@link MetaClass} B  ({@link MetaClass#addConnection(MetaConnection)})
 * and the target must be set to {@link MetaClass} A.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface MetaConnection extends MetaModelElement{

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

	public int getTargetLowerBound();
	public void setTargetLowerBound(int lower);
	
	public int getTargetUpperBound();
	public void setTargetUpperBound(int upper);
	
	public void setTarget(MetaClass target);
	public MetaClass getTarget();
}
