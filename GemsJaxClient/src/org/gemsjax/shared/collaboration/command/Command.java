package org.gemsjax.shared.collaboration.command;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.model.ModelClass;

/**
 * A {@link Command} is something that can be executed on a {@link MetaModel} (even on child elements like {@link MetaClass} etc.) or other
 * elements like {@link Model} (even on child elements like {@link ModelClass}).
 * 
 * {@link Command}s are used to realize the collaborative work
 *  
 * @author Hannes Dorfmann
 *
 */
public interface Command {

	public void execute();
	
	public void undo();
	
	public String toXML();
	
}