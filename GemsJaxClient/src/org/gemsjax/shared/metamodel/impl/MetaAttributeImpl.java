package org.gemsjax.shared.metamodel.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;

/**
 * A {@link MetaClassImpl} can have Attributes.
 * The name of an {@link MetaAttributeImpl} is unique in a MetaClass (even if the type is different).
 * This condition will be checked when you add a {@link MetaAttributeImpl} to a {@link MetaClassImpl} by calling {@link MetaClassImpl#addAttribute(String, String)},
 * which will throw an {@link MetaAttributeException} if the name is not available.
 * @author Hannes Dorfmann
 *
 */
public class MetaAttributeImpl implements MetaAttribute{
	
	
	/**
	 * The unique id
	 */
	private String id;
	
	
	/**
	 * The name of this Attribute
	 */
	private String name;
	
	/**
	 * The name of the Type of this Attribute
	 */
	private MetaBaseType type;
	
	
	private Set<CollaborateableElementPropertiesListener> listeners;
	
	
	
	public MetaAttributeImpl(String id, String name, MetaBaseType type) {
		this.id = id;
		this.name = name;
		this.type = type;
		listeners = new LinkedHashSet<CollaborateableElementPropertiesListener>();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		fireChanged();
	}


	public MetaBaseType getType() {
		return type;
	}


	public void setType(MetaBaseType type) {
		this.type = type;
		fireChanged();
	
	}

	
	private void fireChanged(){
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();
	}
	

	@Override
	public String getID() {
		return id;
	}


	@Override
	public void addCollaborateableElementPropertiesListeners(
			Set<CollaborateableElementPropertiesListener> listeners) {
		this.listeners.addAll(listeners);
	}


	@Override
	public void removeCollaborateableElementPropertiesListeners(
			Set<CollaborateableElementPropertiesListener> listeners) {
		this.listeners.removeAll(listeners);
	}
	
	

}
