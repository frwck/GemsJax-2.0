package org.gemsjax.server.module;

import java.util.Set;

import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.user.RegisteredUser;

public class SearchExecutor {
	
	private UserDAO userDAO;
	private CollaborateableDAO collaborateableDAO;
	private ExperimentDAO experimentDAO;
	private RegisteredUser requester;
	
	/**
	 * Create a new {@link SearchExecutor}.
	 * @param authenticatedUser The authenticated {@link RegisteredUser}, who has requested to start a search
	 */
	public SearchExecutor(RegisteredUser authenticatedUser)
	{
		this.requester = authenticatedUser;
		userDAO = new HibernateUserDAO();
		collaborateableDAO = new HibernateCollaborateableDAO();
		experimentDAO = new HibernateExperimentDAO();
	}
	
	
	public Set<RegisteredUser> getUser(String searchCriteria)
	{
		
	}

}
