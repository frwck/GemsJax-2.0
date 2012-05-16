package org.gemsjax.shared.metamodel.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.collaboration.CollaborateableElementPropertiesListener;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaContainmentRelation;

/**
 * This is used to realize containment relationsships between {@link MetaClassImpl}es.
 * So a {@link MetaClassImpl} is able to contain other {@link MetaClassImpl}es.
 * @author Hannes Dorfmann
 *
 */
public class MetaContainmentRelationImpl implements MetaContainmentRelation {
	
	
	private String id;
	
	private MetaClass containedBy;
	private MetaClass metaClass;
	
	private int min;
	private int max;
	
	
	private Set<CollaborateableElementPropertiesListener> listeners;
	
	public MetaContainmentRelationImpl(String id, MetaClass containedBy, MetaClass metaClass, int min, int max) {
		this.id = id;
		this.containedBy = containedBy;
		this.metaClass = metaClass;
		this.max = max;
		this.min = min;
		
		listeners = new LinkedHashSet<CollaborateableElementPropertiesListener>();
	}

	@Override
	public MetaClass getContainedBy() {
		return containedBy;
	}

	@Override
	public void setContainedBy(MetaClass containedBy) {
		this.containedBy = containedBy;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();

	}

	@Override
	public MetaClass getMetaClass() {
		return metaClass;
	}

	@Override
	public void setMetaClass(MetaClass mc) {
		this.metaClass = mc;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();

	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();

	}

	@Override
	public void setMin(int min) {
		this.min = min;
		
		for (CollaborateableElementPropertiesListener l : listeners)
			l.onChanged();

	}

	@Override
	public String getID() {
		return id;
	}
	
	
	@Override
	public void addPropertiesListener(CollaborateableElementPropertiesListener l) {
		listeners.add(l);
	}


	@Override
	public void removePropertiesListener(
			CollaborateableElementPropertiesListener l) {
		listeners.remove(l);
	}
	
}
