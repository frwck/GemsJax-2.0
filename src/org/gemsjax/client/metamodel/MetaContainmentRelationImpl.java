package org.gemsjax.client.metamodel;

import org.gemsjax.shared.metamodel.MetaContainmentRelation;

/**
 * This is used to realize containment relationsships between {@link MetaClassImpl}es.
 * So a {@link MetaClassImpl} is able to contain other {@link MetaClassImpl}es.
 * @author Hannes Dorfmann
 *
 */
public class MetaContainmentRelationImpl implements MetaContainmentRelation {
	
	private MetaClassImpl containedBy;
	private MetaClassImpl contains;
	
	
	public MetaContainmentRelationImpl(MetaClassImpl containedBy, MetaClassImpl contains) {
		super();
		this.containedBy = containedBy;
		this.contains = contains;
	}


	public MetaClassImpl getContainedBy() {
		return containedBy;
	}


	public void setContainedBy(MetaClassImpl containedBy) {
		this.containedBy = containedBy;
	}


	public MetaClassImpl getContains() {
		return contains;
	}


	public void setContains(MetaClassImpl contains) {
		this.contains = contains;
	}
	
	

	
	

}
