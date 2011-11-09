package org.gemsjax.server.persistence.dao;

import org.gemsjax.server.persistence.HibernateUtil;
import org.hibernate.Session;


/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaModelDAO {
	
	private Session session ;
	
	public MetaModelDAO()
	{
		session = HibernateUtil.getOpenedSession();
	}

	
}
