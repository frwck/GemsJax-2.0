package org.gemsjax.server.data.metamodel;

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
	
	
	public MetaContainmentRelationImpl(String id, MetaClass containedBy, MetaClass metaClass, int min, int max) {
		this.id = id;
		this.containedBy = containedBy;
		this.metaClass = metaClass;
		this.max = max;
		this.min = min;
	}

	@Override
	public MetaClass getContainedBy() {
		return containedBy;
	}

	@Override
	public void setContainedBy(MetaClass containedBy) {
		this.containedBy = containedBy;
	}

	@Override
	public MetaClass getMetaClass() {
		return metaClass;
	}

	@Override
	public void setMetaClass(MetaClass mc) {
		this.metaClass = mc;
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
	}

	@Override
	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public String getID() {
		return id;
	}
	
}
