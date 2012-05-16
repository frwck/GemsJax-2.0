package org.gemsjax.shared.collaboration.command;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.serialisation.Serializable;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
/**
 * A {@link Command} is something that can be executed on a {@link MetaModel} (even on child elements like {@link MetaClass} etc.) or other
 * elements like {@link Model} (even on child elements like {@link ModelClass}).
 * 
 * {@link Command}s are used to realize the collaborative work
 *  
 * @author Hannes Dorfmann
 *
 */
public interface Command extends Serializable{

	public void execute() throws ManipulationException;
	
	public void undo() throws ManipulationException;
	
	public void setCollaborateable(Collaborateable c);
	public Collaborateable getCollaborateable();
	
	public void setTransaction(Transaction t);
	public Transaction getTransaction();
	
}
