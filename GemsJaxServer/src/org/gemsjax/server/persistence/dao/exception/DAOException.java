package org.gemsjax.server.persistence.dao.exception;

import org.hibernate.HibernateException;

/**
 * This is a general exception that can be thrown by a DAO
 * @author Hannes Dorfmann
 *
 */
public class DAOException extends Exception {
	
	private HibernateException hibernateException;
	
	public DAOException(HibernateException ex, String msg)
	{
		super(msg);
		hibernateException = ex;
	}
	
	public DAOException(String msg)
	{
		super(msg);
	}
	
	
	public HibernateException getHibernateException()
	{
		return hibernateException;
	}

}
