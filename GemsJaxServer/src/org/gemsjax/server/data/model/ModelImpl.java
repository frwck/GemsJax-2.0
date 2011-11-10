package org.gemsjax.server.data.model;

import org.gemsjax.server.persistence.collaboration.CollaborateableImpl;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
public class ModelImpl extends CollaborateableImpl implements Model {

	private MetaModel metaModel;
	
	
	public ModelImpl()
	{
		
	}
	

	@Override
	public MetaModel getMetaModel() {
		return metaModel;
	}

	@Override
	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}

}
