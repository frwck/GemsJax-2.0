package org.gemsjax.server.metamodel;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaContainmentRelation;
import org.gemsjax.shared.metamodel.MetaModelElement;

public class MetaContainmentRelationImpl implements MetaContainmentRelation {


	private String id;
	private int max;
	private int min;
	private MetaClass metaClass;
	
	public MetaContainmentRelationImpl(String id)
	{
		this.id = id;
	}
	
	@Override
	public int getMax() {
		return max;
	}

	@Override
	public MetaClass getMetaClass() {
		return metaClass;
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
	public void setMetaClass(MetaClass metaClass) {
		this.metaClass = metaClass;
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
