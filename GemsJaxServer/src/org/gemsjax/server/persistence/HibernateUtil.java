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
    }
 
    public static SessionFactory getSessionFactory()
    {
    	
        return sessionFactory;
    }
   
    public static void reconnect()
    {
    	sessionFactory.close();
    	sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
}
