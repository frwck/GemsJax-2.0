package org.gemsjax.server.module;

import java.util.Set;

import org.eclipse.jetty.server.Authentication.User;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * {@link SearchExecutor} executes a search for a {@link User}, {@link Collaborateable}s, and {@link Experiment}s that
 * are connected to the requester and matches the passed search string
 * @author Hannes Dorfmann
 *
 */
public class SearchExecutor {
	
	private UserDAO userDAO;
	private CollaborateableDAO collaborateableDAO;
	private ExperimentDAO experimentDAO;
	private RegisteredUser requester;
	
	/**
	 * Create a new {@link SearchExecutor}.
	 * @param requester The authenticated {@link RegisteredUser}, who has requested to start a search
	 */
	public SearchExecutor(RegisteredUser requester)
	{
		this.requester = requester;
		userDAO = new HibernateUserDAO();
		collaborateableDAO = new HibernateCollaborateableDAO();
		experimentDAO = new HibernateExperimentDAO();
	}
	
	
	public Set<RegisteredUser> getUsers(String searchString)
	{
		return userDAO.getBySearch(searchString);
	}
	
	public Set<Collaborateable> getCollaborateables(String searchString)
	{
		return collaborateableDAO.getBySearch(searchString, requester);
	}
	
	
	public Set<Experiment> getExperiments(String searchString)
	{
		return experimentDAO.getBySearch(searchString, requester);
	}

}
