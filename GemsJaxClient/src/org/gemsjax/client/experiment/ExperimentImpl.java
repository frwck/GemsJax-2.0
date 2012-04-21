package org.gemsjax.client.experiment;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * Note: the username and displayname is implemented as one 
 * @author Hannes Dorfmann
 *
 */
public class ExperimentImpl implements Experiment {

	private int id;
	private RegisteredUser owner;
	private Set<RegisteredUser> admins;
	private String description;
	private String name;
	private Set<ExperimentGroup> groups;

	
	public ExperimentImpl(int id, String name){
		this.id = id;
		this.name = name;
		admins = new LinkedHashSet<RegisteredUser>();
		groups = new LinkedHashSet<ExperimentGroup>();
	}
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public RegisteredUser getOwner() {
		return owner;
	}

	@Override
	public void setOwner(RegisteredUser owner) {
		this.owner = owner;
	}

	@Override
	public Set<RegisteredUser> getAdministrators() {
		return admins;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Set<ExperimentGroup> getExperimentGroups() {
		return groups;
	}

}
