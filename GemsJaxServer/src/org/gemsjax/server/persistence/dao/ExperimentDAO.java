package org.gemsjax.server.persistence.dao;

import java.util.Date;
import java.util.List;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Use this Data Access Object to get Instances of:
 * <ul>
 * <li> {@link Experiment}</li>
 * <li> {@link ExperimentUser}</li>
 * <li> {@link ExperimentGroup}</li>
 * <li> {@link ExperimentInvitation}</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class ExperimentDAO {

private Session session ;
	
	public ExperimentDAO()
	{
		session = HibernateUtil.getOpenedSession();
	}
	
	
	
	/**
	 * Create a new {@link Experiment} and assign it to the owner
	 * @param name
	 * @param description
	 * @param owner
	 * @return
	 */
	public Experiment createExperiment(String name, String description, RegisteredUser owner)
	{
		
		ExperimentImpl e = new ExperimentImpl();
		
		e.setName(name);
		e.setDescription(description);
		e.setOwner(owner);
		
		
		Transaction tx = session.beginTransaction();
			session.save(e);
		tx.commit();
		
		return e;
		
	}
	
	
	/**
	 * Get a {@link Experiment} by its unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public Experiment getExperimentById(int id) throws MoreThanOneExcpetion
	{
		Query query = session.createQuery( "FROM ExperimentImpl WHERE id="+id );
	      
	    List<ExperimentImpl> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
	}
	
	
	/**
	 * Update the {@link Experiment} details
	 * @param e
	 * @param name
	 * @param description
	 * @throws IllegalArgumentException
	 */
	public void updateExperiment(Experiment e, String name, String description) throws IllegalArgumentException
	{
		if (FieldVerifier.isEmpty(name))
			throw new IllegalArgumentException("Name is empty");
		
		if (FieldVerifier.isEmpty(description))
			throw new IllegalArgumentException("Description is empty");
		
		
		Transaction t = session.beginTransaction();
		
			if (!name.equals(e.getName()))
				e.setName(name);
			
			if (!description.equals(e.getDescription()))
				e.setDescription(description);
		
			session.update(e);
		t.commit();
	}
	
	
	/**
	 * Create a new {@link ExperimentGroup} and add this new created to the passed {@link Experiment}
	 * @param experiment
	 * @param experimentGroupName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ExperimentGroup createAndAddExperimentGroup(Experiment experiment, String experimentGroupName, Date startDate, Date endDate) throws IllegalArgumentException
	{
		
		if (FieldVerifier.isEmpty(experimentGroupName))
			throw new IllegalArgumentException("Group name is empty");
			
		Transaction t = session.beginTransaction();
			ExperimentGroupImpl g = new ExperimentGroupImpl();
			g.setEndDate(endDate);
			g.setStartDate(startDate);
			g.setName(experimentGroupName);
			g.setExperiment(experiment);
			
			experiment.getExperimentGroups().add(g);
			
			session.save(g);
			session.update(experiment);
		t.commit();
		
		return g;
	}
	
	
}
