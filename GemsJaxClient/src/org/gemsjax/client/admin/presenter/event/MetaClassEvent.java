package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.shared.metamodel.impl.MetaClassImpl;
import org.gemsjax.shared.metamodel.impl.MetaModelImpl;

public class MetaClassEvent {

	public enum Type
	{
		ADDED,
		CHANGED,
		REMOVED
	}
	
	
	private MetaModelImpl metaModel;
	private Type type;
	private MetaClassImpl metaClass;
	
	
	public MetaClassEvent(MetaClassImpl metaClass, MetaModelImpl metaModel, Type type)
	{
		this.type = type;
		this.metaClass = metaClass;
		this.metaModel = metaModel;
	}


	public MetaModelImpl getMetaModel() {
		return metaModel;
	}


	public Type getType() {
		return type;
	}


	public MetaClassImpl getMetaClass() {
		return metaClass;
	}
	
	
	
	
}
