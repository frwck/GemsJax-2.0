package org.gemsjax.client.metamodel;

/**
 * This is used to realize containment relationsships between {@link MetaClass}es.
 * So a {@link MetaClass} is able to contain other {@link MetaClass}es.
 * @author Hannes Dorfmann
 *
 */
public class ContainmentRelation {
	
	private MetaClass containedBy;
	private MetaClass contains;
	
	
	public ContainmentRelation(MetaClass containedBy, MetaClass contains) {
		super();
		this.containedBy = containedBy;
		this.contains = contains;
	}


	public MetaClass getContainedBy() {
		return containedBy;
	}


	public void setContainedBy(MetaClass containedBy) {
		this.containedBy = containedBy;
	}


	public MetaClass getContains() {
		return contains;
	}


	public void setContains(MetaClass contains) {
		this.contains = contains;
	}
	
	

	
	

}
