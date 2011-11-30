package org.gemsjax.server.persistence.dao;

import java.util.Set;

import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public interface CollaborateableDAO {

	/**
	 * Delete an {@link Collaborateable} like {@link MetaModel} and {@link Model}
	 * @param u
	 * @throws DAOException 
	 */
	public abstract void deleteCollaborateable(Collaborateable c)
			throws DAOException;
	

	/**
	 * Create a new {@link MetaModel} and store this into the database
	 * @param name
	 * @param owner The owner of this {@link MetaModel}
	 * @return
	 * @throws DAOException 
	 */
	public abstract MetaModel createMetaModel(String name, RegisteredUser owner)
			throws DAOException;

	/**
	 * Set the public permission of an {@link Collaborateable}
	 * @param col
	 * @param permission
	 * @throws DAOException 
	 */
	public abstract void updatePublicPermission(Collaborateable col,
			int permission) throws DAOException;

	/**
	 * Set the name of the {@link Collaborateable}.
	 * If the new name is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newName
	 * @throws DAOException 
	 */
	public abstract void updateCollaborateableName(Collaborateable c,
			String newName) throws DAOException;

	/**
	 * Set the keywords (for search) for the MetaModel.
	 * If the new keywords-string is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newKeywords
	 * @throws DAOException 
	 */
	public abstract void updateCollaborateableKeywords(Collaborateable c,
			String newKeywords) throws DAOException;

	/**
	 * Get a {@link MetaModel} by his unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 */
	public abstract MetaModel getMetaModelById(int id) throws NotFoundException;

	/**
	 * Get a {@link Model} by his unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 * 
	 */
	public abstract Model getModelById(int id) throws NotFoundException;

	/**
	 * Add a {@link User} to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 * @throws DAOException 
	 */
	public abstract void addCollaborativeUser(Collaborateable c, User u)
			throws DAOException;

	/**
	 * Add a Set of {@link User}s to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 * @throws DAOException 
	 */
	public abstract void addCollaborativeUsers(Collaborateable c, Set<User> u)
			throws DAOException;

	/**
	 * Create a {@link Model}
	 * @param name
	 * @param owner
	 * @return
	 * @throws DAOException 
	 */
	public abstract Model createModel(String name, MetaModel metaModel,
			RegisteredUser owner) throws DAOException;

	/**
	 * Remove an User of the list of users that works collaborative on this {@link Collaborateable}.
	 * That means, that this User can not work longer collaborative on this {@link Collaborateable}.
	 * @param c
	 * @param u
	 * @throws DAOException
	 */
	public abstract void removeCollaborativeUser(Collaborateable c, User u)
			throws DAOException;

	/**
	 * Remove a Set of {@link User}s from this collaborateable
	 * @see #removeCollaborativeUser(Collaborateable, User)
	 * @param c
	 * @param users
	 * @throws DAOException
	 */
	public abstract void removeCollaborativeUsers(Collaborateable c,
			Set<User> users) throws DAOException;

}