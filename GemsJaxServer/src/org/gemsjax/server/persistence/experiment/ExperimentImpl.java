package org.gemsjax.server.persistence.experiment;

import java.util.LinkedHashSet;
import java.util.Set;
import org.gemsjax.shared.metamodel.impl.MetaModelImpl;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.user.RegisteredUser;

public class ExperimentImpl implements Experiment{
	
	private Integer id;
	private String name;
	private String description;
	private RegisteredUser owner;
	private MetaModelImpl metaModelTemplate;
	private boolean forExperiment;

	private Set<RegisteredUser> administrators;
	private Set<ExperimentGroup> experimentGroups;
	
	
	public ExperimentImpl()
	{
		administrators = new LinkedHashSet<RegisteredUser>();
		experimentGroups = new LinkedHashSet<ExperimentGroup>();
		forExperiment = false;
		
	}
	
	public boolean isForExperiment() {
		return forExperiment;
	}


	public void setForExperiment(boolean forExperiment) {
		this.forExperiment = forExperiment;
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
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof ExperimentImpl) ) return false;
		
		final ExperimentImpl that = (ExperimentImpl) other;
		
		if (id != null && that.id != null)
			return this.id.equals(that.id);
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		else
			return super.hashCode();
	}

}
