package org.gemsjax.shared.experiment;

import org.gemsjax.shared.user.ExperimentUser;


/**
 * An {@link ExperimentInvitation} represents an invitation to an {@link ExperimentGroup}.
 * The invitation is delivered via email by sending an link to the GemsJax Experiment site. This link contains an verification code,
 * that makes it possible, to determine if this {@link ExperimentInvitation} is valid / expired or already used ({@link #hasParticipated()}).
 * @author Hannes Dorfmann
 *
 */
public interface ExperimentInvitation {
	
	/**
	 * The unique id
	 * @return
	 */
	public int getId();
	
	/**
	 * The invitation is for this {@link ExperimentGroup}
	 * @return
	 */
	public ExperimentGroup getExperimentGroup();
	public void setExperimentGroup(ExperimentGroup group);
	
	/**
	 * The verification code is used to determine if this {@link ExperimentInvitation} is valid / expired or already used ({@link #hasParticipated()}).
	 * @return
	 */
	public String getVerificationCode();
	
	/**
	 * Returns true if an {@link ExperimentUser} was created and already logged in  (at least one time) to the {@link ExperimentGroup} with this {@link ExperimentInvitation} 
	 * @return
	 */
	public boolean hasParticipated();
	
	/**
	 * @see #hasParticipated()
	 * @param participated
	 */
	public void setParticipated(boolean participated);
	
	/**
	 * Get the email address
	 * @return
	 */
	public String getEmail();
	
	public void setEmail(String email);
	

}
