package org.gemsjax.server.data.model;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.persistence.collaboration.CollaborateableImpl;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;


public class ModelImpl extends CollaborateableImpl implements Model {

	private MetaModel metaModel;
	private boolean forExperiment;
	
	
	public ModelImpl()
	{
		
	}
	

	@Override
	public MetaModel getMetaModel() {
		return metaModel;
	}

	
	public void setMetaModel(MetaModelImpl metaModel) {
		this.metaModel = metaModel;
	}


	public boolean isForExperiment() {
		return forExperiment;
	}


	public void setForExperiment(boolean forExperiment) {
		this.forExperiment = forExperiment;
	}

}
