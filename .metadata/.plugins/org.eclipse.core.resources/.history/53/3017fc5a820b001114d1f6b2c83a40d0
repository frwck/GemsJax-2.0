package org.gemsjax.server.persistence.experiment;

import java.util.LinkedHashSet;
import java.util.Set;
import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.user.RegisteredUser;

public class ExperimentImpl implements Experiment{
	
	private int id;
	private String name;
	private String description;
	private RegisteredUser owner;
	private MetaModelImpl metaModelTemplate;

	private Set<RegisteredUser> administrators;
	private Set<ExperimentGroup> experimentGroups;
	
	
	public ExperimentImpl()
	{
		administrators = new LinkedHashSet<RegisteredUser>();
		experimentGroups = new LinkedHashSet<ExperimentGroup>();
		
	}
	
	
	@Override
	public Set<RegisteredUser> getAdministrators() {
		return administrators;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public Set<ExperimentGroup> getExperimentGroups() {
		return experimentGroups;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public RegisteredUser getOwner() {
		return owner;
	}


	@Override
	public void setOwner(RegisteredUser owner) {
		this.owner = owner;
	}


	public void setMetaModelTemplate(MetaModelImpl metaModelTemplate) {
		this.metaModelTemplate = metaModelTemplate;
	}


	public MetaModelImpl getMetaModelTemplate() {
		return metaModelTemplate;
	}

}
