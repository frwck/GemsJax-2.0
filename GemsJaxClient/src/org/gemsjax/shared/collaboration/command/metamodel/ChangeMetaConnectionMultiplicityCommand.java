package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaConnectionMultiplicityCommand extends CommandImpl{
	
	private String metaConnectionId;
	private int newLowerBound;
	private int newUpperBound;
	private int oldLowerBound;
	private int oldUpperBound;
	
	public ChangeMetaConnectionMultiplicityCommand(){}
	
	public ChangeMetaConnectionMultiplicityCommand(String id, MetaConnection connection, int lowerBound, int upperBound){
		setId(id);
		metaConnectionId = connection.getID();
		oldLowerBound = connection.getTargetLowerBound();
		oldUpperBound = connection.getTargetUpperBound();
		this.newLowerBound = lowerBound;
		this.newUpperBound = upperBound;
		
	}
	
	@Override
	public void serialize(Archive a)throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		oldLowerBound = a.serialize("oldLowerBound", oldLowerBound).value;
		oldUpperBound = a.serialize("oldUpperBound", oldUpperBound).value;
		newLowerBound = a.serialize("newLowerBound", newLowerBound).value;
		newUpperBound = a.serialize("newUpperBound", newUpperBound).value;
		
	}
	
	

	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setTargetUpperBound(newUpperBound);
		mc.setTargetLowerBound(newLowerBound);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setTargetUpperBound(oldUpperBound);
		mc.setTargetLowerBound(oldLowerBound);
	}

}
