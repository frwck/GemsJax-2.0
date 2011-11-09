package org.gemsjax.server.persistence.dao;

import org.gemsjax.server.persistence.HibernateUtil;
import org.hibernate.Session;


/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableDAO {
	
	private Session session ;
	
	public CollaborateableDAO()
	{
		session = HibernateUtil.getOpenedSession();
	}
	
	

	
}
