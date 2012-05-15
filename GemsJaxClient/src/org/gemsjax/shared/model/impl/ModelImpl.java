package org.gemsjax.shared.model.impl;

import org.gemsjax.shared.collaboration.CollaborateableImpl;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;

public class ModelImpl extends CollaborateableImpl implements Model{

	private MetaModel metaModel;
	
	
	public ModelImpl(int id, String name){
		setId(id);
		setName(name);
	}
	
	public ModelImpl(){
		
	}
	
	
	@Override
	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void setMetaModel(MetaModel m){
		this.metaModel = m;
	}

}
