package org.gemsjax.client.admin.event.metamodel;

import org.gemsjax.client.model.metamodel.MetaClass;
import org.gemsjax.client.model.metamodel.MetaModel;

public class MetaClassEvent {

	public enum Type
	{
		ADDED,
		CHANGED,
		REMOVED
	}
	
	
	private MetaModel metaModel;
	private Type type;
	private MetaClass metaClass;
	
	
	public MetaClassEvent(MetaClass metaClass, MetaModel metaModel, Type type)
	{
		this.type = type;
		this.metaClass = metaClass;
		this.metaModel = metaModel;
	}


	public MetaModel getMetaModel() {
		return metaModel;
	}


	public Type getType() {
		return type;
	}


	public MetaClass getMetaClass() {
		return metaClass;
	}
	
	
	
	
}
