package org.gemsjax.shared.metamodel;

import java.util.List;

/** The common interface for the meta model class implementation on client and server side.
* We define a common api that can be used for implementing concrete objects on server and client.
* We also can implement common classes (for example to check the validity of a model) that can be used on
* server and client side, by implementing a common class only one time, but can be used both with the concrete
* server implementation and the concrete client implementation of this MetaClass
* @author Hannes Dorfmanns
*/
public interface MetaClass extends MetaModelElement{

	
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

	
	public void addAttribute(MetaAttribute attribute);
	public void addConnection(MetaConnection connection);
	public void addContainmentRelation(MetaContainmentRelation relation);
	public void addInheritance(MetaBaseType type);
	
	public List<MetaAttribute> getAttributes();
	public List<MetaConnection> getConnections();
	public List<MetaContainmentRelation> getContainmentRelations();
	
	/**
	 * Get a List with all super classes / base types. That means, that this list contains all classes/ base types
	 * from which this MetaClass derives.
	 * @return
	 */
	public List<MetaBaseType> getInheritances();
	
	
	public void removeAttribute(MetaAttribute attribute);
	public void removeConnection(MetaConnection connection);
	public void removeContainmentRelation(MetaContainmentRelation relation);
	public void removeInheritance(MetaBaseType type); 
}
