package org.gemsjax.shared.metamodel;

import java.util.Set;

import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;

/**
 * The common interface for the meta model attribute implementation on client and server side.
 * We define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing a common class only one time, but can be used both with the concrete
 * server implementation and the concrete client implementation.
 * @author Hannes Dorfmann
 *
 */
public interface MetaAttribute extends MetaModelElement{
	
	/**
	 * Get the {@link MetaBaseType}
	 * @return
	 */
	public MetaBaseType getType();
	
	public void setType(MetaBaseType type);
	
	
	/**
	 * Get the name of this element
	 * @return
	 */
	public String getName();
	
	/**
	 * Set the name of this element.
	 * <b>Notice:</b> The name must also be unique in a MetaClass
	 * @param name
	 * @return
	 */
	public void setName(String name);
	
	
	public void addCollaborateableElementPropertiesListeners(Set<CollaborateableElementPropertiesListener> listeners);
	public void removeCollaborateableElementPropertiesListeners(Set<CollaborateableElementPropertiesListener> listeners);

	public void addCollaborateableElementPropertiesListener(CollaborateableElementPropertiesListener listeners);
	public void removeCollaborateableElementPropertiesListener(CollaborateableElementPropertiesListener listeners);
	

}
