package org.gemsjax.server.persistence.dao;

import java.util.Set;

import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
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
	 * Creates a 
	 * @param owner
	 * @param name
	 * @param keywords
	 * @param type
	 * @param permission
	 * @param admins
	 * @param collaborators
	 * @return
	 * @throws DAOException
	 */
	public abstract Collaborateable createCollaborateable( RegisteredUser owner, String name, String keywords, 
			CollaborateableType type, Collaborateable.Permission permission,
			 Set<User> collaborators)
			throws DAOException;
	
	
	/**
	 * Updates a Collaborateable with the specified parameters. If a parameter is set to null, this value
	 * will not be updated.
	 * @param c {@link Collaborateable}
	 * @param name
	 * @param keywords
	 * @param _public
	 * @param addAdmins
	 * @param removeAdmins
	 * @param addCollaborators
	 * @param removeCollaborators
	 * @throws DAOException
	 */
	public abstract void updateCollaborateable(Collaborateable c, String name, String keywords,
			Collaborateable.Permission permission, 
			Set<User> addCollaborators, Set<User> removeCollaborators) throws DAOException;
	
	
	

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

	
	
	/**
	 * Search for {@link Collaborateable}s that search criteria matches the name or keywords and have set the public permission to
	 * be public or the requester is owner or collaborator on this. Note: {@link Collaborateable}s that ara used for experiments are
	 * not considered. 
	 * @param searchString
	 * @return
	 */
	public abstract Set<Collaborateable> getBySearch(String searchString, RegisteredUser requester);
	
}