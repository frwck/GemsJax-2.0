package org.gemsjax.server.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    
    private static Session session;
 
    static
    {
    	 sessionFactory = new Configuration().configure().buildSessionFactory();
    	 session = sessionFactory.openSession();
    }
 
    public static SessionFactory getSessionFactory()
    {
    	
        return sessionFactory;
    }
    
    
    public static Session getOpenedSession()
    {
    	return session;
    }
    
    
    public void closeSession()
    {
    	session.close();
    }
    
}
