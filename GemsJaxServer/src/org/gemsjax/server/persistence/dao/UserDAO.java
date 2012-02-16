package org.gemsjax.server.persistence.dao;

import java.util.Set;

import org.gemsjax.server.persistence.dao.exception.AlreadyBefriendedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public interface UserDAO {

	/**
	 * Creates and stores a new {@link RegisteredUser}
	 * @param username
	 * @param passwordHash
	 * @param email
	 * @return
	 * @throws UsernameInUseException 
	 * @throws DAOException 
	 * @throws EMailInUseExcpetion 
	 */
	public abstract RegisteredUser createRegisteredUser(String username,
			String passwordHash, String email) throws UsernameInUseException,
			DAOException, EMailInUseExcpetion;

	/**
	 * Get a {@link RegisteredUser} by his username and password hash. This is used for the login
	 * @param username
	 * @param passwordHash
	 * @return
	 * @throws MoreThanOneExcpetion
	 * @throws NotFoundException 
	 */
	public abstract RegisteredUser getUserByLogin(String username,
			String passwordHash) throws MoreThanOneExcpetion, NotFoundException;

	/**
	 * Delete an {@link User} and all the other associated resources like:
	 * <ul>
	 * <li> {@link Request} </li>
	 * <li> owned {@link Collaborateable}s</li>
	 * <li> owned {@link Experiment}s</li>
	 * </ul>
	 * @param u
	 * @throws DAOException 
	 */
	public abstract void deleteRegisteredUser(RegisteredUser u)
			throws DAOException;

	/**
	 * Get a {@link User} by his unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public abstract RegisteredUser getRegisteredUserById(int id)
			throws NotFoundException;

	/**
	 * 
	 * @param u
	 * @param displayedName
	 * @throws ArgumentException
	 * @throws DAOException 
	 */
	public abstract void updateDisplayedName(User u, String displayedName)
			throws ArgumentException, DAOException;

	/**
	 * Change tha Password of an user
	 * @param u
	 * @param newPasswordHash
	 * @throws DAOException
	 */
	public abstract void updateRegisteredUserPassword(RegisteredUser u,
			String newPasswordHash) throws DAOException;

	public abstract void updateRegisteredUserEmail(RegisteredUser u,
			String newEmail) throws DAOException, EMailInUseExcpetion;
	
	/**
	 * Get {@link RegisteredUser}, that match the passed search criteria in username, email, display name
	 * @param searchCriteria
	 * @return
	 */
	public abstract Set<RegisteredUser> getBySearch(String searchCriteria);
	
	
	public abstract void addFriendship(RegisteredUser requester, RegisteredUser friend) throws DAOException, AlreadyBefriendedException;
	
	public abstract void cancelFriendship(RegisteredUser cancler, RegisteredUser exfriend) throws DAOException;

}