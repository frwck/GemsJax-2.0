package org.gemsjax.shared.experiment;

import java.util.Set;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * An {@link Experiment} is owned by a {@link RegisteredUser} ({@link #getOwner()}) and only this {@link RegisteredUser} can delete this Experiment completely (including all results and resulting models).
 * An {@link Experiment} can be administrated by at least one {@link RegisteredUser}s ({@link #getAdministrators()})
 * An {@link Experiment} has at least one {@link ExperimentGroup} ({@link #getExperimentGroups()}).
 * @author Hannes Dorfmann
 *
 */
public interface Experiment {
	
		/**
	 * Get the unique Experiment id
	 * @return
	 */
	public int getId();
	
	/**
	 * Get the creator / owner, who has created this {@link Experiment}.
	 * This user is the one who can delete this experiment
	 * @return
	 */
	public RegisteredUser getOwner();
	
	/**
	 * Set the owner
	 * @param owner
	 */
	public void setOwner(RegisteredUser owner);
	
	/**
	 * Get the the Users that administrate this Experiments
	 * @return
	 */
	public Set<RegisteredUser> getAdministrators();
	
	/**
	 * Get the name of this Experiment
	 * @return
	 */
	public String getName();
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Get the description
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Set the description
	 * @param description
	 * @return
	 */
	public void setDescription(String description);
	
	/**
	 * Get the {@link ExperimentGroup}s 
	 * @return
	 */
	public Set<ExperimentGroup> getExperimentGroups();
}
