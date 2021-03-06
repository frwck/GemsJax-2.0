package org.gemsjax.server.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.InvitationAlreadyUsedException;
import org.gemsjax.server.persistence.dao.exception.InvitationException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;

public interface ExperimentDAO {

	/**
	 * Create a new {@link Experiment} and assign it to the owner
	 * @param name
	 * @param description
	 * @param owner
	 * @return
	 * @throws ArgumentException
	 */
	public abstract Experiment createExperiment(String name,
			String description, RegisteredUser owner) throws ArgumentException;
	
	
	/**
	 * Create a new {@link ExperimentInvitation} for a {@link ExperimentGroup} and add this to this {@link ExperimentGroup}
	 * @param group
	 * @param email
	 * @param verificationCode
	 * @return
	 */
	public abstract ExperimentInvitation createExperimentInvitation(ExperimentGroup group,  String email, String verificationCode);
	
	/**
	 * Create many {@link ExperimentInvitation}s at once and add them to an {@link ExperimentGroup}.
	 * Notice that the {@link List} emails and the {@link List} verificationCodes belong together according the list index.
	 * 
	 * For example: emails.get(0) and verificationCodes.get(0) are the values to build a new {@link ExperimentInvitation}.
	 * @param group
	 * @param emails
	 * @param verificationCodes
	 * @return
	 * @throws ArgumentException 
	 * @throws DAOException 
	 */
	public abstract Set<ExperimentInvitation> createExperimentInvitations(ExperimentGroup group, List<String> emails) throws ArgumentException, DAOException;

	
	/**
	 * Get a {@link Experiment} by its unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 */
	public abstract Experiment getExperimentById(int id)
			throws NotFoundException;

	/**
	 * Update the {@link Experiment} details
	 * @param e
	 * @param name
	 * @param description
	 * @throws IllegalArgumentException
	 */
	public abstract void updateExperiment(Experiment e, String name,
			String description) throws ArgumentException;

	/**
	 * Create a new {@link ExperimentGroup} and add this new created to the passed {@link Experiment}
	 * @param experiment
	 * @param experimentGroupName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ArgumentException
	 */
	public abstract ExperimentGroup createAndAddExperimentGroup(
			Experiment experiment, String experimentGroupName, Date startDate,
			Date endDate) throws ArgumentException;

	/**
	 * Add a set of new {@link ExperimentGroup}s to the {@link Experiment}.
	 * "new" means, that the {@link ExperimentGroup}s are not already stored in the database and the {@link ExperimentGroup} 
	 * has at least set the name of the group and optional the start date and end date.
	 * @param experiment
	 * @param experimentGroups
	 */
	public abstract void addExperimentGroups(Experiment experiment,
			Set<ExperimentGroupDTO> experimentGroups) throws ArgumentException;

	public abstract void deleteExperimentGroup(ExperimentGroup group)
			throws ArgumentException, DAOException;

	/**
	 * Delete a {@link Experiment}. This would 
	 * @param experiment
	 * @throws ArgumentException
	 * @throws DAOException 
	 */
	public abstract void deleteExperiment(Experiment experiment)
			throws ArgumentException, DAOException;

	/**
	 * Create a new {@link ExperimentUser}
	 * @param username
	 * @param passwordHash
	 * @param experimentGroup
	 * @return
	 * @throws ArgumentException
	 * @throws UsernameInUseException
	 */
	public abstract ExperimentUser createExperimentUser(String username,
			String passwordHash, ExperimentGroup experimentGroup, String displayedName)
			throws ArgumentException, UsernameInUseException;


	
	public abstract boolean isDisplayedNameInExperimentGroupAvailable(String displayedName, ExperimentGroup group);
	
	
	public abstract void updateExperimentGroup(ExperimentGroup group,
			String name, Date startDate, Date endDate)
			throws ArgumentException, DAOException;

	public abstract void deleteExperimentInvitation(
			ExperimentInvitation invitation) throws ArgumentException,
			DAOException;

	/**
	 * Get a List with all Experiments, that belongs to the owner
	 * @param owner
	 * @return
	 */
	public abstract List<Experiment> getExperimentByOwner(RegisteredUser owner);

	/**
	 * Assign a {@link RegisteredUser} as administrator 
	 * @param experiment
	 * @param user
	 * @throws AlreadyAssignedException
	 * @throws DAOException 
	 */
	public abstract void assignExperimentAdministrator(Experiment experiment,
			RegisteredUser user) throws AlreadyAssignedException, DAOException;
	
	
	/**
	 * Get a {@link ExperimentGroup} by its unique id
	 * @param id
	 * @return
	 * @throws DAOException
	 * @throws NotFoundException
	 * @throws MoreThanOneExcpetion 
	 */
	public abstract ExperimentGroup getExperimentGroup(int id) throws NotFoundException, MoreThanOneExcpetion;
	
	/**
	 * Get a ExperimentInvitation by its unique VerificationCode
	 * @param verificationCode
	 * @return
	 * @throws MoreThanOneExcpetion 
	 * @throws NotFoundException 
	 */
	public abstract ExperimentInvitation getExperimentInvitation(String verificationCode) throws MoreThanOneExcpetion, NotFoundException;
	
	/**
	 * Create a new {@link ExperimentUser} 
	 * @param invitationVerificationCode
	 * @return
	 * @throws DAOException 
	 * @throws InvitationAlreadyUsedException 
	 */
	public abstract ExperimentUser createExperimentUser(String invitationVerificationCode, String username, String passwordHash) throws DAOException, InvitationAlreadyUsedException;

	/**
	 * Get a {@link ExperimentUser} by his username and password.
	 * This is method is normally used to make login.
	 * @param username
	 * @param passwordHash
	 * @return
	 * @throws MoreThanOneExcpetion
	 * @throws NotFoundException
	 * @throws DAOException 
	 */
	public abstract ExperimentUser getExperimentUserByLogin(String username, String passwordHash) throws MoreThanOneExcpetion, NotFoundException, DAOException;

	/**
	 * Search for experiments that are owned by the requester or co-administrated by the requester,
	 * and the experiment name or experiment description matches the passed searchString.
	 * @param searchString
	 * @param requester
	 * @return
	 */
	public Set<Experiment> getBySearch(String searchString, RegisteredUser requester);
	
	
	public void setExperimentInvitationParticipated(ExperimentInvitation inv, boolean participated) throws DAOException;
	
}