package org.gemsjax.shared.experiment;

import java.util.List;

import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

/**
 * An {@link Experiment} is owned by a {@link RegisteredUser} ({@link #getOwner()}) and only this {@link RegisteredUser} can delete this Experiment completely (including all results and resulting models).
 * An {@link Experiment} can be administrated by at least one {@link RegisteredUser}s ({@link #getAdministrators()})
 * An {@link Experiment} has at least one {@link ExperimentGroup} ({@link #getGroups()}).
 * @author Hannes Dorfmann
 *
 */
public interface Experiment {
	
		/**
	 * Get the unique Experiment id
	 * @return
	 */
	public long getId();
	
	/**
	 * Get the creator / owner, who has created this {@link Experiment}.
	 * This user is the one who can delete this experiment
	 * @return
	 */
	public RegisteredUser getOwner();
	
	/**
	 * Get the the Users that administrate this Experiments
	 * @return
	 */
	public List<RegisteredUser> getAdministrators();
	
	/**
	 * Get the name of this Experiment
	 * @return
	 */
	public String getName();
	
	/**
	 * Get the description
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Get the {@link ExperimentGroup}s 
	 * @return
	 */
	public List<ExperimentGroup> getGroups();
}
